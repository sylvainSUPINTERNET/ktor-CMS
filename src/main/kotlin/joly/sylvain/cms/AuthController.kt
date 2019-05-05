package joly.sylvain.cms

import joly.sylvain.cms.model.Users

interface AuthController {
    fun start(email: String?, password:String?)

    interface View {
        fun loginSuccess(user: Users)
        fun loginError()
    }
}