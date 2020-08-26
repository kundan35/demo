package com.kotlin.demo.data.repository

import android.content.SharedPreferences
import com.kotlin.demo.data.RemoteConstants
import com.kotlin.demo.data.mapper.DBMovieMapper
import com.kotlin.demo.data.model.response.MovieResponse
import com.kotlin.demo.data.model.vo.MovieVO
import com.kotlin.demo.data.source.local.AppDatabase
import com.kotlin.demo.data.source.local.dao.MovieDao
import com.kotlin.demo.data.source.local.table.Movie
import com.kotlin.demo.data.source.remote.RetrofitService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This repository is responsible for
 * fetching data [MovieVO] from server or db
 * */
class MovieRepositoryImp(
    private val sharedPreference: SharedPreferences,
    private val database: AppDatabase,
    private val retrofitService: RetrofitService
) : MovieRepository {


    override fun getMovies(defaultPage: Boolean): Single<List<MovieVO>> {
        var page = 1;
        if (!defaultPage) {
            page = getNextPage()
        }
        val singleListMovie: Single<MovieResponse> =
            retrofitService.getMovies(RemoteConstants.API_KEY, page)
        return execute(defaultPage, singleListMovie)

    }

    override fun searchMoviesByName(name: String?, defaultPage: Boolean): Single<List<MovieVO>> {
        var page = 1;
        if (!defaultPage) {
            page = getNextPage()
        }
        val singleListMovie: Single<MovieResponse> =
            retrofitService.getMoviesBySearch(RemoteConstants.API_KEY, name, page)
        return execute(false, singleListMovie)
    }

    private fun execute(
        defaultPage: Boolean,
        singleMovieResponse: Single<MovieResponse>
    ): Single<List<MovieVO>> {
        return singleMovieResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess() { movieResponseList ->
                run {
                    insertMovie(movieResponseList)
                }

            }.flatMap { getMoviesFromLocalSource(defaultPage) }
    }

    private fun insertMovie(
        movieResponse: MovieResponse
    ) {
        val iterator = movieResponse.results?.listIterator()
        val movieDao: MovieDao = database.movieDao
        movieDao.deleteAll(false)
        if (iterator != null) {
            for (item in iterator) {
                val movie: Movie = DBMovieMapper.from(item)
                val movieTable: Movie? = database.movieDao.loadOneByMovieId(movie.id)
                if (movieTable == null)
                    movieDao.insert(movie)
            }
        }
        addMoviePageInfoToSharedPreference(movieResponse.page, movieResponse.totPages)
    }

    private fun addMoviePageInfoToSharedPreference(page: Int, totPage: Int) {
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putInt("page", page)
        editor.putInt("totPage", totPage)
        editor.apply();
    }

    private fun getMoviesFromLocalSource(defaultPage: Boolean): Single<List<MovieVO>> {

        val movie: MutableList<Movie> =
            database.movieDao.loadAll()
        val iterator = movie.listIterator()
        val movieList: MutableList<MovieVO> = mutableListOf()
        for (item in iterator) {
            val movieVO =
                MovieVO(
                    item.id,
                    item.title,
                    item.url,
                    item.bookMark
                )
            if (!(!defaultPage && movieVO.bookMark)) {
                movieList.add(movieVO)
            }
        }

        return Single.just(movieList)
    }

    override fun addMovie(movie: Movie) {
        database.movieDao.insert(movie)
    }

    override fun isPaginated(): Boolean {
        val page: Int = sharedPreference.getInt("page", 0)
        val totPage: Int = sharedPreference.getInt("totPage", 0)
        return totPage > page
    }

    private fun getNextPage(): Int {
        val page: Int = sharedPreference.getInt("page", 0)
        return page + 1
    }

    override fun deleteMovie(movie: Movie) {
        database.movieDao.delete(movie)
    }

    override fun updateBookMark(id: Long, isBookMark: Boolean) {
        val movie: Movie? = database.movieDao.loadOneByMovieId(id)
        if (movie != null) {
            movie.bookMark = true
            database.movieDao.update(movie)
        }

    }


}