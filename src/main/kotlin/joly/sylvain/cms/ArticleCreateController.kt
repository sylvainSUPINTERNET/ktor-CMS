package joly.sylvain.cms

interface ArticleCreateController {

    fun start(text: String?, title: String)

    interface View {
        fun createdSuccess()
        fun createdError()
    }
}