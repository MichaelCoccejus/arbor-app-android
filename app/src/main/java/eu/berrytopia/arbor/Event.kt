package eu.berrytopia.arbor

import java.sql.Timestamp
import kotlin.properties.Delegates

class Event : GeoObject() {
    var id: Long by Delegates.notNull()
    var idOfReference: Long by Delegates.notNull()
    lateinit var typeOfReference: String
    var relatedUserEvent: Long by Delegates.notNull()
    lateinit var eventName: String
    lateinit var eventType: String
    lateinit var userDescription: String
    lateinit var timestamp: Timestamp
    lateinit var userPhotos: List<Media>
}