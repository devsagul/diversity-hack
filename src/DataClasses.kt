data class TextDiff(var start: Int, var end: Int, var Text: String, var type: String)

data class Paragraph(var hash: Int, var text: String) {
    var authorId = 0
    var p_id: Int = 0
    val textDiffs = mutableListOf<TextDiff>()
    var e = false
    var d = false
    var i = false
    var eq = false
    var nodiff = false
    var order = 0
    var goesAfter = 0

    fun getMapped(): Map<String,Any> { // inache etot pizdec norm v json ne prevratit'
        var diffType = ""
        diffType = if (e) "changed" else if (d) "removed" else if (i) "inserted" else "no" // kurwa pizdec
        val changes = mutableListOf<Map<String,Any>>()
        textDiffs.forEach {
            val map = mutableMapOf<String,Any>()
            map["type"] = it.type
            map["start"] = it.start
            map["end"] = it.end
            map["text"] = it.Text
            changes += map
        }
        val mappedParagraph = mutableMapOf<String,Any>()
        mappedParagraph["diff"] = diffType
        mappedParagraph["paragraph_id"] = p_id
        mappedParagraph["author_id"] = authorId
        mappedParagraph["order"] = order
        mappedParagraph["changes"] = changes
        return mappedParagraph
    }
}

data class DocVersion(var authorId: Int, val paragraphs: List<Paragraph>) {
    fun paragraphsAsString(): String {
        var res = ""
        paragraphs.forEach { res += "${it.hash} " }
        return res
    }
}