package eu.berrytopia.arbor

import android.net.Uri

class Photos : GeoObject {
    val id: Long
        get() {
            TODO()
        }
    val reference: Long
        get() {
            TODO()
        }
    val smallUri: Uri
        get() {
            TODO()
        }
    val fullUri: Uri
        get() {
            TODO()
        }

    constructor(id: Long, reference: Long, smallUri: Uri, fullUri: Uri) : super() {

    }
}