package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.ItemsItem

class MainViewModel : ViewModel() {
    // Variabel LiveData untuk menyimpan data pengguna GitHub
    private val githubUsersLiveData = MutableLiveData<List<ItemsItem>>()

    // Metode untuk mendapatkan LiveData yang dapat di-observe dari aktivitas
    fun getGithubUsersLiveData(): LiveData<List<ItemsItem>> {
        return githubUsersLiveData
    }

    // Metode untuk mengatur data dalam LiveData
    fun setGithubUsersData(users: List<ItemsItem>) {
        githubUsersLiveData.value = users
    }
}
