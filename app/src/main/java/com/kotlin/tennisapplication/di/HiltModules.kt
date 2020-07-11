package com.kotlin.mykotlinproj.di

import com.kotlin.tennisapplication.actions.PlayerActionGenerator
import com.kotlin.tennisapplication.actions.PlayerActionProcessor
import com.kotlin.tennisapplication.points.PointsProcessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class HiltModule {
    @Provides
    @Singleton
    fun providePlayerActionGenerator(): PlayerActionGenerator {
        return PlayerActionGenerator()
    }

    @Provides
    @Singleton
    fun providePlayerActionProcessor(): PlayerActionProcessor {
        return PlayerActionProcessor()
    }

    @Provides
    @Singleton
    fun providePointsProcessor(): PointsProcessor {
        return PointsProcessor()
    }
}