package joly.sylvain.cms.model

import java.io.Serializable

data class Comments(val id: Int, val content: String, val id_article: Int) : Serializable

