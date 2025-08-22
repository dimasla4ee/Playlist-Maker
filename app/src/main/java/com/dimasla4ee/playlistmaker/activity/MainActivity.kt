package com.dimasla4ee.playlistmaker.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dimasla4ee.playlistmaker.databinding.ActivityMainBinding
import com.dimasla4ee.playlistmaker.setupWindowInsets

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchButton: Button
    private lateinit var mediaLibraryButton: Button
    private lateinit var settingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        searchButton = binding.searchButton
        mediaLibraryButton = binding.mediaLibraryButton
        settingsButton = binding.settingsButton

        setContentView(binding.root)
        enableEdgeToEdge()

        setupWindowInsets(binding.root)

        searchButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        mediaLibraryButton.setOnClickListener {
            val intent = Intent(this@MainActivity, MediaLibraryActivity::class.java)
            startActivity(intent)

        }

        settingsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

}