package joly.sylvain.cms

interface CommentCreateController {

    fun start(content: String?, article_id: Int)

    interface View {
        fun createdSuccess()
        fun createdError()
    }
}