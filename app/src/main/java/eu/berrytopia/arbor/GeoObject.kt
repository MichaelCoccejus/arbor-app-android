package eu.berrytopia.arbor

import java.util.Date
import java.util.LinkedList
import kotlin.properties.Delegates
import java.io.Serializable

open class GeoObject : Serializable {
    var idOfObject: Long by Delegates.notNull() // Eigene ID
    private lateinit var typeOfObject: String // Eigener Typ
    var idOfParent: Long by Delegates.notNull() // ID der Plantage
    lateinit var typeOfParent: String // Typ Plantage damit es nicht mehrfach überprüft werden muss.
    var relatedUser: Long by Delegates.notNull() // ID des User als Referenz aus der Datenbank.
    lateinit var name: String
    lateinit var position: GpsPosition // Wird nur für Typ Baum verwendet (ist leer/null, wenn GeoObject eine Plantage ist).
    lateinit var area: LinkedList<GpsPosition> // Wird nur für Typ Plantage verwendet (ist leer/null, wenn GeoObject ein Baum ist).
    lateinit var taskList: List<Task>
    lateinit var date: Date

    fun setTypeTree() {
        typeOfObject = "Baum"
        //area =
    }
}