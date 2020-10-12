package org.http4k.example

import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    SlackBotApp()
        .asServer(SunHttp(8080)).start()
}