package io.itsydv.jetpasswordmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.itsydv.jetpasswordmanager.model.Credential
import io.itsydv.jetpasswordmanager.model.DateConvertor

@Database(entities = [Credential::class], version = 1, exportSchema = false)
@TypeConverters(DateConvertor::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getDao(): MyDao
}