package joly.sylvain.cms

import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.response.respond
import joly.sylvain.cms.model.Articles
import joly.sylvain.cms.tpl.IndexContext

class MysqlModel(val pool: ConnectionPool): Model {
    override fun getArticleList(): List<Articles> {
        val list = ArrayList<Articles>();


        pool.useConnection { connection ->

            connection.prepareStatement("SELECT id, title, text FROM article").use { stmt ->
                stmt.executeQuery().use { results ->
                    while (results.next()) {
                        list += Articles(results.getInt("id"), results.getString("title"), null)
                    }
                }

            }
        }
        return list
    }

    override fun getArticle(id: Int): Articles? {
        pool.useConnection { connection ->

            connection.prepareStatement("SELECT * FROM article WHERE id = ?").use { stmt ->
                stmt.setInt(1, id) // en JDBC le premier index commence à 1
                stmt.executeQuery().use { result ->
                    if (result.next()) {
                        return Articles(result.getInt("id"), result.getString("title"), result.getString("text"))
                    }
                }
            }
        }
        return null
    }
}