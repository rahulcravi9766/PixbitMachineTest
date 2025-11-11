package com.learning.pixbitmachinetest.di

import com.learning.pixbitmachinetest.data.remote.ApiService
import com.learning.pixbitmachinetest.data.remote.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }
}