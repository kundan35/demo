package com.kotlin.demo.data.source.local.dao

import androidx.room.*
import com.kotlin.demo.data.source.local.table.Movie

/**
 * it provides access to [Movie] underlying database
 * */
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie): Long

    @Query("SELECT * FROM Movie")
    fun loadAll(): MutableList<Movie>

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM Movie where bookMark = :bookmarkStatus")
    fun deleteAll(bookmarkStatus: Boolean)

    @Query("SELECT * FROM Movie where id = :movieId")
    fun loadOneByMovieId(movieId: Long): Movie?

    @Query("SELECT * FROM Movie where title = :movieTitle")
    fun loadOneByMovieTitle(movieTitle: String): Movie?

    @Update
    fun update(movie: Movie)

}