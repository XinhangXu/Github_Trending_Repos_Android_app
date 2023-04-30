package com.xinhangxu.githubtrending


import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Xinhang on 28/April/2023.
 */
interface RestService {

    @GET("search/repositories?sort=stars")
    fun getGithubTrendingRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Call<JsonObject>

    //JsonObject
    //JsonPrimitive


}


