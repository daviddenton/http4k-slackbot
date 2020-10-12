package org.http4k.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SlackBotAppTest {
    @Test
    @Disabled
    fun `root route`() {
        assertThat(SlackBotApp("token")(Request(GET, "/")), hasStatus(OK).and(hasBody("HELLO WORLD!")))
    }
}
