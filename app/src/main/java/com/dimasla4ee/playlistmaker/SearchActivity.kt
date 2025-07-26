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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.dimasla4ee.playlistmaker.databinding.ActivitySearchBinding
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var queryEditText: EditText
    private lateinit var clearQueryButton: ImageView
    private lateinit var reloadButton: Button
    private lateinit var resultLayout: LinearLayout
    private lateinit var tracksRecyclerView: RecyclerView
    private lateinit var historyAdapter: TracksAdapter

    private var query: String = EMPTY_QUERY
    private var tracks = mutableListOf<Track>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        queryEditText = binding.searchBarEditText
        clearQueryButton = binding.clearSearchBarButton
        tracksRecyclerView = binding.tracksRecyclerView
        reloadButton = binding.reloadButton
        resultLayout = binding.resultLayout

        val sharedPreferences = getSharedPreferences("HELLO", MODE_PRIVATE)
        val searchHistory = SearchHistory(sharedPreferences)
        historyAdapter = TracksAdapter(searchHistory.get()) { track ->
            searchHistory.add(track)
            historyAdapter.updateTracks(searchHistory.get())
        }
        val tracksAdapter = TracksAdapter(tracks) { track ->
            searchHistory.add(track)
        }
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager

        setContent(ContentType.NONE)

        binding.historyRecyclerView.adapter = historyAdapter
        tracksRecyclerView.adapter = tracksAdapter

        setupWindowInsets(binding.root)

        sharedPreferences.registerOnSharedPreferenceChangeListener { _, _ ->
            historyAdapter.updateTracks(searchHistory.get())
        }

        binding.clearHistoryButton.setOnClickListener {
            searchHistory.clear()
            setContent(ContentType.NONE)
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        queryEditText.apply {
            setText(query)

            doOnTextChanged { text, _, _, _ ->
                clearQueryButton.visibility = if (text.isNullOrEmpty()) GONE else VISIBLE
                query = text?.toString() ?: EMPTY_QUERY
                if (query.isEmpty()) {
                    tracksAdapter.updateTracks(emptyList())
                    setContent(ContentType.SEARCH_HISTORY)
                    historyAdapter.updateTracks(searchHistory.get())
                } else {
                    setContent(ContentType.TRACKLIST)
                }
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

                    setContent(ContentType.TRACKLIST)

                    lifecycleScope.launch {
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
                clearFocus()
                setContent(ContentType.NONE)
            }
        }

        binding.searchBar.setOnClickListener {
            queryEditText.requestFocus()
        }

        queryEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && query.isEmpty() && searchHistory.get().isNotEmpty()) {
                setContent(ContentType.SEARCH_HISTORY)
                historyAdapter.updateTracks(searchHistory.get())
            }
        }

        reloadButton.setOnClickListener {
            lifecycleScope.launch {
                getSongs(tracksAdapter)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY_KEY, query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState.getString(QUERY_KEY, EMPTY_QUERY)
    }

    private suspend fun getSongs(tracksAdapter: TracksAdapter) {
        ItunesApiClient.getSongs(
            query = query,
            doOnResponse = { _, response ->
                val responseBody = response.body()

                val contentType = when {
                    response.code() != 200 -> ContentType.ERROR
                    responseBody?.resultCount == 0 -> ContentType.NO_RESULTS
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
        binding.historyLayout.visibility = setVisibility(contentType == ContentType.SEARCH_HISTORY)
        tracksRecyclerView.visibility = setVisibility(contentType == ContentType.TRACKLIST)

        if (contentType == ContentType.NO_RESULTS || contentType == ContentType.ERROR) {
            resultLayout.visibility = VISIBLE
            binding.resultImage.setImageResource(contentType.res!!)
            binding.resultMessage.setText(contentType.text!!)
            reloadButton.visibility = setVisibility(contentType == ContentType.ERROR)
        } else {
            resultLayout.visibility = GONE
        }
    }

    private fun setVisibility(isVisible: Boolean): Int = if (isVisible) VISIBLE else GONE

    enum class ContentType(
        val res: Int?,
        val text: Int?
    ) {
        NONE(null, null),
        SEARCH_HISTORY(null, null),
        TRACKLIST(null, null),
        NO_RESULTS(R.drawable.ic_nothing_found_120, R.string.no_results),
        ERROR(R.drawable.ic_no_internet_120, R.string.network_error)
    }

    private companion object {
        const val EMPTY_QUERY = ""
        const val QUERY_KEY = "QUERY_KEY"
    }
}