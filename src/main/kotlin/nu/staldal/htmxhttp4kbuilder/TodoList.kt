package nu.staldal.htmxhttp4kbuilder

import nu.staldal.kotlin.html.Html
import nu.staldal.kotlin.html.button
import nu.staldal.kotlin.html.input
import nu.staldal.kotlin.html.table
import nu.staldal.kotlin.html.tbody
import nu.staldal.kotlin.html.td
import nu.staldal.kotlin.html.tfoot
import nu.staldal.kotlin.html.th
import nu.staldal.kotlin.html.thead
import nu.staldal.kotlin.html.tr

fun Html.todoList(todoList: Iterable<Todo>) {
    page("To do list") {
        table("class" to "table table-hover") {
            thead {
                tr {
                    th("scope" to "col") {
                        +"What to do"
                    }
                    th("scope" to "col") {
                        +"done"
                    }
                    th("scope" to "col") {
                        unsafe("&nbsp;")
                    }
                }
            }
            tbody("id" to "todo-list") {
                todoList.forEach { todo ->
                    todoRow(todo)
                }
            }
            tfoot {
                tr {
                    td("colspan" to "2") {
                        input(
                            "class" to "form-control",
                            "id" to "description",
                            "name" to "description",
                            "placeholder" to "To do...",
                            "required" to "true",
                            "type" to "text",
                            "autofocus" to "autofocus"
                        )
                    }
                }
                td {
                    button(
                        "class" to "btn btn-primary",
                        "_" to "on htmx:afterRequest put '' into #description.value",
                        "hx-post" to "/todo",
                        "hx-include" to "#description",
                        "hx-target" to "#todo-list",
                        "hx-swap" to "beforeend"
                    ) {
                        +"Add"
                    }
                }
            }
        }
    }
}

fun Html.todoRow(todo: Todo) {
    tr {
        td {
            +todo.description
        }
        td {
            input(
                "type" to "checkbox",
                "name" to "done",
                "value" to "true",
                "checked" to todo.done,
                "hx-patch" to "/todo/${todo.id}",
                "hx-target" to "closest tr",
                "hx-swap" to "outerHTML"
            )
        }
        td {
            button(
                "class" to "btn btn-danger",
                "hx-confirm" to "Are you sure?",
                "hx-delete" to "/todo/${todo.id}",
                "hx-target" to "closest tr",
                "hx-swap" to "outerHTML swap:0.5s"
            ) {
                +"Delete"
            }
        }
    }
}
