package eu.berrytopia.arbor

import android.content.Context

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

-----------------------------------------------------------------------------------------

 Joke of day 3

 Once you turn 25 years and above,
 there is no need to set an alarm. Your
 problems will wake you up by force.
 */

class NetworkActivity(private var context: Context) {
    /**
     *   Network-Klasse sollte die Funktionalitäten des Clients im Netz abdecken.
     *   Dazu zählen Login-Prozess, Datenabruf vom Server und Datenspeicherung zum Server.
     *
     *   Es wird nur JsonArrayRequest abgeschickt, da der Json bei einem JsonObjectRequest eine
     *   Json mit Json-Objekten ohne Keys zurückgibt und die Bearbeitung der Json-Objekten
     *   erschwert. Mit JsonArrayRequest wird ein Array der Json-Objekte als response erwartet.
     *
     *   Wir bewegen uns im selben Context und wechseln nicht (Keine Mitgabe über Intent,
     *   sondern erneuter Aufruf). Der Konstruktor wird im neuen Context aufgerufen. Der Context
     *   wird für die Toast benötigt.
     *
     *   Wegen der Komplexizität der Struktur reicht die Zeit nicht die Objekte und Funktionen an
     *   das Backend anzupassen. Die Request können aus dem Grund nicht getestet werden.
     *   Insbesondere wenn der Emulator Probleme bei den Foto-Dateien erzeugt.
     *
     *   Die meisten Request über Volley sind zwar da und auskommentiert. Einige Requests sind
     *   nicht vollständig.
     *
     *   Die Adresse für die Request ändert sich nicht. Lediglich wird der gewünschte Abschnitt der
     *   Variable url angehängt (urlBase + Anhägsel).
     *   private val urlBase: String = "arbor.berrytopia.eu:8080/api/v1/"
     *   private lateinit var url: String
     *
     *   private var requestQueue: RequestQueue = Volley.newRequestQueue(context)
     *   private val requestTimeout: Int = 10000
     *
     *   Gson wird für die Übersetzung Json <-> Objekt
     *   private val gson = Gson()
     */
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

    // Sollte die persistenten Daten aus der JSON auslesen.
    init {
        val user1 =
            AborUser("Chantal", "Burkhard", "Chantal", "Burkhard", "chantal.burkhard@mail.de")
        /*user1.id = 0
        user1.firstName = "Chantal"
        user1.lastName = "Burkhard"
        user1.nickname = "Chantal"
        user1.email = "chantal.burkhard@mail.de"*/
        dummyUser.add(user1)
        val user2 =
            AborUser("Michael", "Coccejus", "Michael", "Coccejus", "michael.coccejus@mail.de")
        /*user2.id = 1
        user2.firstName = "Michael"
        user2.lastName = "Coccejus"
        user2.nickname = "Michael"
        user2.email = "michael.coccejus@mail.de"*/
        dummyUser.add(user2)
        val user3 = AborUser("John", "Voronkov", "John", "Voronkov", "john.voronkov@mail.de")
        /*user3.id = 2
        user3.firstName = "John"
        user3.lastName = "Voronkov"
        user3.nickname = "John"
        user3.email = "john.voronkov@mail.de"*/
        dummyUser.add(user3)
    }

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
        val registeredUser = getUsers()
        for (i in 0 until registeredUser.size) {
            if (registeredUser[i].nickname == userName) {
                if (registeredUser[i].password == passWord) {
                    success = true
                    loggedIn = registeredUser[i]
                    break
                }
            }
        }
        return success
    }


    /*
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
    }
    */
    fun getUsers(): MutableList<AborUser> {
        // Rückgabe von Dummy-Usern
        return dummyUser
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

    /*
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
    */
    fun getTrees(): List<GeoObject> {

        val geoObject1 = GeoObject()
        geoObject1.idOfObject = 0
        geoObject1.name = "Baumbart"
        geoObject1.latinName = "Corylus colurna"
        geoObject1.plantDate = "12/09/2022"
        val mediaItem = Media()
        mediaItem.id = 0
        mediaItem.pic = R.drawable.baum1
        geoObject1.media = mediaItem
        val geoObject2 = GeoObject()
        geoObject2.idOfObject = 1
        geoObject2.name = "Entenhausen"
        geoObject2.latinName = "Fraxinus excelsior"
        geoObject2.plantDate = "28/10/2021"
        val mediaItem2 = Media()
        mediaItem.id = 0
        mediaItem.pic = R.drawable.baum2
        geoObject2.media = mediaItem2
        geoObjects.add(geoObject1)
        geoObjects.add(geoObject2)
        return geoObjects
    }

    /*
    fun addTree(toAdd: GeoObject): Boolean {
       https://gist.github.com/ycui1/5d25672430e6c014a9ef6b422f82652e
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
    */
    fun addTree(toAdd: GeoObject) {
        toAdd.idOfObject = (geoObjects.size + 1).toLong()
        geoObjects.add(toAdd)
        // saveData()
    }

    fun getObjectMedia(): List<Media> {
        val result = mutableListOf<Media>()
        for (tree in geoObjects)
            result.add(tree.media)
        return result
    }

    /**
     * @param idTree Referenz des Baumes für die DB
     *
     * Es soll eine Liste aller Events zurückgegeben werden,
     * die NUR mit dem Baum mit spezifischen ID zusammenhängen.
     *fun getEvents(idTree: Long): List<Event> {

    val result = mutableListOf<Event>()
    url = urlBase + "events"
    val jsonArrayRequest = JsonArrayRequest(
    Request.Method.GET,
    url,
    null,
    {
    val currentEvent = Event()
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
     */
    fun getEvents(idTree: Long): List<Event> {
        val result = mutableListOf<Event>()
        for (currentEvent in events) {
            if (currentEvent.idOfReference == idTree)
                result.add(currentEvent)
        }
        return result
    }

    fun getEvents(): List<String> {
        return listOf("Baumkrankheit", "Schädlinge", "Frostschäden", "Pilzbefall")
    }

    fun addEvent(toAdd: Event) {
        toAdd.id = (events.size + 1).toLong()
        events.add(toAdd)
        //saveEvents()
    }

    /**
     * @param idTree Referenz des Baumes für die DB
     *
     * Es soll eine Liste aller Tasks zurückgegeben werden,
     * die NUR mit dem Baum mit spezifischen ID zusammenhängen.
     *
     * Derzeit wird wegen dem fehlendem Request eine leere Liste zurückgegeben.
     *
     *
    fun getTasks(idTree: Long): List<Task> {

    val result = mutableListOf<Task>()
    url = urlBase + "Tasks"
    val jsonArrayRequest = JsonArrayRequest(
    Request.Method.GET,
    url,
    null,
    { response ->
    val currentTask = Task()

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
    }*/
    fun getTasks(idTree: Long): List<Task> {
        val result = mutableListOf<Task>()
        for (currentTask in tasks) {
            for (currentTree in currentTask.relatedTrees) {
                if (currentTree.idOfObject == idTree) {
                    result.add(currentTask)
                }
            }
        }
        return tasks
    }

    fun addTask(toAdd: Task) {
        toAdd.id = (tasks.size + 1).toLong()
        tasks.add(toAdd)
        //saveTasks()
    }

    fun getLatinNames(): Array<String> {
        return latinNames
    }

/*
Ab hier sind die Funktionen für die Umwandlung von Json in die Datenstruktur der App.
Es werden nicht unbedingt alle Attribute verwendet, da einige Attribute über das Filtern der
Elemente bereits gesetzt werden können (wenn man bereits Voraussetzungen bei bestimmten Typen
von Objecten festlegen kann).

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
*/

    fun addGps(position: GpsPosition) {
        position.id = (gpsPositions.size + 1).toLong()
        gpsPositions.add(position)
    }
}
