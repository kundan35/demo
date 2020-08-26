package com.kotlin.demo.presentation.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.kotlin.demo.data.model.vo.MovieVO

class MovieViewModel(movie: MovieVO) : ViewModel() {

    private val TAG = MovieViewModel::class.java.name
    val movieData = MutableLiveData<MovieVO>()

    init {
        movieData.value = movie
    }

}