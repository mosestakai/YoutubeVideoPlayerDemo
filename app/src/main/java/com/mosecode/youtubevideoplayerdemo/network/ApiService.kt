package com.mosecode.youtubevideoplayerdemo.network

import com.mosecode.youtubevideoplayerdemo.BuildConfig.YOUTUBE_API_KEY
import com.mosecode.youtubevideoplayerdemo.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/")
    fun search(
        @Query("q") searchString: String,
        @Query("key") apiKey: String = YOUTUBE_API_KEY
    ) : Call<SearchResponse>
}