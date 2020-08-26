package com.kotlin.demo.data.source.remote


import com.kotlin.demo.data.model.response.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("movie/now_playing")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int?
    ): Single<MovieResponse>

    @GET("search/movie")
    fun getMoviesBySearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String?,
        @Query("page") page: Int?
    ): Single<MovieResponse>
}