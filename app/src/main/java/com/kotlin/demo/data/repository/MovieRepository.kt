package com.kotlin.demo.data.repository

import com.kotlin.demo.data.model.vo.MovieVO
import com.kotlin.demo.data.source.local.table.Movie
import io.reactivex.Single


/**
 * To make an interaction between [MovieRepositoryImp] & [GetMoviesUseCase]
 * */
interface MovieRepository {

    fun getMovies(defaultPage: Boolean): Single<List<MovieVO>>
    fun searchMoviesByName(name: String?, defaultPage: Boolean): Single<List<MovieVO>>
    fun deleteMovie(movie: Movie)
    fun addMovie(movie: Movie)
    fun isPaginated(): Boolean
    fun updateBookMark(id: Long, isBookMark: Boolean)
}