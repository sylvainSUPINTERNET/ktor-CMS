package joly.sylvain.cms

import joly.sylvain.cms.model.Comments

interface CommentCreateController {

    fun start(content: String?, article_id: Int)

    interface View {
        fun createdSuccess()
        fun createdError()
    }
}