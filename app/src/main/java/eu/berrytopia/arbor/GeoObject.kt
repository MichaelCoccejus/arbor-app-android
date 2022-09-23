package eu.berrytopia.arbor

import java.util.Date
import java.util.LinkedList
import kotlin.properties.Delegates

open class GeoObject {
    var idOfObject: Long by Delegates.notNull()
    var idOfParent: Long by Delegates.notNull()
    lateinit var typeOfParent: String
    var relatedUser: Long by Delegates.notNull() // Id des User als Referenz aus der Datenbank.
    lateinit var type: String
    lateinit var name: String
    lateinit var position: GpsPosition
    lateinit var area: LinkedList<GpsPosition>
    lateinit var taskList: List<Task>
    lateinit var date: Date
}