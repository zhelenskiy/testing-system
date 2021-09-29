package ru.testing

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.testing.plugins.module
import ru.testing.server.Server

fun main() {
    Server(1).run()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}
