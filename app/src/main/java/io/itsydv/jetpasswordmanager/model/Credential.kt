package io.itsydv.jetpasswordmanager.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

@Keep
@Entity("credentials_table")
data class Credential(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val website: String,
    val username: String,
    val password: String,
    var favourite: Boolean = false,
    val dateCreated: Date = Date.from(Instant.now()),
    var lastEdit: Date = dateCreated
)

class DateConvertor {
    @TypeConverter
    fun fromDateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun fromTimestampToDate(time: Long): Date = Date(time)
}
