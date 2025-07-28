package com.dimasla4ee.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TracksViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val context = itemView.context
    private val trackContainer: LinearLayout = itemView.findViewById(R.id.trackContainer)
    private val trackTitle: TextView = itemView.findViewById(R.id.titleTextView)
    private val artistAndTime: TextView = itemView.findViewById(R.id.artistAndTimeTextView)
    private val albumCover: ImageView = itemView.findViewById(R.id.albumCover)

    fun bind(
        track: Track,
        onItemClick: (Track) -> Unit
    ) {
        val dpRadius = itemView.resources.getDimension(R.dimen.small_25)
        val pxRadius = dpRadius.dpToPx(context).toInt()
        val placeholder = AppCompatResources.getDrawable(context, R.drawable.ic_placeholder_45)

        placeholder?.setTint(context.getColor(R.color.light_gray))

        trackTitle.text = track.title

        artistAndTime.text = context.getString(
            R.string.artist_and_time,
            track.artist, track.formatedDuration
        )

        trackContainer.setOnClickListener {
            onItemClick(track)
        }

        Glide.with(itemView)
            .load(track.urlPreviewArtwork)
            .placeholder(placeholder)
            .transform(RoundedCorners(pxRadius))
            .fitCenter()
            .into(albumCover)
    }
}