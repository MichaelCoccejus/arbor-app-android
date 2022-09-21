package eu.berrytopia.arbor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MaintenanceActivity : AppCompatActivity() {
    /**
    Die Activity ist zuständig für das Layout maintenance_activity.
    Es wird MaintenanceActivity benötigt um die Ansicht bzw Anordnung der Elemente in ein Grid ähnliches Muster zu bekommen.
    Die Informationen sollten aus dem GeoObject bezogen werden bzw. Elemente mit der Referenz zu diesem GeoObject

     TODO: Layout für die einzelnen Elemente basteln und dem Adapter zur Verfügung stellen.
     TODO: Funktionalität für die ButtomNavigationBar implementieren.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maintenance_activity)

        val gridView : RecyclerView = findViewById(R.id.treeGridView)
        gridView.layoutManager = GridLayoutManager(this, 5)
    }
}