package com.kotlin.demo.presentation.movies


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.demo.data.model.vo.MovieVO
import com.kotlin.demo.domain.GetMoviesUseCase
import javax.inject.Inject

/**A helper class for the UI controller that is responsible for
 * preparing data for the UI [MoviesFragment]
 *
 *
 * */
class MoviesViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {

    private val TAG = MoviesViewModel::class.java.simpleName
    val movieListReceivedLiveData = MutableLiveData<List<MovieVO>>()
    val isLoad = MutableLiveData<Boolean>()
    val isPaginated = MutableLiveData<Boolean>()

    init {
        isLoad.value = false
    }


    fun loadMovies(defaultPage: Boolean?) {
        if (defaultPage == null) return
        getMoviesUseCase.saveDefaultPage(defaultPage)
        getMoviesUseCase.execute(
            onSuccess = {
                isLoad.value = true
                movieListReceivedLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun searchMovies(name: String?, defaultPage: Boolean) {
        if (name == null) return
        getMoviesUseCase.saveSearchMovieName(name, defaultPage)
        getMoviesUseCase.execute(
            onSuccess = {
                isLoad.value = true
                movieListReceivedLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun isPaginated() {
        isPaginated.value = getMoviesUseCase.isPaginated()
    }

    fun updateBookMarkStatus(id: Long, isBookMarked: Boolean) {
        getMoviesUseCase.updateBookMarkStatus(id, isBookMarked)
    }

}