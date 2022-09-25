package eu.berrytopia.arbor

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NetworkActivity {
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
    private var requestQueue: RequestQueue
    private var context: Context
    /*
    Wir bewegen uns im selben Context und wechseln nicht (Keine Mitgabe über Intent, sondern erneuter Aufruf).
    Der Konstruktor wird im neuen Context aufgerufen.
     */

    constructor(context: Context) {
        this.context = context
        requestQueue = Volley.newRequestQueue(context)
    }

    /** TODO: Implementing Login-Function
     *  @param userName Übergebener Username vom Login-Screen
     *  @param passWord Übergebener Password vom Login-Screen
     *
     *   Muss überprüft werden, ob man als bereits im System ist und die Daten stimmen.
     */
    fun login(userName: String, passWord: String) {

    }

    /** TODO: Implementing Function for creating a new User
     *  @param userName Name für den neuen User.
     *  @param passWord Password für den neuen User.
     *
     *  Es soll mit diesen Daten ein neuer User auf dem Server angelegt werden.
     */
    fun createUser(userName: String, passWord: String) {

    }

    fun getUsers(): MutableList<AborUser> {
        val result: MutableList<AborUser> = mutableListOf()

        url = urlBase + "users"
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response -> // Wir als Array von JsonObjects zurückgegeben.
                for (i in 0 until response.length()) {
                    val currentUser = response.getJSONObject(i)
                    result.add(extractUserFromJson(currentUser))
                }
            },
            { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(jsonArrayRequest)
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

    /** TODO: Implementing Setter for a new plantage and sending 1 Point to server
     *  @param geoPoint Geopunkt des Baums.
     *
     *  Die Daten sollten dem Server gesendet werden, damit sie jederzeit abgerufen werden können.
     */
    fun createTree(geoPoint: Int) {

    }

    /** TODO: Implementing Getter for an already created tree.
     *
     *  Die Rückgabe muss noch hinzugefügt werden.
     *  Die Funktion Baum gibt mit einem Geo-Punkte zurück.
     */
    fun getTree(id: Long) {

    }

    fun getTrees(): List<GeoObject> {
        val result: MutableList<GeoObject> = mutableListOf()

        url = urlBase + "objects"
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val currentTree = GeoObject()
                    val currentObject = response.getJSONObject(i)
                    if (currentObject.getString("type") == "Baum") {
                        currentTree.setTypeTree()
                        currentTree.idOfObject = currentObject.get("id") as Long
                        //currentTree.idOfParent = currentObject.getString("relatedGeoObject").toLong()
                        currentTree.position = currentObject.get("gpsPostion") as GpsPosition

                    }
                }
            },
            { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            })
        return result
    }

    /**
     * @param idTree Referenz des Baumes für die DB
     *
     * Es soll eine Liste aller Events zurückgegeben werden,
     * die NUR mit dem Baum mit spezifischen ID zusammenhängen.
     *
     * Derzeit wird wegen dem fehlendem Request eine leere Liste zurückgegeben.
     *
     * TODO: HTTP-Request an die REST mit erfolgreicher Rückgabe aufbauen.
     */
    fun getEvents(idTree: Long): List<Event> {
        // HttpRequest mit der Referenz des Baumes (ID)
        return emptyList()
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
        return emptyList()
    }

    fun getLatinNames(): Array<String> {
        return emptyArray()
    }

    fun extractUserFromJson(extractingFrom: JSONObject) : AborUser{
        val extractingTo = AborUser()
        extractingTo.id = extractingFrom.get("id") as Long
        extractingTo.firstName = extractingFrom.getString("firstName")
        extractingTo.lastName = extractingFrom.getString("lastName")
        extractingTo.nickName = extractingFrom.getString("nickName")
        extractingTo.email = extractingFrom.getString("email")
        return extractingTo
    }

    fun extractGpsPositionFromJson(extractingFrom: JSONObject) : GpsPosition {
        val extractingTo = GpsPosition()
        extractingTo.id = extractingFrom.getLong("id")
        extractingTo.longitude = extractingFrom.getDouble("longitude")
        extractingTo.latitude = extractingFrom.getDouble("latitude")
        extractingTo.altitude = extractingFrom.getLong("altitude")
        return extractingTo
    }
}