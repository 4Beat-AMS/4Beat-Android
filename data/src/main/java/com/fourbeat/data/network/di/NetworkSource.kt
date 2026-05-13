package com.fourbeat.data.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PublicNetwork

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrivateNetwork

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SpotifyNetwork
