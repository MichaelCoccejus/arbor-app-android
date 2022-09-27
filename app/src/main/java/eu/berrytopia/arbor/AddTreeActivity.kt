package eu.berrytopia.arbor

import android.Manifest
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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.*
import java.net.URL
import java.util.*

class AddTreeActivity : AppCompatActivity() {
    var cameraRequest = 1888
    lateinit var imageView: ImageView

    /**
     * Die Ansicht (addtree_activity) dient zum Erstellen neuer Bäume.
     * Die lateinischen Namen werden aus der Datenbank bezogen und als Array im res>values>strings.xml abgelegt.
     * Bekannte Informationen sollten automatisch hinzugefügt werden.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtree_activity)

        //val net = NetworkActivity(this)
        val newGeoObject = GeoObject()
        newGeoObject.setTypeTree()
        //net.addTree(newGeoObject)

        //val latinNameArray: Array<String> = net.getLatinNames()
        val latinNameArray: Array<String> = arrayOf("Test", "Mushroom", "Mario")

        // Hier wird aus dem selben Array die Strings bezogen. Es muss noch ein Array der User geben.
        val spinner: Spinner = findViewById(R.id.latinNames)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, latinNameArray)
        spinner.adapter = arrayAdapter
        spinner.setSelection(0)

        // Funktionalität für die Kamera.
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )
        imageView = findViewById(R.id.imageViewTree)
        val photoBtn: Button = findViewById(R.id.photoEventBtn2)
        photoBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

        val nameEditText: EditText = findViewById(R.id.editTextName)
        // val sizeEditText: EditText = findViewById(R.id.editTextSize)
        val saveBtn: Button = findViewById(R.id.saveTreeButton)
        saveBtn.setOnClickListener {
            newGeoObject.name = nameEditText.text.toString()
            
        }

        val discardBtn: Button = findViewById(R.id.discardTreeButton)
        discardBtn.setOnClickListener {
            val intent = Intent(this, AddTreeActivity::class.java)
            startActivity(intent)
        }
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            * Aktualisieren der TextViews für die Koordinaten
            */
            val lat = findViewById<TextView>(R.id.latitudeTextView)
            val lon = findViewById<TextView>(R.id.longitudeTextView)
            val alt = findViewById<TextView>(R.id.altitudeTextView)

            lat.text = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
            lon.text = exif.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE)
            alt.text = exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE)
        }
    }

    /*
    * Funktion, die aus der Bitmap ein JPEG macht,  und die URL zurückgibt,
    * damit man mit dem ExifInterface die GPS Daten auslesen kann
    */
    private fun bitmapToFileURL(photo: Bitmap): URL {
        val wrapper = ContextWrapper(applicationContext)

        var file = wrapper.getDir("Photos", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpeg")

        try {
            val bos = ByteArrayOutputStream()
            val stream: OutputStream = FileOutputStream(file)
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val bitmapData = bos.toByteArray()
            stream.write(bitmapData)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.toURI().toURL()
    }

    /*
    * Funktion die das Foto mit gegebenem Namen in den Dateien abspeichert
    */
    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? {
        //create a file to write bitmap data
        var file: File? = null
        try {
            file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
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