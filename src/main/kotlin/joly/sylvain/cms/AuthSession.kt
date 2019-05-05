package joly.sylvain.cms

data class AuthSession (val username:String, val email:String, val role: String, val expiration: Long)