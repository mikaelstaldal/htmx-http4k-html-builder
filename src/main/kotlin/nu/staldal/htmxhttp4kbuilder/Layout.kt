package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.body
import nu.staldal.kotlin.html.div
import nu.staldal.kotlin.html.h1
import nu.staldal.kotlin.html.h2
import nu.staldal.kotlin.html.head
import nu.staldal.kotlin.html.html
import nu.staldal.kotlin.html.link
import nu.staldal.kotlin.html.meta
import nu.staldal.kotlin.html.script
import nu.staldal.kotlin.html.title

private const val mainTitle = "htmx with http4k and kotlin-html-builder"

fun Html.page(subtitle: String, headExtra: Html.() -> Unit = {}, block: Html.() -> Unit) {
    html("lang" to "en") {
        head {
            meta("charset" to "UTF-8")
            meta("name" to "viewport",
                "content" to "width=device-width, initial-scale=1")
            script("type" to "text/javascript", "src" to "/htmx.min.js")
            script("type" to "text/javascript", "src" to "/_hyperscript.min.js")
            link(
                "rel" to "stylesheet",
                "href" to "/css/bootstrap.css"
            )
            headExtra()
            title {
                +mainTitle
            }
        }
        body {
            div("class" to "container") {
                h1 { +mainTitle }
                h2 { +subtitle }
                block()
            }
        }
    }
}
