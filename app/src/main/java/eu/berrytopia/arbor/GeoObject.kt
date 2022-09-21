package eu.berrytopia.arbor

import kotlin.properties.Delegates

open class GeoObject {
    var idOfObject: Long by Delegates.notNull()
    lateinit var type: String
    lateinit var name: String

    constructor(idOfObject: Long, type: String, treeName: String) {
        this.idOfObject = idOfObject
        this.type = type
        this.name = treeName
    }

    constructor()
}