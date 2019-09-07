import Documents._document_id
import Documents._document_text
import Documents._id
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DBHelper {
    init {
        Database.connect("jdbc:sqlite:documents.db", "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Documents)
        }
    }

    fun getDocumentById(document_id: Int): Map<Int,String> {
        val docs = mutableMapOf<Int,String>()
        transaction {
            (Documents.select { _document_id eq document_id }).forEach {
                docs[it[_id]] = it[_document_text]
            }
        }
        return docs
    }
}

object Documents: Table() {
    val _id = integer("id").autoIncrement().primaryKey()
    val _document_id = integer("document_id")
    val _document_text = text("document_text")
}