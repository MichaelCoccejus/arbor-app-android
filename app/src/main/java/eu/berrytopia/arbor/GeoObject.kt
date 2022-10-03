package eu.berrytopia.arbor

import java.io.Serializable
import java.security.Timestamp
import java.util.*
import kotlin.properties.Delegates

open class GeoObject : Serializable {
    var idOfObject: Long by Delegates.notNull() // Eigene ID
    lateinit var typeOfObject: String // Eigener Typ
    //var idOfParent: Long by Delegates.notNull() // ID der Plantage
    lateinit var typeOfParent: String // Typ Plantage damit es nicht mehrfach überprüft werden muss.
    lateinit var relatedUser: AborUser
    lateinit var relatedUsers: List<AborUser>
    lateinit var name: String
    lateinit var position: GpsPosition // Wird nur für Typ Baum verwendet (ist leer/null, wenn GeoObject eine Plantage ist).
    lateinit var area: LinkedList<GpsPosition> // Wird nur für Typ Plantage verwendet (ist leer/null, wenn GeoObject ein Baum ist).
    lateinit var description: String
    lateinit var taskList: List<Task>
    lateinit var eventList: List<Event>
    lateinit var time: Timestamp
    lateinit var latinName: String // Wenn der Typ vom GeoObject Baum ist
    lateinit var plantDate: String // Wenn der Typ vom GeoObject Baum ist
    lateinit var size: String      // Wenn der Typ vom GeoObject Baum ist
    lateinit var media: Media

    fun setTypeTree() {
        typeOfObject = "Baum"
        area = LinkedList<GpsPosition>()
        typeOfParent = "Plantage"
    }
}