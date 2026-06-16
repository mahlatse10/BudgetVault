package com.example.budgetvault

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.usernameInput)
        val password = findViewById<EditText>(R.id.passwordInput)
        val login = findViewById<Button>(R.id.loginBtn)

        login.setOnClickListener {
            login.setOnClickListener {
                if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                    val intent = android.content.Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                } else {
                    android.widget.Toast.makeText(
                        this,
                        "Enter all fields",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}