package eu.berrytopia.arbor

// Dient zur Speicherung von GPS-Daten.
class GpsPosition {
    private var longitude: Long
    private var latitude: Long
    private var altitude: Long

    constructor(iLongitude: Long, iLatitude: Long, iAltitude: Long) {
        this.longitude = iLongitude
        this.latitude = iLatitude
        this.altitude = iAltitude
    }
}