package joly.sylvain.cms

import joly.sylvain.cms.model.Articles

interface ArticleListController {
    fun start()

    interface View {
        fun displayArticleList(list: List<Articles>)
    }
}