package com.example.googletasksclone.di

import android.content.Context
import androidx.room.Room
import com.example.googletasksclone.data.source.CategoryRepository
import com.example.googletasksclone.data.source.local.CategoryDataSource
import com.example.googletasksclone.data.source.local.CategoryLocalDataSource
import com.example.googletasksclone.data.source.local.ToDoDatabase
import com.example.googletasksclone.data.source.DefaultCategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class LocalCategoryDataSource

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "Tasks.db"
        ).build()
    }

    @Singleton
    @LocalCategoryDataSource
    @Provides
    fun provideCategoryLocalDatabase(
        database: ToDoDatabase,
        ioDispatcher: CoroutineDispatcher
    ): CategoryDataSource {
        return CategoryLocalDataSource(database.categoryDao(), ioDispatcher)
    }


    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
object CategoryRepositoryModule {
    @Singleton
    @Provides
    fun provideCategoryRepository(
        @AppModule.LocalCategoryDataSource localCategoryDataSource: CategoryDataSource,
        ioDispatcher: CoroutineDispatcher
    ): CategoryRepository {
        return DefaultCategoryRepository(localCategoryDataSource, ioDispatcher)
    }

}