package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.button
import nu.staldal.kotlin.html.div
import nu.staldal.kotlin.html.form
import nu.staldal.kotlin.html.input
import nu.staldal.kotlin.html.style
import nu.staldal.kotlin.html.table
import nu.staldal.kotlin.html.tbody
import nu.staldal.kotlin.html.td
import nu.staldal.kotlin.html.th
import nu.staldal.kotlin.html.thead
import nu.staldal.kotlin.html.tr

fun Html.bulkUpdate(contacts: Iterable<Contact>) {
    page("Bulk update", {
        style(
            """
            .htmx-settling tr.deactivate td {
                background: lightcoral;
            }
            .htmx-settling tr.activate td {
                background: darkseagreen;
            }
            tr td {
              transition: all 1.2s;
            }
            """
        )
    }) {
        div("hx-include" to "#checked-contacts", "hx-target" to "#tbody") {
            button("class" to "btn btn-primary me-1", "hx-put" to "/contacts/activate") {
                +"Activate"
            }
            button("class" to "btn btn-primary", "hx-put" to "/contacts/deactivate") {
                +"Deactivate"
            }
        }
        form("id" to "checked-contacts") {
            table("class" to "table") {
                thead {
                    tr {
                        th {
                            input(
                                "type" to "checkbox",
                                "_" to """
                                    on change
                                    get the <input[type=checkbox]/> in the <tbody/> in the closest <table/>
                                    set its checked to my checked
                                """
                            )
                        }
                        th { +"Name" }
                        th { +"Email" }
                        th { +"Status" }
                    }
                }
                tbody("id" to "tbody") {
                    contactsList(contacts.map { it to false }, false)
                }
            }
        }
    }
}


fun Html.contactsList(contacts: Iterable<Pair<Contact, Boolean>>, activate: Boolean) {
    contacts.forEach { (contact, wasMutated) ->
        tr("class" to if (wasMutated) (if (activate) "activate" else "deactivate") else "") {
            td {
                input("type" to "checkbox", "name" to "ids", "value" to contact.id)
            }
            td { +contact.name }
            td { +contact.email }
            td { +if (contact.active) "Active" else "Inactive" }
        }
    }
}
