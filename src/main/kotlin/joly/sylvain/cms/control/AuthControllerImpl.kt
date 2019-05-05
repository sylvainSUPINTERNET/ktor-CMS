package joly.sylvain.cms.control

import joly.sylvain.cms.AuthController
import joly.sylvain.cms.Model
import joly.sylvain.cms.model.Users
import org.mindrot.jbcrypt.BCrypt

class AuthControllerImpl(val model: Model, val view: AuthController.View): AuthController {
    override fun start(email: String?, password: String?) {

        if(email != null && password != null){
            val user: Users? = model.getUserBy(email) // search user by email + bcrypt(password_plain)

            if(user != null){
                if(checkPassword(password)){
                    view.loginSuccess(user)
                } else {
                    view.loginError()
                }
            } else {
                view.loginError()
            }
        } else {
            view.loginError()
        }


    }

    fun checkPassword(plain: String): Boolean{
        return BCrypt.checkpw(plain,"\$2a\$10\$1lnRSR0uzCuHXAaEm02rzuygFLUMrKNdwwHKBSQElgQuvzQxReHBC")
    }


}