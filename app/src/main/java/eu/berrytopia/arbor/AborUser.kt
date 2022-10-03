package eu.berrytopia.arbor

import kotlin.properties.Delegates
import java.io.Serializable

class AborUser() : Serializable {
    var id: Long by Delegates.notNull()
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var nickname: String
    lateinit var email: String
}