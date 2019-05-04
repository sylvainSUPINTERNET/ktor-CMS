package joly.sylvain.cms

import joly.sylvain.cms.control.ArticleByIdControllerImpl
import joly.sylvain.cms.control.ArticleListControllerImpl
import joly.sylvain.cms.control.CommentCreateControllerImpl

class AppComponents(mySqlUrl: String, mySqlUser: String, mySqlPassword: String) { // sans val dans le constructor les valeurs sont accessible que dans le constructeur

    private val pool = ConnectionPool(mySqlUrl, mySqlUser, mySqlPassword); // create singleton

    fun getPool(): ConnectionPool{
        return pool;
    }

    private val model = MysqlModel(getPool())

    fun getModel(): Model {
        return model
    }

    fun getArticleListController(view: ArticleListController.View): ArticleListController {
        return ArticleListControllerImpl(getModel(), view )
    }

    fun getArticleController(view: ArticleByIdController.View): ArticleByIdController {
        return ArticleByIdControllerImpl(getModel(), view )
    }

    fun createComment(view: CommentCreateController.View): CommentCreateController {
        return CommentCreateControllerImpl(getModel(), view)
    }
}