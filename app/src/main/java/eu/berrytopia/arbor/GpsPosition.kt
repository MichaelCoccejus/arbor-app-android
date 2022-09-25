package eu.berrytopia.arbor

import kotlin.properties.Delegates

// Dient zur Speicherung von GPS-Daten.
class GpsPosition {
    var id: Long by Delegates.notNull()
    var longitude: Double by Delegates.notNull()
    var latitude: Double by Delegates.notNull()
    var altitude: Long by Delegates.notNull()

    constructor(iLongitude: Double, iLatitude: Double, iAltitude: Long) {
        this.longitude = iLongitude
        this.latitude = iLatitude
        this.altitude = iAltitude
    }

    constructor()
}