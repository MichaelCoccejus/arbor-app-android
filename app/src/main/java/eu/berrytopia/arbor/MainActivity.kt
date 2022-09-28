package eu.berrytopia.arbor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    var RECORD_REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val net = NetworkActivity(this) // Wird für die Bearbeitung über Netzwerk benötigt.

        if (!Environment.isExternalStorageManager()) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
            val uri: Uri = Uri.fromParts("package", this.packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val gpsPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, gpsPermissions, RECORD_REQUEST_CODE)
        }

        val buttonClick = findViewById<Button>(R.id.loginBtn)
        buttonClick.setOnClickListener {
            val userNameInput: EditText = findViewById(R.id.userNameInput)
            val passWordInput: EditText = findViewById(R.id.passwordInput)
            if (net.login(userNameInput.text.toString(), passWordInput.text.toString())) {
                val intent = Intent(this, MapActivity::class.java)
                val userData: AborUser = net.getEditor()
                intent.putExtra("USER", userData)
                startActivity(intent)
            } else
                Toast.makeText(this, "Username/Password ist falsch.", Toast.LENGTH_SHORT).show()
        }
    }
}