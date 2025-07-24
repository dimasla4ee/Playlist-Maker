package com.dimasla4ee.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val context = itemView.context
    private val trackContainer: LinearLayout = itemView.findViewById(R.id.trackContainer)
    private val trackTitle: TextView = itemView.findViewById(R.id.titleTextView)
    private val artistAndTime: TextView = itemView.findViewById(R.id.artistAndTimeTextView)
    private val albumCover: ImageView = itemView.findViewById(R.id.albumCover)

    fun bind(track: Track) {
        val radius = IMAGE_CORNER_RADIUS.dpToPx(context).toInt()
        val placeholder = AppCompatResources.getDrawable(context, R.drawable.ic_placeholder_45)

        placeholder?.setTint(context.getColor(R.color.light_gray))

        trackTitle.text = track.title

        artistAndTime.text = context.getString(
            R.string.artist_and_time,
            track.artist, track.formatedDuration
        )

        Glide.with(itemView)
            .load(track.artworkUrl)
            .placeholder(placeholder)
            .transform(RoundedCorners(radius))
            .fitCenter()
            .into(albumCover)
    }

    companion object {
        const val IMAGE_CORNER_RADIUS = 2f
    }
}