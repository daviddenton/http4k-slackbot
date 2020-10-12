package org.http4k.example

import com.slack.api.Slack
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

fun SlackBotApp(token: String): RoutingHttpHandler {
    val appRoutes = AppRoutes(Slack.getInstance(), token)
    return routes(
        "/kotlin-slackbot-github" bind POST to appRoutes.gitHub,
        "/json/gson" bind GET to appRoutes.jackson,
        "/" bind GET to appRoutes.home
    )
}

class AppRoutes(
    private val slack: Slack,
    private val token: String
) {
    val home: HttpHandler = {
        val response = slack.methods(token).chatPostMessage {
            it.channel("#general").text("Hello :wave:")
        }
        Response(OK).body("Response is: $response")
    }

    val jackson: HttpHandler = {
        Response(OK).body(Jackson.asFormatString(mapOf("hello" to "world")))
    }

    val gitHub: HttpHandler = {
        val request = Jackson.asA<GithubPushEvent>(it.bodyString())
        val responses = mutableListOf<Any>()

        val response = slack.methods(token).chatPostMessage {
            it.channel("#kotlin-slackbot")
                .text(
                    """
                    New commit pushed to `${request.repository.full_name}` by ${request.pusher.name}
                    > ${request.head_commit.message}
                    ${request.head_commit.url}
                """.trimIndent()
                )
        }

        responses.add(response)

        Response(OK).body("Response is: $responses")
    }
}
