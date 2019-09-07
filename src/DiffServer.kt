import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val server = embeddedServer(Netty, port = 80) {
        install(ContentNegotiation) {
            jackson {
                // Configure Jackson's ObjectMapper here
            }
        }

        routing {
            get("/doc") {

            }
        }
    }
}