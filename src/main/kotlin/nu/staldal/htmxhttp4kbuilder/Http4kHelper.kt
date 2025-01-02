package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.htmlDoc
import nu.staldal.kotlin.html.partialHtml
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Header

fun htmlPage(status: Status = Status.OK, block: Html.() -> Unit): Response = Response(status)
    .with(Header.CONTENT_TYPE of TEXT_HTML)
    .body(htmlDoc { block() })

fun htmlPartial(status: Status = Status.OK, block: Html.() -> Unit): Response = Response(status)
    .with(Header.CONTENT_TYPE of TEXT_HTML)
    .body(partialHtml { block() })
