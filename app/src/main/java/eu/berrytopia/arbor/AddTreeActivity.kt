package eu.berrytopia.arbor

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ExifInterface

import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.inputmethod.InputContentInfo
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.*
import java.net.URL
import java.net.URI
import java.nio.Buffer
import java.util.*

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

            val pic = bitmapToFileURL(photo)
            bitmapToFile(photo, "testPic.jpg")

            // Verwendung des ExifInterface für die Koodinaten des Fotos
            val exif = ExifInterface(pic.openStream())
            Log.i("EXIF", "Latitude ${exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)}")
            Log.i("EXIF", "Longitude ${exif.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE)}")
            Log.i("EXIF", "Altitude ${exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE)}")

            /*
            * aktualisieren der TextViews für die Koordinaten
            */

            val lat = findViewById<TextView>(R.id.latitudeTextView)
            val lon = findViewById<TextView>(R.id.longitudeTextView)
            val alt = findViewById<TextView>(R.id.altitudeTextView)

            lat.setText(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE))
            lon.setText(exif.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE))
            alt.setText(exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE))
        }
    }
    /*
    *  Funktion, die aus der Bitmap ein JPEG macht,  und die URL zurückgibt,
    * damit man mit dem ExifInterface die GPS Daten auslesen kann
    *
    */
    private fun bitmapToFileURL(photo: Bitmap): URL {
        val wrapper = ContextWrapper(applicationContext)

        var file = wrapper.getDir("Photos", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpeg")

        try {
            val bos = ByteArrayOutputStream()
            val stream : OutputStream = FileOutputStream(file)
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val bitmapData = bos.toByteArray()
            stream.write(bitmapData)
            stream.flush()
            stream.close()
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        return file.toURI().toURL()
    }
    /*
    * Funktion die das Foto mit gegebenem Namen in den Dateien abspeichert
    *
    */
    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? {
        //create a file to write bitmap data
        var file: File? = null
        try {
            file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + fileNameToSave)
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }
}