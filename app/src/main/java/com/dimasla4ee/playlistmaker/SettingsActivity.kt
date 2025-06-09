package com.dimasla4ee.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.backButton).apply {
            setOnClickListener {
                val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}