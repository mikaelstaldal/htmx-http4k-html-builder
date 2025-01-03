package nu.staldal.htmxhttp4kbuilder

import org.http4k.core.HttpHandler
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.PATCH
import org.http4k.core.Method.POST
import org.http4k.core.Method.PUT
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.isHtmx
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.ResourceLoader
import org.http4k.routing.Router.Companion.orElse
import org.http4k.routing.bind
import org.http4k.routing.htmxWebjars
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.util.UUID

private const val port = 8000

fun main() {
    val app = createApp(DataStore())
    app.asServer(SunHttp(port)).start()
    println("Listening on $port")
}

fun createApp(dataStore: DataStore): HttpHandler = ServerFilters.CatchAll { t ->
    t.printStackTrace()
    Response(Status.INTERNAL_SERVER_ERROR)
}.then(
    routes(
        "/" bind GET to {
            htmlPage { index() }
        },
        "/click-to-edit" bind GET to {
            htmlPage { clickToEdit(dataStore.person) }
        },
        "/bulk-update" bind GET to {
            htmlPage { bulkUpdate(dataStore.contacts.values) }
        },

        "/click-to-load" bind GET to routes(
            Request.isHtmx bind { request ->
                val page = pageLens(request)
                htmlPartial(OK) {
                    agentsList(
                        dataStore.agents.drop(10 * page).take(10).toList(),
                        page + 1
                    )
                }
            },
            orElse bind { htmlPage { clickToLoad(dataStore.agents) } }
        ),

        "/infinite-scroll" bind GET to routes(
            Request.isHtmx bind { request ->
                val page = pageLens(request)
                htmlPartial(OK) {
                    agentsListInfinite(
                        dataStore.agents.drop(10 * page).take(10).toList(), page + 1
                    )
                }
            },
            orElse bind { htmlPage { infiniteScroll(dataStore.agents) } }
        ),

        "/value-select" bind GET to {
            htmlPage { valueSelect(dataStore.makes) }
        },
        "/modal-dialog" bind GET to {
            htmlPage { modalDialog() }
        },
        "/todo-list" bind GET to {
            htmlPage { todoList(dataStore.todos.values) }
        },

        "/person" bind GET to {
            htmlPartial(OK) { viewPerson(dataStore.person) }
        },
        "/person/edit" bind GET to {
            htmlPartial(OK) { editPerson(dataStore.person) }
        },
        "/person" bind PUT to { request ->
            dataStore.person = personLens(request)
            println("Person updated: ${dataStore.person}")
            htmlPartial(OK) { viewPerson(dataStore.person) }
        },

        "/contacts/activate" bind PUT to { request ->
            activateOrDeactivateContact(request, true, dataStore)
        },

        "/contacts/deactivate" bind PUT to { request ->
            activateOrDeactivateContact(request, false, dataStore)
        },

        "/models" bind GET to { request ->
            val make = makeLens(request)
            dataStore.models[make]?.let {
                htmlPartial(OK) { options(it) }
            } ?: Response(NOT_FOUND)
        },

        "/modal" bind GET to {
            htmlPartial(OK) { dialogContent() }
        },

        "/todo" bind POST to { request ->
            val description = descriptionLens(request)
            val id = UUID.randomUUID().toString()
            val todo = Todo(id, description)
            println("created: $todo")
            dataStore.todos[id] = todo
            htmlPartial(CREATED) { todoRow(todo) }
        },
        "/todo/{id}" bind PATCH to { request ->
            val id = idLens(request)
            dataStore.todos[id]?.let {
                val done = doneLens(request)
                println("Setting $it to done=$done")
                it.done = done
                htmlPartial(OK) { todoRow(it) }
            } ?: Response(NOT_FOUND)
        },
        "/todo/{id}" bind DELETE to { request ->
            val id = idLens(request)
            dataStore.todos.remove(id)?.let {
                println("removed: $it")
                Response(OK)
            } ?: Response(NOT_FOUND)
        },

        "/assets" bind static(ResourceLoader.Classpath("/assets")),

        htmxWebjars(),
        webjar("bootstrap", "5.3.3")
    )
)

private fun activateOrDeactivateContact(request: Request, activate: Boolean, dataStore: DataStore): Response {
    val ids = idsLens(request)
    println("${if (activate) "Activating" else "Deactivating"} contacts: $ids")
    val mutated = ids.mapNotNull { id ->
        dataStore.contacts[id]?.let {
            if (it.active xor activate) {
                it.active = activate
                id
            } else null
        }
    }.toSet()
    return htmlPartial(OK) {
        contactsList(
            dataStore.contacts.values.map { it to mutated.contains(it.id) },
            activate
        )
    }
}
