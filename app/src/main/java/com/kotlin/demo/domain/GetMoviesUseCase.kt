package com.kotlin.demo.domain

import com.kotlin.demo.data.model.vo.MovieVO
import com.kotlin.demo.data.repository.MovieRepository
import com.kotlin.demo.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [MoviesViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) :
    SingleUseCase<List<MovieVO>>() {

    private var defaultPage: Boolean = true
    private var searchMovieName: String? = null
    private var isSearchQuery: Boolean = false;

    fun saveDefaultPage(pageDefault: Boolean) {
        defaultPage = pageDefault;
        isSearchQuery = false
    }

    fun saveSearchMovieName(name: String, pageDefault: Boolean) {
        searchMovieName = name
        isSearchQuery = true
        defaultPage = pageDefault
    }

    override fun buildUseCaseSingle(): Single<List<MovieVO>> {
        return if (isSearchQuery) {
            repository.searchMoviesByName(searchMovieName, defaultPage)
        } else {
            repository.getMovies(defaultPage)
        }
    }

    fun isPaginated(): Boolean {
        return repository.isPaginated()
    }

    fun updateBookMarkStatus(id: Long, isBookMarked: Boolean) {
         repository.updateBookMark(id,isBookMarked)
    }


}