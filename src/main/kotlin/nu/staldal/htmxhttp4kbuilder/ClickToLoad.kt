package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.button
import nu.staldal.kotlin.html.table
import nu.staldal.kotlin.html.tbody
import nu.staldal.kotlin.html.td
import nu.staldal.kotlin.html.th
import nu.staldal.kotlin.html.thead
import nu.staldal.kotlin.html.tr

fun Html.clickToLoad(agents: Sequence<Agent>) {
    page("Click to load") {
        table("class" to "table") {
            thead {
                tr {
                    th { +"Name" }
                    th { +"Email" }
                    th { +"ID" }
                }
            }
            tbody {
                agentsList(agents.take(10).toList(), 1)
            }
        }
    }
}

fun Html.agentsList(agents: List<Agent>, page: Int) {
    agents.forEach { agent ->
        tr {
            td { +agent.name }
            td { +agent.email }
            td { +agent.id }
        }
    }
    tr("id" to "replaceMe") {
        td("colspan" to "3") {
            button(
                "class" to "btn btn-light",
                "hx-get" to "?page=${page}",
                "hx-target" to "#replaceMe",
                "hx-swap" to "outerHTML"
            ) {
                +"Load More Agents..."
            }
        }
    }
}
