package joly.sylvain.cms

import joly.sylvain.cms.model.Articles
import joly.sylvain.cms.model.Comments

interface Model {

    fun getArticleList(): List<Articles>
    fun getArticle(id: Int): Articles?
    fun createComment(content:String?, article_id: Int): Boolean
}