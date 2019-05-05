package joly.sylvain.cms.control

import joly.sylvain.cms.CommentDeleteController
import joly.sylvain.cms.Model


class CommentDeleteControllerImpl(val model: Model, val view: CommentDeleteController.View): CommentDeleteController {
    override fun start(comment_id: Int ) {
        val comentIsDeleted = model.removeCommentById(comment_id)
        if(comentIsDeleted){
            view.deletedSuccess()
        } else {
            view.deletedError()
        }

    }

}