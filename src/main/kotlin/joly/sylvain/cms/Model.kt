package joly.sylvain.cms

import joly.sylvain.cms.model.Articles

interface Model {

    fun getArticleList(): List<Articles>
    fun getArticle(id: Int): Articles?
}