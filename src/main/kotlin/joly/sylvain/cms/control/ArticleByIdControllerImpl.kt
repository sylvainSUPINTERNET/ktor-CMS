package joly.sylvain.cms.control

import joly.sylvain.cms.ArticleByIdController
import joly.sylvain.cms.Model

class ArticleByIdControllerImpl(val model: Model, val view: ArticleByIdController.View): ArticleByIdController {
    override fun start(id: Int) {
        val article = model.getArticle(id)
        if(article != null){
            view.displayArticleById(article)
        } else {
            view.displayNotFound()
        }
    }

}