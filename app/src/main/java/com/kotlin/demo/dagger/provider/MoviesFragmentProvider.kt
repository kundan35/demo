package com.kotlin.demo.dagger.provider

import com.kotlin.demo.presentation.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MoviesFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideMoviesFragment(): MoviesFragment;
}