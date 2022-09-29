package eu.berrytopia.arbor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MaintenanceActivity : AppCompatActivity() {
    /**
     * Für das Menü in der Toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_options, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maintenance_activity)

        // Beziehen der Informationen
        val net = NetworkActivity(this)
        val mGeoObject: List<GeoObject> = net.getTrees()
        val treePicsList: List<Media> = net.getObjectMedia()

        /**
         * Einstellungen der View
         * Über den GirdLayoutManager wird die View in ein Gridraster aufgebaut.
         * Der Adapter wurde selbsterstellt.
         */
        val gridView: RecyclerView = findViewById(R.id.treeGridView)
        gridView.layoutManager = GridLayoutManager(this, 3)
        gridView.adapter = MaintenanceAdapter(this, mGeoObject, treePicsList)

        val verwaltungBut: Button = findViewById(R.id.verwaltungBtn)
        verwaltungBut.setOnClickListener {
            val intent = Intent(this, MaintenanceActivity::class.java)
            startActivity(intent)
        }

        val mapBut: Button = findViewById(R.id.mapBtn)
        mapBut.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}