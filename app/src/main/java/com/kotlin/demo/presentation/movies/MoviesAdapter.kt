package com.kotlin.demo.presentation.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.demo.R
import com.kotlin.demo.data.model.vo.MovieVO
import com.kotlin.demo.databinding.HolderMoviesBinding
import com.kotlin.demo.loadImage
import com.kotlin.demo.presentation.movies.MoviesAdapter.MovieViewHolder
import java.util.*

/**
 * [RecyclerView.Adapter] to adapt
 * [MovieVO] items into [RecyclerView] via [MovieViewHolder]
 *
 *
 *
 */
internal class MoviesAdapter(val mListener: OnMoviesAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = MoviesAdapter::class.java.name
    private val movies: MutableList<MovieVO> = ArrayList()
    private val POSTER_API = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderMovieBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_movies, parent, false
        )
        return MovieViewHolder(holderMovieBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): MovieVO {
        return movies[position]
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addData(list: List<MovieVO>) {
        val prvSize: Int = this.movies.size;
        this.movies.addAll(list)
        val currSize: Int = this.movies.size
        notifyItemRangeInserted(prvSize, currSize);
    }

    fun loadData(list: List<MovieVO>) {
        this.movies.clear()
        this.movies.addAll(list)
        notifyDataSetChanged()
    }


    inner class MovieViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {


        fun onBind(movie: MovieVO) {
            val holderMovieBinding = dataBinding as HolderMoviesBinding
            holderMovieBinding.movieViewModel = MovieViewModel(movie)
            holderMovieBinding.movieProgressBar.visibility = View.VISIBLE
            holderMovieBinding.ivBookmark.setImageResource(if (movie.bookMark) R.drawable.ic_star_full_vector else R.drawable.ic_star_empty_white_vector)

            movie.url?.let {
                holderMovieBinding.movieImageView.loadImage(
                    POSTER_API + movie.url,
                    holderMovieBinding.movieProgressBar
                )
            }
            holderMovieBinding.ivBookmark.setOnClickListener {
                 mListener.updateBookMarkedStatus(movie.id,!movie.bookMark)
                holderMovieBinding.ivBookmark.setImageResource(if (!movie.bookMark) R.drawable.ic_star_full_vector else R.drawable.ic_star_empty_white_vector)

            }

        }
    }

}
