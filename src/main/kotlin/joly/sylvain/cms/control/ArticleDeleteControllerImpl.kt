package joly.sylvain.cms.control

import joly.sylvain.cms.ArticleDeleteController
import joly.sylvain.cms.Model


class ArticleDeleteControllerImpl(val model: Model, val view: ArticleDeleteController.View): ArticleDeleteController {
    override fun start(id: Int ) {
        val articleIsDeleted = model.removeArticleById(id)
        if(articleIsDeleted){
            view.deletedSuccess()
        } else {
            view.deletedError()
        }

    }

}