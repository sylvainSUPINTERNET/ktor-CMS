package joly.sylvain.cms

import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ConcurrentLinkedQueue


//Thread safety (ici on evite les conflits entre threads quand on ouvre des connections / ecrits)
class ConnectionPool(val url: String, val user:String, val password:String){

    private val queue = ConcurrentLinkedQueue<Connection>()

    fun getConnection(): Connection {
        val connection = queue.poll()

        if(connection == null){
            return DriverManager.getConnection(url, user, password)
        } else {

            return connection
        }
    }

    fun releaseConnection(c: Connection){
        queue.add(c);
    }

    // si on fait pas de inline on va perdre le context comme .then() le this ne fonctionne plus on fait =>
    inline fun useConnection(f: (Connection) -> Unit) {
        val connection = getConnection()
        try{
            f(connection)
        } finally {
            releaseConnection(connection)
        }
    }
}