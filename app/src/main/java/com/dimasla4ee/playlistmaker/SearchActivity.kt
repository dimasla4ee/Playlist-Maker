package com.dimasla4ee.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager

        val searchBarLayout = findViewById<LinearLayout>(R.id.searchBar)
        val searchBar = findViewById<EditText>(R.id.searchBarEditText)
        val clearQueryButton = findViewById<ImageView>(R.id.clearSearchBarButton)

        val searchBarTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //TODO: Not yet implemented
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                clearQueryButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO: Not yet implemented
            }
        }

        searchBar.addTextChangedListener(searchBarTextWatcher)

        clearQueryButton.setOnClickListener {
            inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            searchBar.apply {
                setText("")
                clearFocus()
            }
        }

        searchBarLayout.setOnClickListener {
            searchBar.requestFocus()
        }
    }

}