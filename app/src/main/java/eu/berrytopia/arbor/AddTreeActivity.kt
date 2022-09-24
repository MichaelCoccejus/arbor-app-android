package eu.berrytopia.arbor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class AddTreeActivity : AppCompatActivity() {
    var cameraRequest = 1888
    lateinit var imageView: ImageView

    /**
     * Die Ansicht (addtree_activity) dient zum Erstellen neuer Bäume.
     * Die lateinischen Namen werden aus der Datenbank bezogen und als Array im res>values>strings.xml abgelegt.
     * Bekannte Informationen sollten automatisch hinzugefügt werden.
     *
     * TODO: Eventuelle Manipulation des Arrays für lateinische Namen.
     * TODO: Plantage muss zur Ansicht zu Verfügung gestellt werden.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtree_activity)

        val newGeoObject = GeoObject()
        newGeoObject.setTypeTree()
        val client: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        val longitudeView: TextView = findViewById(R.id.longitudeTextView)
        val latitudeView: TextView = findViewById(R.id.latitudeTextView)
        val altitudeView: TextView = findViewById(R.id.altitudeTextView)

        // Hier wird aus dem selben Array die Strings bezogen. Es muss noch ein Array der User geben.
        val spinner: Spinner = findViewById(R.id.latinNames)
        ArrayAdapter.createFromResource(
            this,
            R.array.testArray,
            android.R.layout.simple_spinner_item // Es gibt bereits eine vorgegebene Liste wie die Items angezeigt werden. simple_spinner_item ist eine Darstellung.
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
        }

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )
        imageView = findViewById(R.id.imageHolder)
        val photoBtn: Button = findViewById(R.id.photoEventBtn2)

        photoBtn.setOnClickListener {
            /*
            Anscheinend gibt es eine nicht deprecated Version von startActivityForResult.
            Kann noch getestet werden und evtl angewendet werden. Dient als kleine Hilfestellung.
            Bleibt solange auskommentiert bis behandelt werden kann.
            var resultPhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Fotos in ImageView setzen.
                    val photo: Bitmap = result.data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(photo)
                }
            }
            */

            val locResult = client.lastLocation.result
            val longitude = locResult.longitude
            val latitude = locResult.latitude
            val altitude = locResult.altitude
            longitudeView.text = longitude.toString()
            latitudeView.text = latitude.toString()
            altitudeView.text = altitude.toString()
            newGeoObject.position = GpsPosition(longitude.toLong(), latitude.toLong(), altitude.toLong())

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            // Fotos in ImageView setzen.
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
    }
}