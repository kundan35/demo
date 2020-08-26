package com.kotlin.demo.dagger.builder

import androidx.lifecycle.ViewModel
import com.kotlin.demo.dagger.ViewModelKey
import com.kotlin.demo.presentation.movies.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun provideMoviesFragmentViewModel(moviesViewModel: MoviesViewModel): ViewModel
}