package eu.berrytopia.arbor

import java.sql.Time
import java.sql.Timestamp
import kotlin.properties.Delegates

class Task {
    var id: Long by Delegates.notNull()
    lateinit var taskName: String
    lateinit var userDescription: String
    lateinit var created: Timestamp
    lateinit var endTime: Time
    lateinit var relatedTrees: List<GeoObject>
    lateinit var userPhotos: List<Media>
    lateinit var events: List<Event>
}