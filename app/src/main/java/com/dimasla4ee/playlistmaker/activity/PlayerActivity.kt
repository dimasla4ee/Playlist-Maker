package com.dimasla4ee.playlistmaker.activity

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dimasla4ee.playlistmaker.Keys
import com.dimasla4ee.playlistmaker.R
import com.dimasla4ee.playlistmaker.Track
import com.dimasla4ee.playlistmaker.databinding.ActivityPlayerBinding
import com.dimasla4ee.playlistmaker.dpToPx
import com.dimasla4ee.playlistmaker.setupWindowInsets
import com.dimasla4ee.playlistmaker.show
import com.dimasla4ee.playlistmaker.toMmSs

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var track: Track
    private var mediaPlayer = MediaPlayer()
    private var playerState = PlayerState.DEFAULT

    private val isPlaying: Boolean
        get() = playerState == PlayerState.PLAYING

    private val currentPosition: Int
        get() = mediaPlayer.currentPosition

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setupWindowInsets(binding.root)

        handler = Handler(mainLooper)

        track = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Keys.TRACK_INFO, Track::class.java)
        } else {
            @Suppress("deprecation")
            intent.getParcelableExtra(Keys.TRACK_INFO)
        })!!
        fillTrackInfo(track)

        binding.panelHeader.setOnIconClickListener { finish() }

        preparePlayer(track.urlPreviewAudio)

        binding.playButton.setOnClickListener {
            playbackControl()
        }

        binding.songCurrentDuration.text = currentPosition.toMmSs()
        updateTrackTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun fillTrackInfo(track: Track) {
        binding.apply {
            songDurationFetched.text = track.formatedDuration

            track.year.let { year ->
                if (year == null) {
                    songYearFetched.show(false)
                    songYearLabel.show(false)
                } else {
                    songYearFetched.text = year.toString()
                }
            }

            track.album.let { album ->
                if (album.isEmpty()) {
                    songAlbumFetched.show(false)
                    songAlbumLabel.show(false)
                } else {
                    songAlbumFetched.text = album
                }
            }

            songGenreFetched.text = track.genre
            songCountryFetched.text = track.country

            songTitle.text = track.title
            songAuthor.text = track.artist

            val dpRadius = resources.getDimension(R.dimen.small_100)
            val pxRadius = dpRadius.dpToPx(this@PlayerActivity).toInt()
            val placeholder = AppCompatResources.getDrawable(
                this@PlayerActivity, R.drawable.ic_placeholder_45
            )

            placeholder?.setTint(getColor(R.color.light_gray))

            Glide.with(root)
                .load(track.urlBigArtwork)
                .placeholder(placeholder)
                .transform(RoundedCorners(pxRadius))
                .fitCenter()
                .into(songCover)
        }
    }

    private fun preparePlayer(source: String?) {
        mediaPlayer.apply {
            setDataSource(source)
            prepareAsync()
            setOnPreparedListener { playerState = PlayerState.PREPARED }
            setOnCompletionListener { stopPlayer() }
        }
    }

    private fun playPlayer() {
        mediaPlayer.start()
        playerState = PlayerState.PLAYING
        handler.post(updateTrackTimerRunnable)
        binding.playButton.setImageResource(R.drawable.ic_pause_24)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = PlayerState.PAUSED
        handler.removeCallbacks(updateTrackTimerRunnable)
        binding.playButton.setImageResource(R.drawable.ic_play_24)
    }

    private fun stopPlayer() {
        mediaPlayer.stop()
        mediaPlayer.prepareAsync()
        playerState = PlayerState.PREPARED
        handler.removeCallbacks(updateTrackTimerRunnable)
        binding.songCurrentDuration.text = 0.toMmSs()
        binding.playButton.setImageResource(R.drawable.ic_play_24)
    }

    private fun releasePlayer() {
        mediaPlayer.release()
        playerState = PlayerState.DEFAULT
        handler.removeCallbacks(updateTrackTimerRunnable)
    }

    private fun playbackControl() {
        when (playerState) {
            PlayerState.PLAYING -> {
                pausePlayer()
            }

            PlayerState.PREPARED, PlayerState.PAUSED -> {
                playPlayer()
            }

            else -> Unit
        }
    }

    private val updateTrackTimerRunnable = object : Runnable {
        override fun run() {
            if (isPlaying) {
                binding.songCurrentDuration.text = currentPosition.toMmSs()
                handler.postDelayed(this, DELAY)
            }
        }
    }

    private fun updateTrackTimer() {
        handler.post(updateTrackTimerRunnable)
    }

    enum class PlayerState {
        DEFAULT,
        PREPARED,
        PLAYING,
        PAUSED
    }

    private companion object {
        const val DELAY = 300L
    }
}

