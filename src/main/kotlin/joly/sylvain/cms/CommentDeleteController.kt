package joly.sylvain.cms

interface CommentDeleteController {

    fun start(comment_id: Int)

    interface View {
        fun deletedSuccess()
        fun deletedError()
    }
}