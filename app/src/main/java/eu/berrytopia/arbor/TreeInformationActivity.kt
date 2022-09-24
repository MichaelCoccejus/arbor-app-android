package eu.berrytopia.arbor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TreeInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tree_information)
        val net = NetworkActivity()

        // Dem Intent wurde im Maintenance-Adapter der Baum als GeoObject übergeben
        val currentObject = intent.getSerializableExtra("GeoObj") as GeoObject


        // Behandlung der Events
        val eventListView: RecyclerView = findViewById(R.id.eventRecyclerView)
        eventListView.layoutManager = LinearLayoutManager(this)
        eventListView.adapter = EventAdapter(net.getEvents(currentObject.idOfObject))

        val addEventBtn : FloatingActionButton = findViewById(R.id.addEventBtn)
        addEventBtn.setOnClickListener{
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        // Behandlung der Tasks
        val taskListView: RecyclerView = findViewById(R.id.taskRecyclerView)
        taskListView.layoutManager = LinearLayoutManager(this)
        taskListView.adapter = TaskAdapter(net.getTasks(currentObject.idOfObject))

        val addTaskBtn : FloatingActionButton = findViewById(R.id.addTaskBtn)
        addTaskBtn.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}