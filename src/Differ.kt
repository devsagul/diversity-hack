import org.apache.commons.lang3.StringUtils
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch

class Differ {

    private var currId = 1
    private val uniqueParagraphs = mutableMapOf<Int,Int>()
    private val paragraphOrder = arrayListOf<Int>() //ETO ESHE NE SAMYJ PIZDEC
    private val pOrderMap  = mutableMapOf<Int,Int>() //A VOT ETO UZHE DA
    private val changesList = mutableListOf<Paragraph>()


        private fun isFamiliar(original: String, edited: String): Boolean {
            return StringUtils.getLevenshteinDistance(original, edited)/original.length.toDouble() < 0.45
        }

        private fun getParagraphs(text: String): List<Paragraph> {
            val pargs = mutableListOf<Paragraph>()
            text.split("\n").forEach {
                pargs += Paragraph(hash = it.hashCode(), text = it)
            }
            return pargs
        }

    fun analyzeDocuments(originalDoc: String, originalAuthor: Int, editedDocs: Map<Int,String>): List<Map<String,Any>> {
        val original = DocVersion(originalAuthor,getParagraphs(originalDoc))
        val edited = mutableListOf<DocVersion>()
        editedDocs.forEach {
            val doc = DocVersion(it.key,getParagraphs(it.value))
            doc.paragraphs.forEach { p -> p.authorId = doc.authorId }
            edited += doc
        }
        original.paragraphs.forEach {   // ))
            it.p_id = currId++
            it.authorId = original.authorId
            uniqueParagraphs[it.hash] = it.p_id
            it.nodiff = true
            changesList += it
            paragraphOrder.add(it.p_id)
        }
        edited.forEach {
            findDiffs(version = it, original = DocVersion(originalAuthor,getParagraphs(originalDoc)))
        }
        changesList.forEach { change ->
            if (change.i) {
                for (n in 0 until paragraphOrder.size) {
                    if (n == change.goesAfter) {
                        paragraphOrder.add(n,change.p_id)
                        break
                    }
                }
            }
        }
        for (n in 0 until paragraphOrder.size) {
            pOrderMap[paragraphOrder[n]] = n
        }
        changesList.forEach {
            it.order = pOrderMap[it.p_id]!!
        }

        //
        val dates = mapOf(1 to "05.09.2019 17:03",
            2 to "07.09.2019 11:04",
            3 to "07.09.2019 19:45",
            7 to "08.08.2019 09:21")
        changesList.forEach {
            it.data = dates[it.authorId]?: "07.09.2019 11:06"
        }
        //
        val preparedList = mutableListOf<Map<String,Any>>()
        changesList.forEach {
            preparedList += it.getMapped()
        }
        return preparedList
    }

    private fun findDiffs(version: DocVersion, original: DocVersion) { // NU ETO PIZDEC
        val delPool = mutableListOf<Paragraph>()
        val insPool = mutableListOf<Paragraph>()
        val edPool = mutableMapOf<Paragraph,Paragraph>()

        var cch ='\uAAAA' // S TAKYM KOSTYLEM I PADAT' NE STRASHNO

        var versionHashString = ""
        var origHashString = ""
        val originalPairs = mutableMapOf<Char,Int>()
        val versionPairs = mutableMapOf<Char,Int>()
        original.paragraphs.forEach {
            originalPairs[cch] = it.hash
            origHashString += "$cch "
            cch += 1
        }
        version.paragraphs.forEach {
            var key = '\uAAAA'
            if (originalPairs.containsValue(it.hash)) {
                key = originalPairs.filterValues {va -> va == it.hash }.keys.first()
            } else {
                key = cch
            }
            versionPairs[key] = it.hash
            versionHashString += "$key "
            cch += 1
        }
        origHashString = origHashString.trim()
        versionHashString = versionHashString.trim()
        val diffMatchPatch = DiffMatchPatch()
        val diffs = diffMatchPatch.diffMain(origHashString,versionHashString)
        var lastEqId = 0
        diffs.forEach { // MDA
            when {
                it.operation == DiffMatchPatch.Operation.DELETE -> { // GOVNOKOD AS IS
                    it.text.split(" ").forEach { hashStr ->
                        var cc = hashStr.trim()
                        if (cc != "") {
                            val hash = originalPairs[cc[0]]!!
                            original.paragraphs.forEach { p ->
                                if (p.hash == hash) {
                                    p.d = true
                                    delPool.add(p)
                                }
                            }
                        }
                    }
                }
                it.operation == DiffMatchPatch.Operation.INSERT -> { // GOVNOKOD AS IT IS, REPEATED
                    it.text.split(" ").forEach { hashStr ->
                        var cc = hashStr.trim()
                        if (cc != "") {
                            val hash = versionPairs[cc[0]]!!
                            version.paragraphs.forEach { p ->
                                if (p.hash == hash) {
                                    p.i = true
                                    p.goesAfter = lastEqId
                                    insPool.add(p)
                                }
                            }
                        }
                    }
                }
                it.operation == DiffMatchPatch.Operation.EQUAL -> {//
                    it.text.split(" ").forEach { hashStr ->
                        var cc = hashStr.trim()
                        if (cc != "") {
                            val hash = versionPairs[cc[0]]!!
                            lastEqId = uniqueParagraphs[hash]!!
                        }
                    }
                }
            }
        }
            //
            val insUsed = mutableListOf<Paragraph>()
            val delUsed = mutableListOf<Paragraph>()
            insPool.forEach { ins ->
                for (d in delPool.size-1 downTo 0){
                    val del = delPool[d]

                    if (isFamiliar(ins.text,del.text) && !insUsed.contains(ins) && !delUsed.contains(del)) {
                        ins.i = false
                        del.d = false
                        edPool[del] = ins
                        delPool.remove(del)
                        insUsed += ins
                        delUsed += del
                    }
                }
            }
            insUsed.forEach { used ->
                insPool.removeIf { used.hash == it.hash }
            }
            delUsed.forEach { delPool.minus(it) }
            //
            delPool.forEach { p ->
                p.p_id = uniqueParagraphs[p.hash]!! //hmmm
                changesList += p
            }
            insPool.forEach { p ->
                uniqueParagraphs.getOrPut(p.hash) {currId++}
                p.p_id = uniqueParagraphs[p.hash]!!
                changesList += p
            }
            //
            edPool.forEach { edited -> // MDA
                val orig = edited.key
                val variant = edited.value
                variant.e = true
                val textDiffs = diffMatchPatch.diffMain(orig.text,variant.text)
                var currentSymbol = 0
                textDiffs.forEach { diff ->
                    when {
                        diff.operation == DiffMatchPatch.Operation.DELETE -> {
                            variant.textDiffs += TextDiff(currentSymbol,currentSymbol+diff.text.length,"","del")
                            currentSymbol += diff.text.length
                        }
                        diff.operation == DiffMatchPatch.Operation.INSERT -> {
                            variant.textDiffs += TextDiff(currentSymbol +1, currentSymbol +1, diff.text, "ins")
                            currentSymbol += diff.text.length
                        }
                        diff.operation == DiffMatchPatch.Operation.EQUAL -> currentSymbol += diff.text.length
                    }
                }
                variant.p_id = uniqueParagraphs[orig.hash]!!
                changesList += variant
            }

    }
}