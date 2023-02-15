package io.itsydv.jetpasswordmanager.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.itsydv.jetpasswordmanager.data.MyDao
import io.itsydv.jetpasswordmanager.data.MyDatabase
import io.itsydv.jetpasswordmanager.repository.MyRepository
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class MyModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase
        = Room.databaseBuilder(context, MyDatabase::class.java, "credential_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDao(database: MyDatabase): MyDao = database.getDao()

    @Singleton
    @Provides
    fun provideRepository(myDao: MyDao): MyRepository = MyRepository(myDao)
}