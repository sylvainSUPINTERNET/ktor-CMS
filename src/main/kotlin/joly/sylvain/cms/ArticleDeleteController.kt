package joly.sylvain.cms

interface ArticleDeleteController {

    fun start(id: Int)

    interface View {
        fun deletedSuccess()
        fun deletedError()
    }
}