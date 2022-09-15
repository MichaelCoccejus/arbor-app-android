package eu.berrytopia.arbor

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AddTreeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.addtree_activity)

        val spinner: Spinner = findViewById(R.id.latinNames)
        ArrayAdapter.createFromResource(
            this,
            R.array.testArray,
            android.R.layout.simple_spinner_item // Es gibt bereits eine vorgegebene Liste wie die Items angezeigt werden. simple_spinner_item ist eine Darstellung.
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
        }
    }
}