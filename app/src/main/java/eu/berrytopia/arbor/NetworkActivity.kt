package eu.berrytopia.arbor

class NetworkActivity {
    /*
   *   Network-Klasse sollte die Funktionalitäten des Clients im Netz abdecken.
   *   Dazu zählen Login-Prozess, Datenabruf vom Server und Datenspeicherung zum Server.
    */


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

    /** TODO: Implementing Getter for data from sensors
     *
     * Daten sollten vom Server abgerufen werden.
     * Parameter und Rückgabe muss noch hinzugefügt werden.
     */
    fun getSensorData() {

    }

    /** TODO: Implementing Setter for a new plantage and sending 4 Points to server
     *  @param geoPoint1 1. Geopunkt der Plantage
     *  @param geoPoint2 2. Geopunkt der Plantage
     *  @param geoPoint3 3. Geopunkt der Plantage
     *  @param geoPoint4 4. Geopunkt der Plantage
     *
     *  Die Daten sollten dem Server gesendet werden, damit sie jederzeit abgerufen werden können.
     */
    fun createPlantage(geoPoint1: Int, geoPoint2: Int, geoPoint3: Int, geoPoint4: Int) {

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
    fun getTree() {

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
    fun getEvents(idTree: Long) : List<Event>{
        // HttpRequest mit der Referenz des Baumes (ID)
        return listOf()
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
    fun getTasks(idTree: Long) : List<Task> {
        return listOf()
    }
}