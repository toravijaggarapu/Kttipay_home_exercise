package com.kttipay.sample.di

import com.kttipay.sample.repository.MovieRepo
import com.kttipay.sample.repository.MovieRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun providesMovieRepo(movieRepoImpl: MovieRepoImpl): MovieRepo

}