package joly.sylvain.cms

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.application
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
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.*
import joly.sylvain.cms.control.ArticleByIdControllerImpl
import joly.sylvain.cms.control.ArticleListControllerImpl
import joly.sylvain.cms.model.Articles
import joly.sylvain.cms.model.Users
import joly.sylvain.cms.tpl.IndexContext
import kotlinx.coroutines.launch
import java.util.*
import org.mindrot.jbcrypt.BCrypt
import com.typesafe.config.ConfigFactory




class App

fun main(args: Array<String>) {

    val conf = ConfigFactory.load()
    //appcomponents -> container d'injection de dépendances (choisit les singletons / instancie si besoin etc)
    val appComponents = AppComponents(
        conf.getString("application.jbdc.uri"),
        conf.getString("application.jbdc.user"),
        conf.getString("application.jbdc.password")
    )
    embeddedServer(Netty, 8080) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }

        install(Sessions){
            cookie<AuthSession>("AUTH_COOKIE"){
                cookie.path = "/"
            }
        }



        routing {
            static("/static") {
                resources("static")
            }
            get("/article/{id}") {
                val controller =
                    appComponents.getArticleController(object : ArticleByIdController.View {
                        // object -> class anonyme
                        override fun displayNotFound() {
                            launch {
                                call.respond(HttpStatusCode.NotFound)
                            }
                        }

                        override fun displayArticleById(article: Articles?) {
                            launch {
                                if(call.sessions.get<AuthSession>() == null){
                                    call.respond(FreeMarkerContent("article.ftl", article, "e"))
                                } else {
                                    call.respond(FreeMarkerContent("article_admin.ftl", article, "e"))
                                }
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

                val controller = appComponents.getArticleListController(object : ArticleListController.View {
                    override fun displayArticleList(list: List<Articles>) {
                        val context = IndexContext(list);


                        launch {
                            if(call.sessions.get<AuthSession>() == null){
                                call.respond(FreeMarkerContent("home.ftl", context, "e"))
                            } else {
                                call.respond(FreeMarkerContent("home_admin.ftl", context, "e"))
                            }
                        }
                    }

                })

                controller.start()
            }

            post("/article") {
                val post = call.receiveParameters()
                val content = post["com_content"]
                val article_id = post["article_id"]!!.toInt()

                val controller = appComponents.createComment(object: CommentCreateController.View {
                    override fun createdSuccess() {
                        launch {
                            //call.respondText { "Posted with success ! ${content} ${article_id}" }
                            call.respondRedirect("/article/${article_id}")
                        }
                    }

                    override fun createdError() {
                        launch{
                            call.respondText { "Oups ! Something wrong happend !"}
                        }
                    }
                })

                controller.start(content, article_id)
            }
            get("/comment/{comment_id}/delete/{article_id}") {
                val comment_id = call.parameters["comment_id"]!!.toInt();
                val article_id = call.parameters["article_id"]!!.toInt();

                val controller = appComponents.deleteComment(object: CommentDeleteController.View {
                    override fun deletedSuccess() {

                        launch {
                            if(call.sessions.get<AuthSession>() != null){
                                call.respondRedirect("/article/${article_id}")
                            } else {
                                call.respondText { "Oups, something wrong occured ! Please, try again." }
                            }
                        }
                    }

                    override fun deletedError() {
                        launch {
                            call.respondText { "Oups, something wrong occured ! Please, try again." }
                        }
                    }

                })

                controller.start(comment_id);
            }

            /**
             * Authentication
             */
            get("/authentication") {
                    call.respond(FreeMarkerContent("authentication.ftl", null, "e"))
            }
            post("/authentication") {
                val post = call.receiveParameters()
                val email = post["email"]
                val password = post["password"]
               val controller =  appComponents.login(object: AuthController.View {
                    override fun loginSuccess(user: Users) {
                        launch {
                            call.sessions.clear("AUTH_COOKIE") // clear old session

                            val sessionAuth = call.sessions.get<AuthSession>()// Gets a session of this type or null if not available
                            val currentTime = System.currentTimeMillis()
                            //1 800 000 miliseconds = 30 minutes
                            val halfAnHourLater = currentTime + 1800000

                            call.sessions.set(AuthSession(user.username, user.email, user.role, halfAnHourLater)) // Sets a session of this type

                            if(call.sessions.get<AuthSession>() != null){
                                call.respondRedirect("/")
                            } else {
                                call.respondText {
                                    "Error is occured, please try again."
                                }
                            }

                        }

                    }

                    override fun loginError() {
                        launch {
                            call.respondText {
                                "Invalid credentials."
                            }
                        }
                    }

                })
                controller.start(email, password)


            }
            get("/auth/logout"){
                call.sessions.clear("AUTH_COOKIE") // clear old session
                call.respond(FreeMarkerContent("logout_success.ftl", null, "e"))
            }
        }
    }.start(wait = true)
}


