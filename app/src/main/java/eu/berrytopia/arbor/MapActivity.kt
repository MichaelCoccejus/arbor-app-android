package eu.berrytopia.arbor

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity


class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setSupportActionBar(findViewById(R.id.my_toolbar))


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_options, menu)
        return true
    }
}