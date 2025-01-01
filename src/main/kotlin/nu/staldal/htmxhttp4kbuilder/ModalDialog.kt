package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.button
import nu.staldal.kotlin.html.div
import nu.staldal.kotlin.html.h5
import nu.staldal.kotlin.html.p

fun Html.modalDialog() {
    page("Modal dialog") {
        button(
            "class" to "btn btn-primary",
            "hx-get" to "/modal",
            "hx-target" to "#modals-here",
            "hx-trigger" to "click",
            "_" to """
                on htmx:afterOnLoad 
                wait 10ms 
                then add .show to #modal 
                then add .show to #modal-backdrop
                """
        ) {
            +"Open modal"
        }
        div("id" to "modals-here")
    }
}

fun Html.dialogContent() {
    div("class" to "modal-backdrop fade", "id" to "modal-backdrop", "style" to "display:block;")
    div(
        "class" to "modal fade",
        "id" to "modal",
        "tabindex" to "-1",
        "style" to "display:block;"
    ) {
        div("class" to "modal-dialog modal-dialog-centered") {
            div("class" to "modal-content") {
                div("class" to "modal-header") {
                    h5("class" to "modal-title") { +"Modal title" }
                }
                div("class" to "modal-body") {
                    p { +"Modal body text goes here." }
                }
                div("class" to "modal-footer") {
                    button(
                        "class" to "btn btn-secondary",
                        "type" to "button",
                        "_" to """
                            on click 
                            wait 10ms 
                            then remove .show from #modal 
                            then remove .show from #modal-backdrop 
                            then wait 200ms 
                            then remove #modal-backdrop 
                            then remove #modal
                            """
                    ) {
                        +"Close"
                    }
                }
            }
        }
    }
}
