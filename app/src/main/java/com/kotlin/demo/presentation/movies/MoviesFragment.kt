package com.kotlin.demo.presentation.movies


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.demo.R
import com.kotlin.demo.databinding.FragmentMoviesBinding
import com.kotlin.demo.presentation.OnMainActivityCallback
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MoviesFragment : DaggerFragment(), OnMoviesAdapterListener {

    private lateinit var fragmentMoviesBinding: FragmentMoviesBinding
    private var adapter: MoviesAdapter? = null
    private var mCallback: OnMainActivityCallback? = null
    private var mLayoutManager: GridLayoutManager? = null
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private val spanCount = 2
    private var queryParameter = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMainActivityCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnMainActivityCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MoviesAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMoviesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        fragmentMoviesBinding.moviesViewModel = viewModel
        mLayoutManager = GridLayoutManager(activity, spanCount);
        fragmentMoviesBinding.moviesRecyclerView.layoutManager = mLayoutManager
        fragmentMoviesBinding.moviesRecyclerView.adapter = adapter
        initViewModelObserver()
        initSearchListener()
        initPagination()
        viewModel.loadMovies(true)
        return fragmentMoviesBinding.root
    }

    private fun initSearchListener() {
        fragmentMoviesBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it != "") {
                        queryParameter = it;
                        viewModel.searchMovies(it, true)
                    } else {
                        viewModel.loadMovies(true)
                        queryParameter = ""
                    }
                }
                return true
            }
        }
        )
    }

    private fun initPagination() {
        mLayoutManager?.let {
            fragmentMoviesBinding.moviesRecyclerView.addOnScrollListener(object :
                PaginationScrollListener(it) {
                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

                override fun loadMoreItems() {
                    isLoading = true
                    viewModel.isPaginated()
                }
            })
        }
    }

    private fun initViewModelObserver() {
        viewModel.isLoad.observe(viewLifecycleOwner, Observer {
            it?.let { visibility ->
                fragmentMoviesBinding.moviesProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })

        viewModel.movieListReceivedLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (isLoading) {
                    adapter?.addData(it)
                    isLoading = false
                } else {
                    adapter?.loadData(it)
                }

            }
        })

        viewModel.isPaginated.observe(viewLifecycleOwner, Observer {
            it?.let { isPaginated ->
                if (isPaginated) {
                    if (queryParameter == "") {
                        viewModel.loadMovies(false)
                    } else {
                        viewModel.searchMovies(queryParameter, false)
                    }
                } else isLastPage = true

            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
        adapter = null
    }

    override fun updateBookMarkedStatus(id: Long, bookMarkStatus: Boolean) {
        viewModel.updateBookMarkStatus(id,bookMarkStatus)
    }

}