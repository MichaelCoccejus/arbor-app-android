package eu.berrytopia.arbor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.graphics.Bitmap
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

        val net = NetworkActivity(this)
        val userList = net.getUsers()

        val spinner: Spinner = findViewById(R.id.userSelection)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, userList)
        spinner.adapter = arrayAdapter

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.imageHolder)

        val photoBtn: Button = findViewById(R.id.photoButton)
        photoBtn.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

         val saveBtn: Button = findViewById(R.id.saveTaskButton)
         saveBtn.setOnClickListener{

         }

         val discardBtn: Button = findViewById(R.id.discardTaskButton)
         discardBtn.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
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