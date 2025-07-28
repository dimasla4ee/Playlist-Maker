package com.dimasla4ee.playlistmaker

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dimasla4ee.playlistmaker.databinding.ActivityPlayerBinding
import com.google.gson.Gson

class PlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        setupWindowInsets(binding.root)

        val json = intent.getStringExtra("song_info")
        val track = Gson().fromJson(json, Track::class.java)

        binding.headerBackButton.setOnClickListener {
            finish()
        }

        //TODO: Implement snippets
        binding.songCurrentDuration.text = "0:30"

        binding.songDurationFetched.text = track.formatedDuration
        track.year.let { year ->
            if (year == null) {
                binding.songYearFetched.visibility = View.GONE
                binding.songYearLabel.visibility = View.GONE
            } else {
                binding.songYearFetched.text = year.toString()
            }
        }
        track.album.let { album ->
            if (album.isEmpty()) {
                binding.songAlbumFetched.visibility = View.GONE
                binding.songAlbumLabel.visibility = View.GONE
            } else {
                binding.songAlbumFetched.text = album
            }
        }
        binding.songGenreFetched.text = track.genre
        binding.songCountryFetched.text = track.country

        binding.songTitle.text = track.title
        binding.songAuthor.text = track.artist

        val radius = IMAGE_CORNER_RADIUS.dpToPx(this).toInt()
        val placeholder = AppCompatResources.getDrawable(this, R.drawable.ic_placeholder_45)

        placeholder?.setTint(getColor(R.color.light_gray))

        Glide.with(binding.root)
            .load(track.urlBigArtwork)
            .placeholder(placeholder)
            .transform(RoundedCorners(radius))
            .fitCenter()
            .into(binding.songCover)
    }

    private companion object {
        const val IMAGE_CORNER_RADIUS = 8f
    }
}