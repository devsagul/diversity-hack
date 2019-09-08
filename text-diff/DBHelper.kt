import Documents._author_id
import Documents._document_id
import Documents._document_text
import Documents._id
import Documents._original
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.and

class DBHelper {
    init {
        Database.connect("jdbc:sqlite:documents.db", "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Documents)
        }
    }

    fun getDocumentsWithAuthorsById(document_id: Int): Map<Int,String> {
        val docs = mutableMapOf<Int,String>()
        transaction {
            (Documents.select { _document_id eq document_id and not(_original)}).forEach {
                docs[it[_author_id]] = it[_document_text]
            }
        }
        return docs
    }


    fun getOriginalWithAuthorById(document_id: Int): Pair<Int,String> {
        var doc = 0 to ""
        transaction {
            (Documents.select { _document_id eq document_id and _original}).forEach {
                doc = it[_author_id] to it[_document_text]
            }
        }
        return doc
    }
    fun addDocument(text: String, author_id: Int, document_id: Int, original: Boolean = false) {
        transaction {
            Documents.insert {
                it[_document_id] = document_id
                it[_document_text] = text
                it[_author_id] = author_id
                it[_original] = original
            }
        }
    }

    fun isDocumentExists(document_id: Int): Boolean {
        var docExists = true
        transaction {
            docExists = !Documents.select { _document_id eq document_id }.empty()
        }
        return docExists
    }

}

object Documents: Table() {
    val _id = integer("id").autoIncrement().primaryKey()
    val _document_id = integer("document_id")
    val _document_text = text("document_text")
    val _author_id = integer("author_id")
    val _original = bool("original")
}