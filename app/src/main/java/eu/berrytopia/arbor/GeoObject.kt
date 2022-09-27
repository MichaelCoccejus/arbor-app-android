package eu.berrytopia.arbor

import java.io.Serializable
import java.sql.Timestamp
import java.util.*
import kotlin.properties.Delegates

open class GeoObject() : Serializable {
    var idOfObject: Long by Delegates.notNull() // Eigene ID
    lateinit var typeOfObject: String // Eigener Typ
    //var idOfParent: Long by Delegates.notNull() // ID der Plantage
    lateinit var typeOfParent: String // Typ Plantage damit es nicht mehrfach überprüft werden muss.
    lateinit var relatedUser: List<AborUser>
    //var relatedUser: Long by Delegates.notNull() // ID des User als Referenz aus der Datenbank.
    lateinit var name: String
    lateinit var position: GpsPosition // Wird nur für Typ Baum verwendet (ist leer/null, wenn GeoObject eine Plantage ist).
    lateinit var area: LinkedList<GpsPosition> // Wird nur für Typ Plantage verwendet (ist leer/null, wenn GeoObject ein Baum ist).
    lateinit var description: String
    //lateinit var taskList: List<Task>
    lateinit var eventList: List<Event>
    lateinit var time: Timestamp
    /*
    Für das aktuellste Foto würde man die ganzen Fotos in Events stecken.
    Aus Gründen der Zeit und Komplexität wird ein festes Bild in das GeoObject gesetzt.
     */
    lateinit var media: Media

    fun setTypeTree() {
        typeOfObject = "Baum"
        area = LinkedList<GpsPosition>()
        typeOfParent = "Plantage"
    }
}