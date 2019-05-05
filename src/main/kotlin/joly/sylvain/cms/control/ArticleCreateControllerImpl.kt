package joly.sylvain.cms.control

import joly.sylvain.cms.ArticleCreateController
import joly.sylvain.cms.Model

class ArticleCreateControllerImpl(val model: Model, val view: ArticleCreateController.View): ArticleCreateController {
    override fun start(text: String?, title: String ) {
        val articleIsCreated = model.createArticle(text, title);
        if(articleIsCreated){
            view.createdSuccess()
        } else {
            view.createdError()
        }
    }

}