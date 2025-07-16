package com.dimasla4ee.playlistmaker

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.dimasla4ee.playlistmaker.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private var query: String = EMPTY_QUERY

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

        val binding = ActivitySearchBinding.inflate(layoutInflater)
        val searchBarContainer = binding.searchBar
        val queryEditText = binding.searchBarEditText
        val clearQueryButton = binding.clearSearchBarButton
        val backButton = binding.backButton
        val tracksRecyclerView = binding.tracksRecyclerView
        val tracksAdapter = TracksAdapter(placeholderTracks)
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager

        setContentView(binding.root)

        backButton.setOnClickListener {
            finish()
        }

        queryEditText.setText(query)

        queryEditText.doOnTextChanged { text, _, _, _ ->
            clearQueryButton.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
            query = text?.toString() ?: EMPTY_QUERY
        }

        clearQueryButton.setOnClickListener {
            inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            queryEditText.apply {
                setText(EMPTY_QUERY)
                clearFocus()
            }
        }

        searchBarContainer.setOnClickListener {
            queryEditText.requestFocus()
        }

        tracksRecyclerView.adapter = tracksAdapter
    }

    private companion object {
        const val EMPTY_QUERY = ""
        const val QUERY_KEY = "QUERY_KEY"
    }
}