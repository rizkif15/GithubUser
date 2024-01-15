package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.mActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@SearchActivity, "Anda mencari : "+searchView.text, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SearchActivity, MainActivity::class.java)
                    intent.putExtra("kueri", searchView.text.toString()) // Mengirim data dengan nama "kueri"
                    startActivity(intent)
                    false
                }
        }
    }
}