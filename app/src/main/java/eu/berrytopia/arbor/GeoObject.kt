package eu.berrytopia.arbor

import kotlin.properties.Delegates

class GeoObject {
    var idOfObject: Long by Delegates.notNull<Long>()
    lateinit var type: String

    constructor(idOfObject: Long, type: String) {
        this.idOfObject = idOfObject
        this.type = type
    }
}