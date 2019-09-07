import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

val db = DBHelper()

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson {
                // Configure Jackson's ObjectMapper here
            }
        }

        routing {
            get("/doc") {
                //call.respond(mapOf("status" to "OK"))
                val docId = call.parameters["id"]
                docId?.let {
                    call.respond(db.getDocumentById(it.toInt()))
                } ?: call.respondText("Document id is required!")
            }
        }
    }
    server.start(wait = true)
}