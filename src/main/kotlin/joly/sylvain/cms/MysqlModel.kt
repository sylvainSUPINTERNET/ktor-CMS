package joly.sylvain.cms

import joly.sylvain.cms.model.Articles
import joly.sylvain.cms.model.Comments
import joly.sylvain.cms.model.Users

class MysqlModel(val pool: ConnectionPool) : Model {
    override fun removeArticleById(id: Int): Boolean {
        pool.useConnection { connection ->
            connection.prepareStatement("DELETE FROM article WHERE id = ? ").use {stmt->
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return true;
            }
        }
        return false
    }

    override fun createArticle(text: String?, title: String): Boolean {
        pool.useConnection { connection ->
            connection.prepareStatement("INSERT INTO article (text, title) VALUES (?, ?)").use { stmt->
                stmt.setString(1, text)
                stmt.setString(2, title)
                stmt.executeUpdate()
                return true
            }

        }
        return false
    }

    override fun removeCommentById(id: Int): Boolean {
        pool.useConnection { connection ->
            connection.prepareStatement("DELETE FROM comment WHERE id = ? ").use {stmt->
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return true;
            }
        }
        return false
    }

    override fun getUserBy(email: String): Users? {
        pool.useConnection { connection ->
            connection.prepareStatement("SELECT * from users WHERE users.email = ?").use { stmt ->
                stmt.setString(1, email)
                stmt.executeQuery().use {rs ->
                    if(rs.next()){
                        return Users(rs.getString("username"), rs.getString("email"), rs.getString("role"), rs.getString("password"))
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    override fun createComment(content: String?, article_id: Int): Boolean {
        pool.useConnection { connection ->
            connection.prepareStatement("INSERT INTO comment (content, article_id) VALUES (?, ?)").use { stmt->
                stmt.setString(1, content)
                stmt.setInt(2, article_id)
                stmt.executeUpdate()
                return true
            }

        }
        return false
    }

    override fun getArticleList(): List<Articles> {
        val list = ArrayList<Articles>();


        pool.useConnection { connection ->

            connection.prepareStatement("SELECT id, title, text FROM article").use { stmt ->
                stmt.executeQuery().use { results ->
                    while (results.next()) {
                        print(results.getString("title"))
                        list += Articles(results.getInt("id"), results.getString("title"), null, null)
                    }
                }

            }
        }
        return list
    }

    override fun getArticle(id: Int): Articles? {
        val commentList = ArrayList<Comments>();



        pool.useConnection { connection ->
            var article_title = ""
            var article_id = 0;
            var article_text = ""
            connection.prepareStatement("SELECT a.title, a.text, c.* FROM comment as c INNER JOIN article as a on c.article_id = a.id WHERE a.id = ?")
                .use { stmt ->
                    stmt.setInt(1, id) // en JDBC le premier index commence à 1
                    stmt.executeQuery().use { results ->

                        while (results.next()) {
                            commentList += Comments(
                                results.getInt("id"),
                                results.getString("content"),
                                id // article id
                            )
                            article_text = results.getString("text")
                            article_id = id // article id
                            article_title = results.getString("title")
                        }
                        if(commentList.isEmpty()){
                            connection.prepareStatement("SELECT * FROM article WHERE id = ?")
                                .use { stmt ->
                                    stmt.setInt(1, id) // en JDBC le premier index commence à 1
                                    stmt.executeQuery().use { result ->
                                        if(result.next()){
                                            return Articles(result.getInt("id"), result.getString("title"), result.getString("text"), null)
                                        }
                                    }
                                }
                        }else{
                            return Articles(article_id, article_title, article_text, commentList)
                        }
                    }
                }
        }

        return null
    }
}