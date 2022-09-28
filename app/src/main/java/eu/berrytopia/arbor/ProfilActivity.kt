package eu.berrytopia.arbor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil_activity)
        val loggedIn: AborUser = intent.extras?.get("USER") as AborUser

        val firstNameInput: EditText = findViewById(R.id.firstNameInput)
        firstNameInput.setText(loggedIn.firstName)
        val lastNameInput: EditText = findViewById(R.id.lastNameInput)
        lastNameInput.setText(loggedIn.lastName)
        val nickNameInput: EditText = findViewById(R.id.nickNameInput)
        nickNameInput.setText(loggedIn.nickname)
        val emailInput: EditText = findViewById(R.id.eMailInput)
        emailInput.setText(loggedIn.email)
        val passWordInput: EditText = findViewById(R.id.passWordInput)
        passWordInput.hint = "Neues Password"

        val saveBtn: Button = findViewById(R.id.saveProfilBtn)
        saveBtn.setOnClickListener {
            if (firstNameInput.text.toString() != loggedIn.firstName)
                loggedIn.firstName = firstNameInput.text.toString()
            if (lastNameInput.text.toString() != loggedIn.lastName)
                loggedIn.lastName = lastNameInput.text.toString()
            if (nickNameInput.text.toString() != loggedIn.nickname)
                loggedIn.nickname = nickNameInput.text.toString()
            if (emailInput.text.toString() != loggedIn.email)
                loggedIn.email = emailInput.text.toString()
            if (passWordInput.text != null || passWordInput.text.toString() != "")
                loggedIn.password = passWordInput.text.toString()
            finishAffinity()
        }
    }
}