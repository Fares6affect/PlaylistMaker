package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SearchActivity : AppCompatActivity() {
    var editTextInfo:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val arrowBack = findViewById<ImageView>(R.id.arrowBack)
        arrowBack.setOnClickListener {
            finish()
        }

        val searchButton = findViewById<ImageView>(R.id.searchButton)
        val editTextLine = findViewById<EditText>(R.id.EditTextLine)
        val clearButton = findViewById<ImageView>(R.id.clearButton)
        editTextLine.setText(editTextInfo)

        searchButton.setOnClickListener {
            editTextLine.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editTextLine, InputMethodManager.SHOW_IMPLICIT)
        }

        val textWatcherOnLineSearch = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                } else {
                }
                editTextInfo = s?.toString()?:""
                clearButton.visibility = clearButtonVisibility(s)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }

        editTextLine.addTextChangedListener(textWatcherOnLineSearch)

        clearButton.setOnClickListener {
            editTextLine.setText("")
            val editTextLine = findViewById<EditText>(R.id.EditTextLine)
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(editTextLine.windowToken, 0)
        }

    }
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT_INFO,editTextInfo)
    }

    companion object{
        const val EDIT_TEXT_INFO="EDIT_TEXT_INFO"
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editTextInfo = savedInstanceState.getString(EDIT_TEXT_INFO)
    }
}