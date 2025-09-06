package com.dimasla4ee.playlistmaker.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dimasla4ee.playlistmaker.R
import com.dimasla4ee.playlistmaker.databinding.ActivityMediaLibraryBinding
import com.dimasla4ee.playlistmaker.setupWindowInsets

class MediaLibraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaLibraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediaLibraryBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_media_library)
        enableEdgeToEdge()

        setupWindowInsets(binding.root)
    }
}