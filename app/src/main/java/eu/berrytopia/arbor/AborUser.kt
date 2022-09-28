package eu.berrytopia.arbor

import kotlin.properties.Delegates
import java.io.Serializable

data class AborUser(var firstName: String, var lastName: String, var nickname: String, var password: String, var email: String) : Serializable {
    /*var id: Long by Delegates.notNull()
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var nickname: String
    lateinit var email: String*/
}