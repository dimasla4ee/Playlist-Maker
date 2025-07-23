package com.dimasla4ee.playlistmaker

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.dimasla4ee.playlistmaker.databinding.ActivitySearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private var query: String = EMPTY_QUERY
    private var tracks = mutableListOf<Track>()

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchBarContainer: LinearLayout
    private lateinit var queryEditText: EditText
    private lateinit var clearQueryButton: ImageView
    private lateinit var backButton: ImageView
    private lateinit var resultImage: ImageView
    private lateinit var resultMessage: TextView

    private lateinit var reloadButton: Button
    private lateinit var tracksRecyclerView: RecyclerView

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY_KEY, query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState.getString(QUERY_KEY, EMPTY_QUERY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tracksAdapter = TracksAdapter(tracks)
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        val scope = CoroutineScope(Dispatchers.Main)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        searchBarContainer = binding.searchBar
        queryEditText = binding.searchBarEditText
        clearQueryButton = binding.clearSearchBarButton
        backButton = binding.backButton
        resultImage = binding.resultImage
        resultMessage = binding.resultMessage
        tracksRecyclerView = binding.tracksRecyclerView
        reloadButton = binding.reloadButton

        setContentView(binding.root)

        backButton.setOnClickListener {
            finish()
        }

        queryEditText.apply {
            setText(query)

            doOnTextChanged { text, _, _, _ ->
                clearQueryButton.visibility = if (text.isNullOrEmpty()) GONE else VISIBLE
                query = text?.toString() ?: EMPTY_QUERY
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

                    scope.launch {
                        getSongs(tracksAdapter)
                    }

                    true
                }
                false
            }
        }

        clearQueryButton.setOnClickListener {
            inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            queryEditText.apply {
                setText(EMPTY_QUERY)
                setContent(ContentType.TRACKLIST)
                tracksAdapter.updateTracks(emptyList())
                clearFocus()
            }
        }

        searchBarContainer.setOnClickListener {
            queryEditText.requestFocus()
        }

        reloadButton.setOnClickListener {
            scope.launch { getSongs(tracksAdapter) }
        }

        tracksRecyclerView.adapter = tracksAdapter
    }

    private suspend fun getSongs(tracksAdapter: TracksAdapter) {
        ItunesApiClient.getSongs(
            query = query,
            doOnResponse = { _, response ->
                val responseBody = response.body()

                val contentType = when {
                    response.code() != 200 -> ContentType.ERROR
                    responseBody?.resultCount == 0 -> ContentType.NONE
                    else -> ContentType.TRACKLIST
                }
                setContent(contentType)

                val loadedTracks = responseBody?.results ?: emptyList()
                tracksAdapter.updateTracks(loadedTracks)
            },
            doOnFailure = { _, _ ->
                setContent(ContentType.ERROR)
            }
        )
    }

    private fun setContent(contentType: ContentType) {
        if (contentType == ContentType.TRACKLIST) {
            tracksRecyclerView.visibility = VISIBLE
        } else {
            tracksRecyclerView.visibility = GONE
            resultImage.setImageResource(contentType.res!!)
            resultMessage.setText(contentType.text!!)
            reloadButton.visibility = if (contentType == ContentType.ERROR) VISIBLE else GONE
        }
    }

    enum class ContentType(
        val res: Int?,
        val text: Int?
    ) {
        TRACKLIST(null, null),
        NONE(R.drawable.ic_nothing_found_120, R.string.no_results),
        ERROR(R.drawable.ic_no_internet_120, R.string.network_error)
    }

    private companion object {
        const val EMPTY_QUERY = ""
        const val QUERY_KEY = "QUERY_KEY"
    }
}