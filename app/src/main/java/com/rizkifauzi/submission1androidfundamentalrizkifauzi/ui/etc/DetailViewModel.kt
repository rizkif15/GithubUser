package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.DetailUserResponse
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.ItemsItem
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val userLiveData = MutableLiveData<DetailUserResponse>()

    fun getUserData(username: String): LiveData<DetailUserResponse> {
        val apiService = ApiConfig.getApiService()
        val call = apiService.getDetailUser(username)

        call.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                if (response.isSuccessful) {
                    userLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                //val errorMessage = "Terjadi kesalahan: ${t.message}"
            }
        })

        return userLiveData
    }

    private val followersLiveData = MutableLiveData<List<ItemsItem>>()
    private val followingLiveData = MutableLiveData<List<ItemsItem>>()

    // Fungsi untuk mengambil daftar followers
    fun getFollowers(username: String): LiveData<List<ItemsItem>> {
        val apiService = ApiConfig.getApiService()
        val call = apiService.getFollowers(username)

        call.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                if (response.isSuccessful) {
                    followersLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                // Tangani kesalahan di sini jika permintaan gagal
            }
        })

        return followersLiveData
    }

    // Fungsi untuk mengambil daftar following
    fun getFollowing(username: String): LiveData<List<ItemsItem>> {
        val apiService = ApiConfig.getApiService()
        val call = apiService.getFollowing(username)

        call.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                if (response.isSuccessful) {
                    followingLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                // Tangani kesalahan di sini jika permintaan gagal
            }
        })

        return followingLiveData
    }
}
