package com.parapaparam.chiphy.network

import com.parapaparam.chiphy.entity.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("/v1/gifs/search")
    fun search(@Query("q") condition: String,
               @Query("limit") limit: Int,
               @Query("offset") offset: Int) : Single<SearchResponse>
}