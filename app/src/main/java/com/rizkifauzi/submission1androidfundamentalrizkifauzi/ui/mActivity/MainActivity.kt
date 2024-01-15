package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.mActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.R
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.GthubResponse
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.retrofit.ApiConfig
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.databinding.ActivityMainBinding
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc.MainViewModel
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc.UsersAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var rvReview: RecyclerView

    private var kueri: String? = null

    // Inisialisasi MainViewModel
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        kueri = intent.getStringExtra("kueri")

        binding.topBar.setOnMenuItemClickListener{ menuItem ->
            when (menuItem.itemId){
                R.id.btnSearch -> {
                    val iintent = Intent(this, SearchActivity::class.java)
                    startActivity(iintent)
                    true
                }
                else -> false
            }
        }

        supportActionBar?.hide()
        val layoutManager = LinearLayoutManager(this)
        rvReview = binding.rvReview
        rvReview.layoutManager = layoutManager

        // Inisialisasi adapter untuk RecyclerView
        adapter = UsersAdapter(this) // Memberikan konteks (context)
        rvReview.adapter = adapter

        progressBar = binding.progressBar

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Panggil fungsi untuk mengambil data dari API hanya jika data belum ada di ViewModel
        if (viewModel.getGithubUsersLiveData().value == null) {
            fetchGitHubUsers()
        }

        // Observasi LiveData dari ViewModel untuk menampilkan data saat ada perubahan
        viewModel.getGithubUsersLiveData().observe(this) { users ->
            adapter.submitList(users)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Anda yakin keluar aplikasi?")
            .setPositiveButton("Ya") { _, _ ->
                finishAffinity() // Menutup semua activity dalam tumpukan
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss() // Tutup dialog jika "Tidak" ditekan
            }
            .show()
    }

    private fun fetchGitHubUsers() {
        progressBar.visibility = View.VISIBLE // Tampilkan ProgressBar sebelum mengambil data

        val query = kueri ?: "Rizki" // Gunakan kueri jika tidak null, jika null, gunakan "Rizki"

        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : Callback<GthubResponse> {
            override fun onResponse(call: Call<GthubResponse>, response: Response<GthubResponse>) {
                progressBar.visibility = View.GONE // Sembunyikan ProgressBar setelah mendapatkan data

                if (response.isSuccessful) {
                    val githubResponse = response.body()
                    if (githubResponse != null) {
                        val users = githubResponse.items
                        viewModel.setGithubUsersData(users)
                    }
                } else {
                    // Tangani kesalahan di sini, misalnya koneksi gagal
                    Toast.makeText(this@MainActivity, "Koneksi Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GthubResponse>, t: Throwable) {
                progressBar.visibility = View.GONE // Sembunyikan ProgressBar jika ada kesalahan jaringan

                // Tangani kesalahan jaringan di sini
                Toast.makeText(this@MainActivity, "Koneksi Gagal", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

