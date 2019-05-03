package joly.sylvain.cms

import joly.sylvain.cms.model.Articles

interface ArticleByIdController {

    fun start(id: Int)

    interface View {
        fun displayArticleById(article: Articles?)
        fun displayNotFound();
    }

}