package nu.staldal.hmxhttp4kbuilder

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import nu.staldal.htmxhttp4kbuilder.DataStore
import nu.staldal.htmxhttp4kbuilder.createApp
import org.http4k.core.Request
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasStatus
import org.http4k.testing.Approver
import org.http4k.testing.Html5ApprovalTest
import org.http4k.testing.hasApprovedContent
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(Html5ApprovalTest::class)
class IndexTest {
    val app = createApp(DataStore())

    @Test
    fun index(approver: Approver) {
        assertThat(app(Request(GET, "/")), hasStatus(OK).and(approver.hasApprovedContent()))
    }
}
