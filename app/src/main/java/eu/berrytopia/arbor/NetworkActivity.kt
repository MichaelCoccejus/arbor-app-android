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
import java.sql.Timestamp

class NetworkActivity(private var context: Context) {
    /**
     *   Network-Klasse sollte die Funktionalitäten des Clients im Netz abdecken.
     *   Dazu zählen Login-Prozess, Datenabruf vom Server und Datenspeicherung zum Server.
     *
     *   Es wird nur JsonArrayRequest abgeschickt, da der Json bei einem JsonObjectRequest eine Json mit Json-Objekten ohne Keys zurückgibt
     *   und die Bearbeitung der Json-Objekten erschwert. Mit JsonArrayRequest wird ein Array der Json-Objekte als response erwartet.
     */
    private val urlBase: String =
        "arbor.berrytopia.eu:8080/api/v1/" // Die Adresse ändert sich nicht. Lediglich was angehängt wird.
    private lateinit var url: String
    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val gson = Gson()
    private val requestTimeout: Int = 10000
    private val dummyPwd = listOf("Burkhard", "Coccejus", "Voronkov")

    /*
    Wir bewegen uns im selben Context und wechseln nicht (Keine Mitgabe über Intent, sondern erneuter Aufruf).
    Der Konstruktor wird im neuen Context aufgerufen.
    Der Context wird für die Toast benötigt ( Noch keine Ahnung, ob es klappt ).
    */

    /*
    Joke of day 1
    Gefundener Kommentar unter dem Kampf zwischen Gandalf und dem Balrog in Khazad'dum (anderer
    Name für Moria).

    Gandalf tells the party to run so he can kill the final boss and get all the XP.
    Then he shows up next session all leveled up and with new robes and a new staff.

    Scumbag Gandalf.

    -----------------------------------------------------------------------------------------

    Joke of day 2
    Central Florida Man Buys 20mm  Electronic Gatling Gun Pulled From Navy Fighter Jet

    Florida man says he has no immediately plans on what he will be doing with his new acquired
    M61 Vulcan: 250-pound, 6-foot long pneumatically driven, six-barrel, air-cooled, electric
    rotary cannon which fires six thousand 20mm rounds per minute.
     */

    /**
     *  @param userName Übergebener Username vom Login-Screen
     *  @param passWord Übergebener Password vom Login-Screen
     *
     *   Muss überprüft werden, ob man als bereits im System ist und die Daten stimmen.
     *   Dummy-Daten wurden vorerst übernommen.
     *
     */
    fun login(userName: String, passWord: String) : Boolean{
        var success = false
        val registeredUser = getUsers()
        for (i in 0 until registeredUser.size) {
            if (registeredUser[i].nickname == userName) {
                success = (dummyPwd[i] == passWord)
                break
            }
        }
        return success
    }

    fun getUsers(): MutableList<AborUser> {
        val result: MutableList<AborUser> = mutableListOf()

        /*url = urlBase + "users"
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
        requestQueue.add(jsonArrayRequest)*/

        // Rückgabe von Dummy-Usern
        val user1 = AborUser()
        user1.id = 0
        user1.firstName = "Chantal"
        user1.lastName = "Burkhard"
        user1.nickname = "Chantal"
        result.add(user1)
        val user2 = AborUser()
        user2.id = 1
        user2.firstName = "Michael"
        user2.lastName = "Coccejus"
        user2.nickname = "Michael"
        result.add(user2)
        val user3 = AborUser()
        user3.id = 2
        user3.firstName = "John"
        user3.lastName = "Voronkov"
        user3.nickname = "John"
        result.add(user3)

        return result
    }

    fun getUser(id: Long) {

    }

    /** TODO: Implementing Getter for an already created plantage.
     *
     *  Die Rückgabe muss noch hinzugefügt werden.
     *  Die Funktion gibt 4 Geo-Punkte zurück.
     */
    fun getPlantage() {

    }

    /**
     *  Die Rückgabe muss noch hinzugefügt werden.
     *  Die Funktion Baum gibt mit einem Geo-Punkte zurück.
     */
    fun getTree(id: Long) {

    }

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
                        currentTree.relatedUser =
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
        /*
        https://gist.github.com/ycui1/5d25672430e6c014a9ef6b422f82652e
         */

        var successValue = false

        val json = gson.toJson(toAdd) // Gson von Google kann das Object zu JSON umwandeln.

        /*
        Hier sollte ein eigenes erstellen eines JSON möglich sein.
        val params: MutableMap<Any?, Any?> = mutableMapOf()
        params[""] = toAdd
        */

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

    fun getObjectMedia(): List<Media> {
        return emptyList()
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
                // TODO: Implementation von JSON in das Object currentEvent
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

    /**
     * @param idTree Referenz des Baumes für die DB
     *
     * Es soll eine Liste aller Tasks zurückgegeben werden,
     * die NUR mit dem Baum mit spezifischen ID zusammenhängen.
     *
     * Derzeit wird wegen dem fehlendem Request eine leere Liste zurückgegeben.
     *
     * TODO: HTTP-Request an die REST mit erfolgreicher Rückgabe aufbauen.
     */
    fun getTasks(idTree: Long): List<Task> {
        val result = mutableListOf<Task>()
        url = urlBase + "Tasks"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val currentTask = Task()
                // TODO: Implementation von JSON in das Object currentEvent
                result.add(currentTask)
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

    fun getLatinNames(): Array<String> {
        return emptyArray()
    }

    /*
    Ab hier sind die Funktionen für die Umwandlung von Json in die Datenstruktur der App.
    Es werden nicht unbedingt alle Attribute verwendet, da einige Attribute über das Filtern der
    Elemente bereits gesetzt werden können (wenn man bereits Voraussetzungen bei bestimmten Typen
    von Objecten festlegen kann).
     */
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
}
