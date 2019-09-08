import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.toMap
import java.lang.IllegalArgumentException

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
                val df = Differ()
                val docId = call.parameters["id"]
                docId?.let {
                    val orig = db.getOriginalWithAuthorById(docId.toInt())
                    val copies = db.getDocumentsWithAuthorsById(docId.toInt())
                    val res = df.analyzeDocuments(orig.second, orig.first, copies)
                    call.respond(mapOf("result" to res))
                } ?: call.respondText("Document id is required!")
            }

            post("/add") {
                val params = call.receiveParameters()
                try {
                    require(params.contains("author_id") && params.contains("text")
                            && params.contains("document_id")) {
                        "One or more parameters are missing"
                    }
                    db.addDocument(text = params["text"]!!, author_id = params["author_id"]!!.toInt(),
                        document_id = params["document_id"]!!.toInt(), original = db.isDocumentExists(params["document_id"]!!.toInt()))
                    call.respond(mapOf("respond" to "OK"))
                } catch (e: IllegalArgumentException) {call.respond(mapOf("respond" to "error", "result" to "one or more parameters are missing"))}
            }

            get("/dummy") {

                val df = Differ()
                val edited = mapOf<Int,String>(2 to "  So she was considering in her own mind (as well as she could, for the hot day made her feel very sleepy and stupid), whether the pleasure of making a daisy-chain would be worth the trouble of getting up and picking the daisies, when suddenly a White Rabbit with pink eyes ran close by her.\n" +
                        "  There was nothing so less remarkable in that; nor did Alice think it so VERY much out of the way to hear the Rabbit say to itself, `Oh dear! Oh dear! I wanna kill myself!' (when she thought it over afterwards, it occurred to her that she ought to have wondered at this, but at the time it all seemed quite natural); but when the Rabbit actually TOOK A WATCH OUT OF ITS WAISTCOAT- POCKET, and looked at it, and then hurried on, Alice started to her feet, for it flashed across her mind that she had never before seen a rabbit with either a waistcoat-pocket, or a watch to take out of it, and burning with curiosity, she ran across the field after it, and fortunately was just in time to see it pop down a large rabbit-hole under the hedge.",
                    3 to "Down the Semen's hole\n" +"Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do: once or twice she had peeped into the book her sister was reading, but it had no pictures or conversations in it, `and what is the use of a book,' thought Alice `without pictures or conversation?'\n" +
                            "  So she was considering in her own mind (as well as she could, for the hot day made her feel very sleepy and stupid), whether the pleasure of making a daisy-chain would be worth the trouble of getting up and picking the daisies, when suddenly a White Rabbit with pink eyes ran close by her.\n" +
                            "  There was nothing at all remarkable in that; nor did Alice think it so VERY much out of the way to hear the Rabbit say to itself, `Oh dear! Oh dear! I shall be late!' (when she thought it over afterwards, it occurred to her that she ought to have wondered at this, but at the time it all seemed quite natural); but when the Rabbit actually TOOK A WATCH OUT OF ITS WAISTCOAT- POCKET, and looked at it, and then hurried on, Alice started to her feet, for it flashed across her mind that she had never before seen a rabbit with either a waistcoat-pocket, or a watch to take out of it, and burning with curiosity, she ran across the field after it, and fortunately was just in time to see it pop down a large rabbit-hole under the hedge.")
                val res = df.analyzeDocuments("Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do: once or twice she had peeped into the book her sister was reading, but it had no pictures or conversations in it, `and what is the use of a book,' thought Alice `without pictures or conversation?'\n" +
                        "  So she was considering in her own mind (as well as she could, for the hot day made her feel very sleepy and stupid), whether the pleasure of making a daisy-chain would be worth the trouble of getting up and picking the daisies, when suddenly a White Rabbit with pink eyes ran close by her.\n" +
                        "  There was nothing so VERY remarkable in that; nor did Alice think it so VERY much out of the way to hear the Rabbit say to itself, `Oh dear! Oh dear! I shall be late!' (when she thought it over afterwards, it occurred to her that she ought to have wondered at this, but at the time it all seemed quite natural); but when the Rabbit actually TOOK A WATCH OUT OF ITS WAISTCOAT- POCKET, and looked at it, and then hurried on, Alice started to her feet, for it flashed across her mind that she had never before seen a rabbit with either a waistcoat-pocket, or a watch to take out of it, and burning with curiosity, she ran across the field after it, and fortunately was just in time to see it pop down a large rabbit-hole under the hedge.",1,
                    edited)
                call.respond(mapOf("result" to res))
            }
        }
    }
    server.start(wait = true)
}

fun addDocs() {
    db.addDocument(author_id = 1, document_id = 11, original = true, text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sodales, lectus a fermentum cursus, massa neque dictum mi, at egestas lacus massa eu dolor. Vivamus quis mattis tellus. Etiam lobortis accumsan ex, vitae ullamcorper lectus. Ut et imperdiet ex. Etiam at nibh sit amet eros efficitur accumsan. Etiam sit amet gravida lectus. Morbi eu quam id magna blandit egestas vitae vitae tortor. Mauris lorem ex, commodo eget dui in, pharetra vestibulum est. Suspendisse mattis metus vel imperdiet dapibus.\n" +
            "Nam vel ante eget leo efficitur placerat quis ac purus. Vestibulum mollis commodo lacus non scelerisque. Proin tincidunt odio augue, et congue diam bibendum sit amet. Sed bibendum eget sapien suscipit tincidunt. Quisque sit amet libero neque. In aliquam, tortor in convallis malesuada, libero metus vulputate erat, ac tempus sapien diam egestas purus. Nam auctor eros vitae elit mollis, at tincidunt dolor egestas. Quisque sed dui eu sapien posuere fermentum eu id lorem. Suspendisse potenti.\n" +
            "Donec tempor congue ante eget malesuada. Fusce non sagittis velit, non ultricies erat. Ut porta urna vitae tincidunt blandit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed aliquam ac eros in vulputate. Sed ut lacus auctor ligula facilisis bibendum. Nullam quis mauris volutpat, auctor dui sit amet, blandit nibh.\n" +
            "Sed semper ligula quis mi volutpat fermentum. Cras dui ex, varius id luctus ut, convallis in ligula. Nam eleifend erat efficitur, ornare arcu et, blandit tellus. Vivamus auctor feugiat nunc et dapibus. Proin tempus orci sapien. Proin in diam ac sapien egestas dapibus. In orci justo, pretium ac orci ut, finibus pulvinar quam. Duis sed tempus enim, nec aliquam elit. Phasellus vitae eleifend nisi, eget congue massa. Aenean lorem nunc, blandit in ante nec, suscipit tempus ligula.\n" +
            "Phasellus ultrices libero mauris, vitae venenatis leo maximus nec. Sed massa libero, feugiat ut nulla in, dignissim porta massa. Curabitur at sodales libero. Ut at sem risus. Quisque semper at lacus vitae tincidunt. Integer erat sapien, porttitor ut maximus non, pharetra vitae diam. Praesent faucibus est in ligula accumsan tristique. Nunc sed dui at orci egestas elementum eu eu metus. Nam eu sem eros. Proin libero eros, mollis ac viverra volutpat, consectetur et turpis. Maecenas egestas, diam nec tristique convallis, mauris leo tincidunt ante, ut mollis sem nulla ut lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce tempus tincidunt nisi a hendrerit. Fusce commodo magna eget libero euismod condimentum. Maecenas tristique velit velit, ut laoreet sapien consectetur eget. Maecenas pretium interdum tincidunt.")

    db.addDocument(author_id = 5, document_id = 11, text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sodales, lectus a fermentum cursus, massa neque dictum mi, at egestas lacus massa eu dolor. Vivamus quis mattis tellus. Etiam lobortis accumsan ex, vitae ullamcorper lectus. Ut et imperdiet ex. Etiam at nibh sit amet eros efficitur accumsan. Etiam sit amet gravida lectus. Morbi eu quam id magna blandit egestas vitae vitae tortor. Mauris lorem ex, commodo eget dui in, pharetra vestibulum est. Suspendisse mattis metus vel imperdiet dapibus.\n" +
            "Nam vel cheto tam leo efficitur placerat quis ac purus. Vestibulum mohuelisllis commodo lacus non scelerisque. Proin tincidunt odio augue, et congue diam bibendum sit amet. Sed bibendum eget sapien suscipit tincidunt. Quisque sit amet libero neque. In aliquam, tortor in convallis malesuada, libero metus vulputate erat, ac tempus sapien diam egestas purus. Nam auctor eros vitae elit mollis, at tincidunt dolor egestas. Quisque sed dui eu sapien posuere fermentum eu id lorem. Suspendisse potenti.\n" +
            "Donec tempor congue ante eget malesuada. Fusce non sagittis velit, non ultricies erat. Ut porta urna vitae tincidunt blandit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed aliquam ac eros in vulputate. Sed ut lacus auctor ligula facilisis bibendum. Nullam quis mauris volutpat, auctor dui sit amet, blandit nibh.\n" +
            "Paragraph in the middle pf the doc.\n" +
            "Sed semper ligula quis mi volutpat fermentum. Cras dui ex, varius id luctus ut, convallis in ligula. Nam eleifend erat efficitur, ornare arcu et, blandit tellus. Vivamus auctor feugiat nunc et dapibus. Proin tempus orci sapien. Proin in diam ac sapien egestas dapibus. In orci justo, pretium ac orci ut, finibus pulvinar quam. Duis sed tempus enim, nec aliquam elit. Phasellus vitae eleifend nisi, eget congue massa. Aenean lorem nunc, blandit in ante nec, suscipit tempus ligula.\n" +
            "Phasellus ultrices libero mauris, vitae venenatis leo maximus nec. Sed massa libero, feugiat ut nulla in, dignissim porta massa. Curabitur at sodales libero. Ut at sem risus. Quisque semper at lacus vitae tincidunt. Integer erat sapien, porttitor ut maximus non, pharetra vitae diam. Praesent faucibus est in ligula accumsan tristique. Nunc sed dui at orci egestas elementum eu eu metus. Nam eu sem eros. Proin libero eros, mollis ac viverra volutpat, consectetur et turpis. Maecenas egestas, diam nec tristique convallis, mauris leo tincidunt ante, ut mollis sem nulla ut lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce tempus tincidunt nisi a hendrerit. Fusce commodo magna eget libero euismod condimentum. Maecenas tristique velit velit, ut laoreet sapien consectetur eget. Maecenas pretium interdum tincidunt.\n" +
            "New paragraph. Lol.")

    db.addDocument(author_id = 6, document_id = 11, text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sodales, lectus a fermentum cursus, massa neque dictum mi, at egestas lacus massa eu dolor. Vivamus quis mattis tellus. Etiam lobortis accumsan ex, vitae ullamcorper lectus. Ut et imperdiet ex. Etiam at nibh sit amet eros efficitur accumsan. Etiam sit amet gravida lectus. Morbi eu quam id magna blandit egestas vitae vitae tortor. Mauris lorem ex, commodo eget dui in, pharetra vestibulum est. Suspendisse mattis metus vel imperdiet dapibus.\n" +
            "Donec tempor congue ante eget malesuada. Fusce non sagittis velit, non ultricies erat. Ut porta urna vitae tincidunt blandit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed aliquam ac eros in vulputate. Sed ut lacus auctor ligula facilisis bibendum. Nullam quis mauris volutpat, auctor dui sit amet, blandit nibh.\n" +
            "Sed semper ligula ligma balls mi volutpat fermentum. Cras dui ex, varius id luctus ut, convallis in ligula. Nam eleifend erat efficitur, ornare arcu et, he's changing tellus. Vivamus auctor feugiat nunc et dapibus. Proin tempus orci sapien. Proin in diam ac sapien egestas dapibus. In orci justo, pretium ac orci ut, finibus pulvinar quam. Duis sed tempus enim, nec aliquam elit. Phasellus vitae eleifend nisi, eget congue massa. Aenean lorem nunc, blandit in ante nec, suscipit tempus ligula.\n" +
            "Paragraph, but from other dude.\n" +
            "Phasellus ultrices libero mauris, vitae venenatis leo fuking text. Sed massa libero, feugiat ut nulla in, dignissim porta massa. Curabitur at sodales libero. Ut at sem risus. Quisque semper at lacus vitae tincidunt. Integer erat sapien, porttitor ut maximus non, pharetra vitae diam. Praesent faucibus est in ligula accumsan tristique. Nunc sed dui at orci egestas elementum eu eu metus. Nam eu sem eros. Proin libero eros, mollis ac viverra volutpat, consectetur et turpis. Maecenas egestas, diam nec tristique convallis, mauris leo tincidunt ante, ut mollis sem nulla ut lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce tempus tincidunt nisi a hendrerit. Fusce commodo magna eget libero euismod condimentum. Maecenas tristique velit velit, ut laoreet sapien consectetur eget. Maecenas pretium interdum tincidunt.")
    db.addDocument(author_id = 7, document_id = 11, text = "Ya sverhu.\n" +
            "Nam vel ante eget leo menya izmenili, a v drugom meste udalili ac purus. Vestibulum mollis commodo lacus non scelerisque. Proin tincidunt odio augue, et congue diam bibendum sit amet. Sed bibendum eget sapien suscipit tincidunt. Quisque sit amet libero neque. In aliquam, tortor in convallis malesuada, libero metus vulputate erat, ac tempus sapien diam egestas purus. Nam auctor eros vitae elit mollis, at tincidunt dolor egestas. Quisque sed dui eu sapien posuere fermentum eu id lorem. Suspendisse potenti.\n" +
            "Ya gde-to eshe.\n" +
            "Donec tempor congue ante eget malesuada. Fusce non sagittis velit, non ultricies erat. Ut porta urna vitae tincidunt blandit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed aliquam ac eros in vulputate. Sed ut lacus auctor ligula facilisis bibendum. Nullam quis mauris volutpat, auctor dui sit amet, blandit nibh.\n" +
            "Sed semper ligula quis mi volutpat fermentum. Cras dui ex, varius id luctus ut, convallis in ligula. Nam eleifend erat efficitur, ornare arcu et, blandit tellus. Vivamus auctor feugiat nunc et dapibus. Proin tempus orci sapien. Proin in diam ac sapien egestas dapibus. In orci justo, pretium ac orci ut, finibus pulvinar quam. Duis sed tempus enim, nec aliquam elit. Phasellus vitae eleifend nisi, eget congue massa. Aenean lorem nunc, blandit in ante nec, suscipit tempus ligula.\n" +
            "Phasellus kek libero mauris, vitae venenatis leo maximus lol. Sed massa libero porvalo, feugiat ut nulla in, dignissim porta massa. Curabitur at sodales libero. Ut at sem risus. Quisque semper at lacus vitae tincidunt. Integer erat sapien, porttitor ut maximus non, pharetra vitae diam. Praesent faucibus est in ligula accumsan tristique. Nunc sed dui at orci egestas elementum eu eu metus. Nam eu sem eros. Proin libero eros, mollis ac viverra volutpat, consectetur et turpis. Maecenas egestas, diam nec tristique convallis, mauris leo tincidunt ante, ut mollis sem nulla ut lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce tempus tincidunt nisi a hendrerit. Fusce commodo magna eget libero euismod condimentum. Maecenas tristique velit velit, ut laoreet sapien consectetur eget. Maecenas pretium interdum tincidunt.")
}
