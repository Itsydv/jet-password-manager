package io.itsydv.jetpasswordmanager.data

import androidx.room.*
import io.itsydv.jetpasswordmanager.model.Credential
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface MyDao {

    @Query("SELECT * FROM credentials_table")
    fun getAllCredentials(): Flow<List<Credential>>

    @Query("SELECT * FROM credentials_table WHERE id = :id")
    suspend fun getCredential(id: UUID): Credential

    @Insert
    suspend fun insertCredential(credential: Credential)

    @Update
    suspend fun updateCredential(credential: Credential)

    @Delete
    suspend fun deleteCredential(credential: Credential)

}