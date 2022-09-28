package eu.berrytopia.arbor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AddEventActivity : AppCompatActivity() {
    var cameraRequest = 1888
    lateinit var imageView: ImageView

    /**
     Activity ist zuständig für die Ansicht addevent_activity und benötigt keine weiteren Klassen oder Elemente aus dem layout-Ordner.
     Die Activity fügt neue Elemente mit der Referenz zu dem aktuell zu bearbeitenden GeoObject hinzu (siehe Diagramm).

     TODO: Die Funktionalitäten der Buttons.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addevent_activity)

        val net = NetworkActivity(this)
        // Array der Kategorien der Events aus der NetworkActivity.
        val eventArray = net.getEvents()
         // Array der Bäume aus der NetworkActivity.
        val treeArray = net.getTrees()

        val newEvent = Event()

        val eventSpinner: Spinner = findViewById(R.id.eventName)
        val eventArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventArray)
        eventSpinner.adapter = eventArrayAdapter
        eventSpinner.setSelection(0)


        val treeSpinner: Spinner = findViewById(R.id.treeNameEvent)
        val treeArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, treeArray)
        treeSpinner.adapter = treeArrayAdapter
        treeSpinner.setSelection(0)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.imagePreview)

        val photoBtn: Button = findViewById(R.id.photoEventBtn)
        photoBtn.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

        val addPhotoBtn: Button = findViewById(R.id.addPhotoBtn)
        addPhotoBtn.setOnClickListener{
            // TODO: Zugriff auf Fotos in Dateien zur Auwahl
        }

        val addDocumentBtn: Button = findViewById(R.id.addDocumentBtn)
        addDocumentBtn.setOnClickListener{
            // TODO: Zugriff auf Dokumente in Dateien zur Auwahl
        }

        val description: EditText = findViewById(R.id.descInputField)

        val saveBtn: Button = findViewById(R.id.saveEventButton)
        saveBtn.setOnClickListener{
             newEvent.userDescription = description.text.toString()
        }

        val discardBtn: Button = findViewById(R.id.discardEventButton)
        discardBtn.setOnClickListener{
                        val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
    }
}