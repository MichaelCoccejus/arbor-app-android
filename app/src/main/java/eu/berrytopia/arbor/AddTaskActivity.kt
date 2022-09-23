package eu.berrytopia.arbor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AddTaskActivity : AppCompatActivity() {
    var cameraRequest = 1888
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtask_activity)

        // Hier wird aus dem selben Array die Strings bezogen. Es muss noch ein Array der User geben.
        val spinner: Spinner = findViewById(R.id.userSelection)
        ArrayAdapter.createFromResource(
            this,
            R.array.testArray,
            android.R.layout.simple_spinner_item // Es gibt bereits eine vorgegebene Liste wie die Items angezeigt werden. simple_spinner_item ist eine Darstellung.
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
        }

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.imageHolder)

        val photoBtn: Button = findViewById(R.id.photoButton)
        photoBtn.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }
    }
}