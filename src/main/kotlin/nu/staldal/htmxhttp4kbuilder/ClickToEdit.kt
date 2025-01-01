package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.button
import nu.staldal.kotlin.html.div
import nu.staldal.kotlin.html.form
import nu.staldal.kotlin.html.input
import nu.staldal.kotlin.html.label

fun Html.clickToEdit(person: Person) {
    page("Click to edit") {
        viewPerson(person)
    }
}

fun Html.viewPerson(person: Person) {
    div("hx-target" to "this", "hx-swap" to "outerHTML") {
        viewControl("First Name", "firstName", person.firstName)
        viewControl("Last Name", "lastName", person.lastName)
        viewControl("Email", "email", person.email)
        div {
            button("class" to "btn btn-primary", "hx-get" to "/person/edit") {
                +"Click To Edit"
            }
        }
    }
}

fun Html.editPerson(person: Person) {
    form(
        "hx-put" to "/person",
        "hx-target" to "this",
        "hx-swap" to "outerHTML"
    ) {
        editControl("First Name", "firstName", person.firstName)
        editControl("Last Name", "lastName", person.lastName)
        editControl("Email", "email", person.email)
        div {
            button("class" to "btn btn-primary me-1") {
                +"Submit"
            }
            button("class" to "btn btn-secondary", "hx-get" to "/person") {
                +"Cancel"
            }
        }
    }
}

fun Html.viewControl(label: String, id: String, theValue: String) {
    div("class" to "row mb-1") {
        label("class" to "col-sm-1 col-form-label", "for" to id) {
            +label
        }
        div("class" to "col-sm-4") {
            input(
                "class" to "form-control-plaintext",
                "type" to "text",
                "readonly" to true,
                "name" to id,
                "value" to theValue
            )
        }
    }
}

fun Html.editControl(label: String, id: String, theValue: String) {
    div("class" to "row mb-1") {
        label("class" to "col-sm-1 col-form-label", "for" to id) {
            +label
        }
        div("class" to "col-sm-4") {
            input(
                "class" to "form-control",
                "type" to "text",
                "name" to id,
                "value" to theValue
            )
        }
    }
}
