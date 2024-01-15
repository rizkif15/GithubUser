package com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.retrofit

import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.DetailUserResponse
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.GthubResponse
import com.rizkifauzi.submission1androidfundamentalrizkifauzi.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<GthubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}