package com.fourbeat.data.datasource.di

import com.fourbeat.data.datasource.auth.AuthDataSource
import com.fourbeat.data.datasource.auth.AuthRemoteDataSource
import com.fourbeat.data.datasource.user.UserDataSource
import com.fourbeat.data.datasource.user.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun bindsAuthDataSource(impl: AuthRemoteDataSource): AuthDataSource

    @Binds
    fun bindsUserDataSource(impl: UserRemoteDataSource): UserDataSource
}
