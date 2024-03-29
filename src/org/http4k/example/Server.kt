package org.http4k.example

import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    SlackBotApp()
        .asServer(SunHttp(System.getenv("PORT").toIntOrNull() ?: 8080))
        .start()
}

