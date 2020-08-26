package com.kotlin.demo.presentation.movies


/**
 * To make an interaction between [MoviesAdapter] & [MoviesFragment]
 * */
interface OnMoviesAdapterListener {
    fun updateBookMarkedStatus(id: Long,bookMarkStatus: Boolean)
}