package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.a
import nu.staldal.kotlin.html.h3
import nu.staldal.kotlin.html.li
import nu.staldal.kotlin.html.ul

fun Html.index() {
    page("Examples") {
        h3 { +"htmx UI" }
        ul("id" to "menu") {
            menuItem("/click-to-edit", "Click to edit")
            menuItem("/bulk-update", "Bulk update")
            menuItem("/click-to-load", "Click to load")
            menuItem("/infinite-scroll", "Infinite scroll")
            menuItem("/value-select", "Value select")
            menuItem("/modal-dialog", "Modal dialog using Bootstrap")
        }
        h3 { +"Complete use cases" }
        ul {
            menuItem("/todo-list", "To do list")
        }
    }
}

fun Html.menuItem(url: String, text: String) {
    li {
        a("href" to url) {
            +text
        }
    }
}
