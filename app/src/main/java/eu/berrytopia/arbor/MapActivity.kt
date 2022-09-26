package eu.berrytopia.arbor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class MapActivity : AppCompatActivity() {
    // Für das Menü in der Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_options, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)

        // Nur zum Erstellen von Bäumen. Plantage werden zeitlich nicht reichen.
        val floatBut: FloatingActionButton = findViewById(R.id.floatingActionButton)
        floatBut.setOnClickListener {
                val intent = Intent(this, AddTreeActivity::class.java)
                startActivity(intent)
        }

        // Eine vereinfachte Version von BottomNavigtionBar
        val verwaltungBtn: Button = findViewById(R.id.verwaltungBtn)
        verwaltungBtn.setOnClickListener{
            val intent = Intent(this, MaintenanceActivity::class.java)
            startActivity(intent)
        }

        val mapBut: Button = findViewById(R.id.mapBtn)
        mapBut.setOnClickListener{
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        // Ab hier sind die Funktion für die Karte
        initMap()
        initGps()
    }

    /*
    Geofence Plantage
    Die Fence sollte am Ende geladen werden.
     */
    val fence =
        listOf(
            GeoPoint(49.948457, 7.936816), //top left
            GeoPoint(49.948420, 7.937085), //top right
            GeoPoint(49.947366, 7.937146), //bottom right
            GeoPoint(49.947454, 7.936883) //bottom left
        )

    var map: MapView? = null

    private fun initMap() {
        Configuration.getInstance().userAgentValue = applicationContext.packageName

        map = findViewById(R.id.map)

        map!!.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        map!!.controller.setZoom(19.0)

        updateMapPosition(GeoPoint(49.950314, 7.9370000))

        val poly = org.osmdroid.views.overlay.Polygon(map!!)
        poly.points = fence

        map!!.overlays.add(poly)
    }

    lateinit var client: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    private fun initGps() {
        client = LocationServices.getFusedLocationProviderClient(this)
        client.requestLocationUpdates(
            /*
             Nicht deprecated wenn es über den statischen Weg geht.
             Intervall und Priority könnte man noch über apply statt setInterval und setPriority setzen.
             */
            LocationRequest.create().setInterval(1000).setPriority(Priority.PRIORITY_HIGH_ACCURACY),
            //LocationRequest().setInterval(1000).setPriority(Priority.PRIORITY_HIGH_ACCURACY),
            locationCallBack,
            Looper.myLooper()
        )
    }

    var currentLat: String = ""
    var currentLong: String = ""

    var locationCallBack = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            for (it in locationResult.locations) {
                if (currentLat != "%.4f".format(it.latitude) ||
                    currentLong != "%.4f".format(it.longitude)
                ) {

                    currentLat = "%.4f".format(it.latitude)
                    currentLong = "%.4f".format(it.longitude)
                    Log.i("MYTEST", "Lat: ${it.latitude}")
                    Log.i("MYTEST", "Lon: ${it.longitude}")

                    runOnUiThread {
                        updateMapPosition(GeoPoint(it.latitude, it.longitude))
                    }
                }
            }
        }
    }

    fun updateMapPosition(myPos: GeoPoint) {
        map!!.controller.setCenter(myPos)
        map!!.controller.setZoom(19.0)
    }


}