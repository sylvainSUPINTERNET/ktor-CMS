package joly.sylvain.cms

import com.nhaarman.mockitokotlin2.*
import joly.sylvain.cms.control.ArticleByIdControllerImpl
import joly.sylvain.cms.control.ArticleListControllerImpl
import joly.sylvain.cms.model.Articles
import org.junit.Assert.*
import org.junit.Test

class PresenterTests {

    @Test
    fun testArticleListPrensenter(){
        val list = listOf(
            Articles(1, "Titlte", "ZEOAKOAEKAZOKEOAKE"),
            Articles(2, "Title2", "eeokzaoekakzeokaozek")
        )

        val model = mock<Model>{
            on {getArticleList()} doReturn list
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
    fun testArticlePrensenter(){
        //set data
        val article = Articles(1, "Title1", "Deslakzeoakekaoekaokeoazke")
        val id = 1;

        //mock
        val model = mock<Model> {
            on {getArticle(id)} doReturn article}


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
    fun testInvalidArticlePrensenter(){
        //mock
        val model = mock<Model> {
            on {getArticle(any())} doReturn null}


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

}