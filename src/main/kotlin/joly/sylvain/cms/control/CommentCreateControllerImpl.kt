package joly.sylvain.cms.control

import joly.sylvain.cms.CommentCreateController
import joly.sylvain.cms.Model

class CommentCreateControllerImpl(val model: Model, val view: CommentCreateController.View): CommentCreateController {
    override fun start(content: String?, article_id: Int ) {
        if(content != ""){
            val comment = model.createComment(content, article_id)
            if(comment != false && content != ""){
                view.createdSuccess()
            } else {
                view.createdError()
            }
        } else {
            view.createdError()
        }

    }

}