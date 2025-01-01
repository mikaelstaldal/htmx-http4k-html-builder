package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.table
import nu.staldal.kotlin.html.tbody
import nu.staldal.kotlin.html.td
import nu.staldal.kotlin.html.th
import nu.staldal.kotlin.html.thead
import nu.staldal.kotlin.html.tr

fun Html.infiniteScroll(agents: Sequence<Agent>) {
    page("Infinite scroll") {
        table("class" to "table") {
            thead {
                tr {
                    th { +"Name" }
                    th { +"Email" }
                    th { +"ID" }
                }
            }
            tbody {
                agentsListInfinite(agents.take(10).toList(), 1)
            }
        }
    }
}

fun Html.agentsListInfinite(agents: List<Agent>, page: Int) {
    agents.dropLast(1).forEach { agent ->
        tr {
            td { +agent.name }
            td { +agent.email }
            td { +agent.id }
        }
    }
    val lastAgent = agents.last()
    tr(
        "hx-get" to "?page=${page}",
        "hx-trigger" to "revealed",
        "hx-swap" to "afterend"
    ) {
        td { +lastAgent.name }
        td { +lastAgent.email }
        td { +lastAgent.id }
    }
}
