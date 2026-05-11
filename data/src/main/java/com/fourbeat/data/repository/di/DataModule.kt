package com.fourbeat.data.repository.di

import com.fourbeat.data.repository.AuthRepositoryImpl
import com.fourbeat.data.repository.PreferenceRepositoryImpl
import com.fourbeat.data.repository.UserRepositoryImpl
import com.fourbeat.domain.repository.AuthRepository
import com.fourbeat.domain.repository.PreferenceRepository
import com.fourbeat.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindsPreferenceRepository(impl: PreferenceRepositoryImpl): PreferenceRepository
}
