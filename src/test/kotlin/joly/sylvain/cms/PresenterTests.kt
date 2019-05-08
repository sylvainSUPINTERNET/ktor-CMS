package joly.sylvain.cms

import com.nhaarman.mockitokotlin2.*
import joly.sylvain.cms.control.*
import joly.sylvain.cms.model.Articles
import joly.sylvain.cms.model.Comments
import joly.sylvain.cms.model.Users
import org.junit.Assert.*
import org.junit.Test
import java.util.ArrayList


class PresenterTests {


    @Test
    fun testArticleListPrensenter() {
        val list = prepareData();

        val model = mock<Model> {
            on { getArticleList() } doReturn list
        }
        val view = mock<ArticleListController.View>()
        val presenter = ArticleListControllerImpl(model, view)
        presenter.start()

        //test
        verify(model).getArticleList()
        verify(view).displayArticleList(list)
        verifyNoMoreInteractions(model, view)
    }


    @Test
    fun testArticlePrensenter() {
        val article = prepareData()[0]
        val id = 1;

        //mock
        val model = mock<Model> {
            on { getArticle(id) } doReturn article
        }


        //simulation of scenario
        val view = mock<ArticleByIdController.View>()
        val presenter = ArticleByIdControllerImpl(model, view)
        presenter.start(id);


        //test method
        verify(model).getArticle(id) // ici on dit en gros a mockito -> verifique que l'apelle de getArticle(id) a bien etait fait à partir de model
        verify(view).displayArticleById(article)
        //test no interaction with another pieaces of code
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun testInvalidArticlePrensenter() {
        //mock
        val model = mock<Model> {
            on { getArticle(any()) } doReturn null
        }


        //simulation of scenario
        val view = mock<ArticleByIdController.View>()
        val presenter = ArticleByIdControllerImpl(model, view)
        presenter.start(3);


        //test method
        verify(model).getArticle(3) // ici on dit en gros a mockito -> verifique que l'apelle de getArticle(id) a bien etait fait à partir de model
        verify(view).displayNotFound()
        //test no interaction with another pieaces of code
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun testGetUserByEmailAndAuthOk() {
        val UsersAdmin = prepareAdminUser();
        val model = mock<Model> {
            on { getUserBy(UsersAdmin.email) } doReturn UsersAdmin
        }

        val view = mock<AuthController.View>()
        val presenter = AuthControllerImpl(model, view);
        presenter.start(UsersAdmin.email, "admin")

        verify(model).getUserBy(UsersAdmin.email)
        verify(view).loginSuccess(UsersAdmin)
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun testGetUserByEmailAndAuthNotOk() {
        val UsersAdmin = prepareAdminUser();
        val model = mock<Model> {
            on { getUserBy(UsersAdmin.email) } doReturn UsersAdmin
        }

        val view = mock<AuthController.View>()
        val presenter = AuthControllerImpl(model, view);
        presenter.start(UsersAdmin.email, "ezaeklaze")


        verify(model).getUserBy(UsersAdmin.email)
        verify(view).loginError()
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun getListCommentsLinkArticles(){
        val article = prepareData()[0]
        val id = 1;
        print(article);
        //mock
        val model = mock<Model> {
            on { getArticle(id) } doReturn article
        }


        //simulation of scenario
        val view = mock<ArticleByIdController.View>()
        val presenter = ArticleByIdControllerImpl(model, view)
        presenter.start(id);


        //test method
        verify(model).getArticle(id)
        verify(view).displayArticleById(article)

        assertEquals(2,article.comments!![0].id)

        verifyNoMoreInteractions(model, view)

    }

    @Test
    fun createCommentTest(){
        val content = "Test content";
        val articleId = 1;

        val model = mock<Model> {
            on {createComment(content = content,article_id = articleId)} doReturn true
        }

        val view = mock<CommentCreateController.View>()
        val presenter = CommentCreateControllerImpl(model, view);
        presenter.start(content, articleId)

        verify(model).createComment(content, articleId)
        verify(view).createdSuccess()
        verifyNoMoreInteractions(model, view)

    }

    @Test
    fun createCommentFailTest(){
        val content = "Test content";
        val articleId = 1;

        val model = mock<Model> {
            on {createComment(content = "", article_id = 0)} doReturn false
        }

        val view = mock<CommentCreateController.View>()
        val presenter = CommentCreateControllerImpl(model, view);
        presenter.start(content, articleId)

        verify(model).createComment(content, articleId)
        verify(view).createdError()
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun deleteCommentTest(){
        val commentId = 1;

        val model = mock<Model> {
            on {removeCommentById(commentId)} doReturn true
        }

        val view = mock<CommentDeleteController.View>()
        val presenter = CommentDeleteControllerImpl(model, view);
        presenter.start(commentId)

        verify(model).removeCommentById(commentId)
        verify(view).deletedSuccess()
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun deleteCommentFailTest(){
        val commentId = 1;

        val model = mock<Model> {
            on {removeCommentById(commentId)} doReturn false
        }

        val view = mock<CommentDeleteController.View>()
        val presenter = CommentDeleteControllerImpl(model, view);
        presenter.start(commentId)

        verify(model).removeCommentById(commentId)
        verify(view).deletedError()
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun createArticleTest(){
        val text = "Article content"
        val title = "Title for article"

        val model = mock<Model> {
            on {createArticle(text = text, title = title)} doReturn true
        }

        val view = mock<ArticleCreateController.View>()
        val presenter = ArticleCreateControllerImpl(model, view);
        presenter.start(text, title)

        verify(model).createArticle(text, title)
        verify(view).createdSuccess()
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun createArticleFailTest(){
        val text = "Article content"
        val title = "Title for article"

        val model = mock<Model> {
            on {createArticle(text = text, title = title)} doReturn false
        }

        val view = mock<ArticleCreateController.View>()
        val presenter = ArticleCreateControllerImpl(model, view);
        presenter.start(text, title)

        verify(model).createArticle(text, title)
        verify(view).createdError()
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun deleteArticleByIdTest(){
        val articleId = 1

        val model = mock<Model> {
            on {removeArticleById(articleId)} doReturn true
        }

        val view = mock<ArticleDeleteController.View>()
        val presenter = ArticleDeleteControllerImpl(model, view);
        presenter.start(articleId)

        verify(model).removeArticleById(articleId)
        verify(view).deletedSuccess()
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun deleteArticleByIdFailTest(){
        val articleId = 1

        val model = mock<Model> {
            on {removeArticleById(articleId)} doReturn false
        }

        val view = mock<ArticleDeleteController.View>()
        val presenter = ArticleDeleteControllerImpl(model, view);
        presenter.start(articleId)

        verify(model).removeArticleById(articleId)
        verify(view).deletedError()
        verifyNoMoreInteractions(model, view)
    }


}



    /**
     * Prepare data for one test
     */
    fun prepareData(): List<Articles> {
        val comments_article1 = listOf(
            Comments(1, "Comment 1 article 1", 1),
            Comments(2, "Comment 2 for article 1", 1)
        )
        val comments_article2 = listOf(
            Comments(1, "Comment 1 article 2", 2),
            Comments(2, "Comment 2 article 2", 2)
        )
        val ac = ArrayList<Comments>()
        val ac2 = ArrayList<Comments>()

        val iterator = comments_article1.iterator()
        val i2 = comments_article2.iterator()

        while (iterator.hasNext()) {
            iterator.next()
            ac.add(iterator.next())
        }

        while (i2.hasNext()) {
            i2.next()
            ac2.add(i2.next())
        }


        val list = listOf(
            Articles(1, "Titlte", "ZEOAKOAEKAZOKEOAKE", ac),
            Articles(2, "Title2", "eeokzaoekakzeokaozek", ac2)
        )
        return list
    }

    fun prepareAdminUser(): Users {
        return Users("admin", "admin@admin.fr", "ADMIN", "\$2a\$10\$1lnRSR0uzCuHXAaEm02rzuygFLUMrKNdwwHKBSQElgQuvzQxReHBC")
    }
