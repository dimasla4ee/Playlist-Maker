package com.dimasla4ee.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.searchButton).also { button ->
            val onClickListener = object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(this@MainActivity, SearchActivity::class.java)
                    startActivity(intent)
                }
            }
            button.setOnClickListener(onClickListener)
        }

        val mediaLibraryButton = findViewById<Button>(R.id.mediaLibraryButton).apply {
            setOnClickListener {
                val intent = Intent(this@MainActivity, MediaLibraryActivity::class.java)
                startActivity(intent)
            }
        }

        val settingsButton = findViewById<Button>(R.id.settingsButton).apply {
            setOnClickListener {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}