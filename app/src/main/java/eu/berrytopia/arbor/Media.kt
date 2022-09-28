package eu.berrytopia.arbor

import kotlin.properties.Delegates

class Media : GeoObject() {
    var id: Long by Delegates.notNull()
    lateinit var typeOfRelatedParent: String
    var idOfRelatedParent: Long by Delegates.notNull()
    lateinit var typeOfReference: String
    var idOfReference: Long by Delegates.notNull()
    lateinit var smallUri: String
    lateinit var fullUri: String
}