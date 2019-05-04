package joly.sylvain.cms

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.files
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import joly.sylvain.cms.control.ArticleByIdControllerImpl
import joly.sylvain.cms.control.ArticleListControllerImpl
import joly.sylvain.cms.model.Articles
import joly.sylvain.cms.tpl.IndexContext
import kotlinx.coroutines.launch
import java.util.*


class App

fun main(args: Array<String>) {


    //appcomponents -> container d'injection de dépendances (choisit les singletons / instancie si besoin etc)
    val appComponents = AppComponents(
        "jdbc:mysql://localhost:8889/CMS?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        "root",
        "root"
    )



    embeddedServer(Netty, 8080) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }

        routing {
            static("/static") {
                resources("static")
            }
            get("article/{id}") {
                /*
                val article = model.getArticle(id);

                if(article != null){
                    call.respond(FreeMarkerContent("article.ftl", article, "e"))
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
                */
                val controller =
                    appComponents.getArticleController(object : ArticleByIdController.View {
                        // object -> class anonyme
                        override fun displayNotFound() {
                            launch {
                                call.respond(HttpStatusCode.NotFound)
                            }
                        }

                        override fun displayArticleById(article: Articles?) {
                            println("_________")
                            println("_________")
                            println("_________")

                            print(article)

                            println("_________")
                            println("_________")

                            launch {
                                call.respond(FreeMarkerContent("article.ftl", article, "e"))
                            }
                        }

                    })
                val id = call.parameters["id"]!!.toIntOrNull(); // paramters["id"] -> optionnal
                if(id == null){
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    controller.start(id)
                }
            }

            get("/") {
                /*
                val articles = model.getArticleList();
                val context = IndexContext(articles);
                call.respond(FreeMarkerContent("home.ftl", context, "e"))
                */
                val controller = appComponents.getArticleListController(object : ArticleListController.View {
                    override fun displayArticleList(list: List<Articles>) {
                        val context = IndexContext(list);
                        launch {
                            call.respond(FreeMarkerContent("home.ftl", context, "e"))
                        }
                    }

                })

                controller.start()
            }

            get("/article/add") {

                call.respond(FreeMarkerContent("form_article.ftl", null, "e"))
            }

            post("/article") {
                val post = call.receiveParameters()
                val title = post["article_title"]
                val text = post["article_text"]
                /*
                val stmt = connection.prepareStatement("INSERT INTO article (title, text) VALUES (? ?) ")
                stmt.setString(1, title)
                stmt.setString(2, text)

                val result = stmt.execute()

                */

                call.respondText { "Posted with success !" }
            }

        }
    }.start(wait = true)
}


