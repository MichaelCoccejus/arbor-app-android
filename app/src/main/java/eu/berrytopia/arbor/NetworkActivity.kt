package eu.berrytopia.arbor

import android.content.Context
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.security.Timestamp

class NetworkActivity(private var context: Context) {
    /**
     *   Network-Klasse sollte die Funktionalitäten des Clients im Netz abdecken.
     *   Dazu zählen Login-Prozess, Datenabruf vom Server und Datenspeicherung zum Server.
     *
     *
     *   Wir bewegen uns im selben Context und wechseln nicht (Keine Mitgabe über Intent,
     *   sondern erneuter Aufruf). Der Konstruktor wird im neuen Context aufgerufen. Der Context
     *   wird für die Toast benötigt.
     */


    /**
     * Die Adresse für die Request ändert sich nicht. Lediglich wird der gewünschte Abschnitt der
     * Variable url angehängt (urlBase + Anhägsel).
     */
    private val urlBase: String = "arbor.berrytopia.eu:8080/api/v1/"
    private lateinit var url: String

    /**
     * Standard Aufruf von Volley Request: Erstmal überlegen was geliefert wird.
     *
     * val requestObject = JsonArrayRequest | JsonObjectRequest {
     *      Request.METHOD.PUT,     Hier wird die Methode wie GET | PUT eingesetzt.
     *      url,                    Selbsterklärend.
     *      JsonObject,             Hier sollte der Body als JsonObject übergeben werden. Falls es keinen gibt, dann reicht auch null
     *      { response ->           Die Anwort vom Server/Backend. response -> ist optional und kann Daten enthalten, falls welche übergebn werden.
     *
     *      },
     *      { error ->              Die Fehlerbehandlung, falls der Request nicht funktioniert. error -> ist optional und enthält die Fehlermeldung.
     *
     *      }
     * }
     *
     * Zusätzliche Eigenschaften manipulieren (Beispiel):
     *
     * resquestObject.retryPolicy = DefaultRetryPolicy(
     *      requestTimeout,
     *      DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
     *      DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
     * )
     *
     * Request immer in die RequestQueue stecken. Die Request werden je nach Reihenfolge der Request abgearbeitet.
     */
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val requestTimeout: Int = 10000

    //Gson wird für die Übersetzung Json <-> Objekt
    private val gson = Gson()

    private val dummyUser = mutableListOf<AborUser>()
    private val geoObjects: MutableList<GeoObject> = mutableListOf()
    private val events: MutableList<Event> = mutableListOf()
    private val tasks: MutableList<Task> = mutableListOf()
    private val gpsPositions: MutableList<GpsPosition> = mutableListOf()
    private val photos: MutableList<Media> = mutableListOf()
    private lateinit var loggedIn: AborUser
    private val latinNames = arrayOf(
        "Corylus colurna",
        "Fraxinus excelsior",
        "Cercis siliquastrum"
    ) // Baumhasel, Gemeine Esche, Judasbaum

    /**
     *  @param userName Übergebener Username vom Login-Screen
     *  @param passWord Übergebener Password vom Login-Screen
     *
     *   Muss überprüft werden, ob man als bereits im System ist und die Daten stimmen.
     *   Dummy-Daten wurden vorerst übernommen.
     *
     */
    fun login(userName: String, passWord: String): Boolean {
        var success = false
        /**
         * Die Umsetzung des Logins muss noch an das Backend geändert werden. Am Besten sollte nur
         * ein Boolean vom Backend zurückgeschickt werden, da der Transport von Passwörtern
         * bekanntlich gefährlich ist.
         */
        return success
    }


    fun getUsers(): MutableList<AborUser> {
        val result: MutableList<AborUser> = mutableListOf()

        url = urlBase + "users"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response -> // Wir als Array von JsonObjects zurückgegeben.
                for (i in 0 until response.length()) {
                    val currentUser = response.getJSONObject(i)
                    result.add(extractUserFromJson(currentUser))
                }
            },
            { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            })
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            requestTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(jsonArrayRequest)
        return result
    }

    fun getEditor(): AborUser {
        return loggedIn
    }

    /**
     *  Die Rückgabe muss noch hinzugefügt werden.
     *  Die Funktion Baum gibt mit einem Geo-Punkte zurück.

    fun getTree(id: Long): GeoObject? {
    val trees = filteringTrees()
    for (currentTree in trees) {
    if (currentTree.idOfObject == id)
    return currentTree
    }
    return null
    }
     */


    fun getTrees(): List<GeoObject> {
        val result: MutableList<GeoObject> = mutableListOf()
        url = urlBase + "objects"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                for (i in 0 until response.length()) {
                    val currentTree = GeoObject()
                    val currentObject = response.getJSONObject(i)
                    if (currentObject.getString("type") == "TREE") {
                        currentTree.setTypeTree()
                        currentTree.idOfObject = currentObject.get("id") as Long
                        currentTree.position = currentObject.get("gpsPostion") as GpsPosition
                        currentTree.relatedUsers =
                            extractUserListFromJson(currentObject.getJSONArray("relatedUsers"))
                        currentTree.description = currentObject.getString("userDescription")
                        currentTree.eventList = extractEventsFromJson(
                            currentObject.getJSONArray("events"),
                            currentTree.idOfObject
                        )
                        currentTree.time = currentObject.get("createdTime") as Timestamp
                    }
                }
            },
            { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            })
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            requestTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(jsonArrayRequest)
        return result
    }

    fun addTree(toAdd: GeoObject): Boolean {
        //https://gist.github.com/ycui1/5d25672430e6c014a9ef6b422f82652e
        var successValue = false
        val json = gson.toJson(toAdd) // Gson von Google kann das Object zu JSON umwandeln.
        url = urlBase + "objects"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(json), // Anscheinend ist das der Body, der mitgegeben wird. Funktioniert mit Gson evtl genau so wie mit params Ansatz.
            {
                successValue = true
                Toast.makeText(context, "Konnte abgespeichert werden.", Toast.LENGTH_SHORT)
                    .show()
            },
            {
                successValue = false
                Toast.makeText(context, "Konnte nicht abgespeichert werden.", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            requestTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(jsonObjectRequest)
        return successValue
    }

    fun getObjectMedia(getForObject: GeoObject): List<Media> {
        val result = mutableListOf<Media>()
        url = urlBase +  "media"
        val jsonArrayRequest = JsonArrayRequest (
            Request.Method.GET,
            url,
            null,
            { response ->
                for (position in 0 until response.length()) {
                    val currentElement = response[position] as Media
                    if (currentElement.idOfObject == getForObject.idOfObject)
                        result.add(currentElement)
                }
            },
            {
                Toast.makeText(context, "Kann keine Medien für derzeitigen Baum finden.", Toast.LENGTH_SHORT).show()
            }
        )
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            requestTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(jsonArrayRequest)
        return result
    }


    /**
     * @param idTree Referenz des Baumes für die DB
     *
     * Es soll eine Liste aller Events zurückgegeben werden,
     * die NUR mit dem Baum mit spezifischen ID zusammenhängen.
     */
    fun getEvents(idTree: Long): List<Event> {
        val result = mutableListOf<Event>()
        url = urlBase + "events"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                val currentEvent = Event()
                if (currentEvent.idOfReference == idTree)
                    result.add(currentEvent)
            },
            {
                Toast.makeText(context, "Es gibt keine Events", Toast.LENGTH_SHORT).show()
            }
        )
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            requestTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(jsonArrayRequest)
        return result
    }

    fun getEvents(): List<Event> {
        val result = mutableListOf<Event>()
        url = urlBase + "events"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                for (position in 0 until response.length())
                    result.add(response[position] as Event)
            },
            { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonArrayRequest)
        return result
    }

    fun addEvent(toAdd: Event): Boolean {
        var success = false
        val json = gson.toJson(toAdd)
        url = urlBase + "events"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject(json),
            {
                success = true
            },
            {
                success = false
            }
        )
        return success
    }

    /**
     * @param idTree Referenz des Baumes für die DB
     *
     * Es soll eine Liste aller Tasks zurückgegeben werden,
     * die NUR mit dem Baum mit spezifischen ID zusammenhängen.
     *
     * Derzeit wird wegen dem fehlendem Request eine leere Liste zurückgegeben.
     *
     */
    fun getTasks(idTree: Long): List<Task> {

        val result = mutableListOf<Task>()
        url = urlBase + "Tasks"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                for (position in 0 until response.length())
                    result.add(response[position] as Task)
            },
            {
                Toast.makeText(context, "Es gibt keine Aufgaben.", Toast.LENGTH_SHORT).show()
            }
        )
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
            requestTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(jsonArrayRequest)
        return result
    }

    fun addTask(toAdd: Task) {

    }

    fun getLatinNames(): Array<String> {
        return latinNames
    }

    /*
    Ab hier sind die Funktionen für die Umwandlung von Json in die Datenstruktur der App.
    Es werden nicht unbedingt alle Attribute verwendet, da einige Attribute über das Filtern der
    Elemente bereits gesetzt werden können (wenn man bereits Voraussetzungen bei bestimmten Typen
    von Objecten festlegen kann).*/

    private fun extractUserFromJson(extractingFrom: JSONObject): AborUser {
        val extractingTo = AborUser()
        extractingTo.id = extractingFrom.get("id") as Long
        extractingTo.firstName = extractingFrom.getString("firstName")
        extractingTo.lastName = extractingFrom.getString("lastName")
        extractingTo.nickname = extractingFrom.getString("nickName")
        extractingTo.email = extractingFrom.getString("email")
        return extractingTo
    }

    private fun extractUserListFromJson(extractingFrom: JSONArray): List<AborUser> {
        val extractingTo: MutableList<AborUser> = mutableListOf()
        for (i in 0 until extractingFrom.length()) {
            extractingTo.add(extractUserFromJson(extractingFrom[i] as JSONObject))
        }
        return extractingTo
    }

    private fun extractEventFromJson(
        extractingFrom: JSONObject,
        id: Long
    ): Event {
        val extractingTo = Event()
        extractingTo.id = extractingFrom.getLong("id")
        extractingTo.eventType = extractingFrom.getString("eventType")
        extractingTo.idOfReference = id
        extractingTo.typeOfReference = "Baum"
        return extractingTo
    }

    private fun extractEventsFromJson(
        extractingFrom: JSONArray,
        id: Long,
    ): List<Event> {
        val extractingTo = mutableListOf<Event>()
        for (i in 0 until extractingFrom.length()) {
            extractingTo.add(
                extractEventFromJson(
                    extractingFrom[i] as JSONObject,
                    id
                )
            )
        }
        return extractingTo
    }

    fun addGps(position: GpsPosition) {
        position.id = (gpsPositions.size + 1).toLong()
        gpsPositions.add(position)
    }
}
