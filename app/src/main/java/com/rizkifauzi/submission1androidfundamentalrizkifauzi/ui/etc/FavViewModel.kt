package com.rizkifauzi.submission1androidfundamentalrizkifauzi.ui.etc

import androidx.lifecycle.ViewModel
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.local.entity.FavEntity
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.source.FavRepository

class FavViewModel(private val favRepo: FavRepository) : ViewModel() {

    fun getFavoritedUsers() = favRepo.getFavUsers()

    fun saveFavUser(news: FavEntity) {
        favRepo.setFavUser(news, true)
    }

    fun deleteFavUser(news: FavEntity) {
        favRepo.setFavUser(news, false)
    }
}