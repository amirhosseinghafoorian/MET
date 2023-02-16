package com.example.data.di

import com.example.data.datasource.detail.DetailRemoteDataSource
import com.example.data.datasource.detail.DetailRemoteDataSourceImpl
import com.example.data.datasource.search.SearchRemoteDataSource
import com.example.data.datasource.search.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindSearchRemoteDataSource(
        searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl
    ): SearchRemoteDataSource

    @Binds
    @Singleton
    fun bindDetailRemoteDataSource(
        detailRemoteDataSourceImpl: DetailRemoteDataSourceImpl
    ): DetailRemoteDataSource

}