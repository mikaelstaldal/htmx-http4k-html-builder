package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.div
import nu.staldal.kotlin.html.label
import nu.staldal.kotlin.html.option
import nu.staldal.kotlin.html.select

fun Html.valueSelect(makes: List<IdName>) {
    page("Value select") {
        div {
            div("class" to "row mb-3") {
                label(
                    "class" to "col-sm-1 col-form-label",
                    "for" to "make"
                ) {
                    +"Make"
                }
                div("class" to "col-sm-2") {
                    select(
                        "class" to "form-select",
                        "name" to "make",
                        "hx-get" to "/models",
                        "hx-target" to "#model",
                        "hx-indicator" to ".htmx-indicator"
                    ) {
                        option("value" to "", "selected" to true)
                        options(makes)
                    }
                }
            }
            div("class" to "row mb-3") {
                label("class" to "col-sm-1 col-form-label", "for" to "model") {
                    +"Model"
                }
                div("class" to "col-sm-2") {
                    select(
                        "class" to "form-select",
                        "id" to "model",
                        "name" to "model"
                    )
                }
            }
        }
    }
}

fun Html.options(choices: List<IdName>) {
    choices.forEach { choice ->
        option("value" to choice.id) {
            +choice.name
        }
    }
}
