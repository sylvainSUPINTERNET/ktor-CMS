package joly.sylvain.cms.control

import joly.sylvain.cms.ArticleListController
import joly.sylvain.cms.Model

class ArticleListControllerImpl(val model: Model, val view: ArticleListController.View): ArticleListController {
    override fun start() {

        val list = model.getArticleList()
        view.displayArticleList(list)
    }

}