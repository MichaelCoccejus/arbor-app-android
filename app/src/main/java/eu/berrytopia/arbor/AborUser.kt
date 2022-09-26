package eu.berrytopia.arbor

import kotlin.properties.Delegates

class AborUser {
    var id: Long by Delegates.notNull()
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var nickname: String
    lateinit var email: String
}