package joly.sylvain.cms.model

import java.io.Serializable

data class Articles(val id: Int, val title: String, val text: String?, val comments:ArrayList<Comments>?) : Serializable



