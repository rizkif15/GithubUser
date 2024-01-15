package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.mActivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.R
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.DetailUserResponse
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.databinding.ActivityDetailBinding

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.local.entity.FavEntity
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc.DetailViewModel
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc.FavViewModel
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc.SectionsPagerAdapter
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc.ViewModelFactory


class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var progressBar2: ProgressBar
    private lateinit var avatarImageView2: ImageView

    private lateinit var tvFollowers: TextView
    private lateinit var tvFollowing: TextView
    private lateinit var tvNama: TextView
    private lateinit var tvUsername: TextView

    private lateinit var fabFavorit: FloatingActionButton
    private var isFavorited = false
    private lateinit var favViewModel: FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val avatarUrl = intent.getStringExtra("avatarurl")

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username // Set nilai username
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter


        // Inisialisasi elemen-elemen tampilan
        progressBar2 = binding.progressBar2
        avatarImageView2 = binding.avatarImageView2
        tvFollowers = binding.tvFollowers
        tvFollowing = binding.tvFollowing
        tvNama = binding.tvNama
        tvUsername = binding.tvUsername

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        showProgressBar() // Menampilkan ProgressBar saat permintaan dimulai

        // Aktifkan tombol back di toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.topBar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

        viewModel.getUserData(username ?: "").observe(this) { user ->
            hideProgressBar() // Sembunyikan ProgressBar saat mendapatkan respons

            if (user != null) {
                fillUserData(user) // Mengisi data ke tampilan detail
                // Ambil referensi ke MaterialToolbar di XML
                // Set teks judul toolbar dengan nilai dari user.login
                toolbar.title = user.login
            } else {
                // Tangani kesalahan atau pengguna tidak ditemukan di sini
            }
        }

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        // Inisialisasi FAB
        fabFavorit = findViewById(R.id.btnFavorite)

        // Inisialisasi ViewModel
        favViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application))[FavViewModel::class.java]

        // Setup FAB click listener
        fabFavorit.setOnClickListener {

            // Lakukan pengecekan status favorit
            if (isFavorited) {
                // Jika sudah di-favoritkan, hapus data dari database
                val favEntity = FavEntity(username ?: "", avatarUrl ?: "", true) // Pastikan username dan avatarUrl tidak null
                favViewModel.deleteFavUser(favEntity)
                // Update UI FAB
                fabFavorit.setImageResource(R.drawable.baseline_favorite_24) // Gambar bookmark biasa
            } else {
                // Jika belum di-favoritkan, tambahkan data ke database
                val favEntity = FavEntity(username ?: "", avatarUrl ?: "", false) // Pastikan username dan avatarUrl tidak null
                favViewModel.saveFavUser(favEntity)
                // Update UI FAB
                fabFavorit.setImageResource(R.drawable.baseline_favorite_border_24) // Gambar bookmark terisi
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    @SuppressLint("DiscouragedApi", "SetTextI18n")
    private fun fillUserData(user: DetailUserResponse) {
        tvNama.text = user.name
        tvUsername.text = "("+user.login+")"
        tvFollowers.text = user.followers.toString()
        tvFollowing.text = user.following.toString()

        // Menggunakan Glide untuk memuat gambar avatar dari URL
        if (!user.avatarUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(user.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // Animasi transisi
                .into(avatarImageView2)
        } else {
            // Jika URL avatar kosong, Anda dapat menampilkan gambar default atau menangani kasus ini sesuai kebutuhan Anda
            avatarImageView2.setImageResource(R.drawable.ic_launcher_background)
        }
    }


    private fun showProgressBar() {
        progressBar2.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar2.visibility = View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}