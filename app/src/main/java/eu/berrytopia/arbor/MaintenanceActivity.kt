package eu.berrytopia.arbor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MaintenanceActivity : AppCompatActivity() {
    /**
    Die Activity ist zuständig für das Layout maintenance_activity.
    Es wird MaintenanceActivity benötigt um die Ansicht bzw Anordnung der Elemente in ein Grid ähnliches Muster zu bekommen.
    Die Informationen sollten aus dem GeoObject bezogen werden bzw. Elemente mit der Referenz zu diesem GeoObject

     TODO: Layout für die einzelnen Elemente basteln und dem Adapter zur Verfügung stellen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maintenance_activity)

        val net = NetworkActivity(this)
        val mGeoObject: List<GeoObject> = net.getTrees()
        val treePicsList: List<Media> = net.getObjectMedia()

        val gridView : RecyclerView = findViewById(R.id.treeGridView)
        gridView.layoutManager = GridLayoutManager(this, 3)
        gridView.adapter = MaintenanceAdapter(this, mGeoObject, treePicsList)

        val verwaltungBut: Button = findViewById(R.id.verwaltungBtn)
        verwaltungBut.setOnClickListener{
            val intent = Intent(this, MaintenanceActivity::class.java)
            startActivity(intent)
        }

        val mapBut: Button = findViewById(R.id.mapBtn)
        mapBut.setOnClickListener{
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}