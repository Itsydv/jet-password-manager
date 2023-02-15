package io.itsydv.jetpasswordmanager.repository

import io.itsydv.jetpasswordmanager.data.MyDao
import io.itsydv.jetpasswordmanager.model.Credential
import java.util.UUID
import javax.inject.Inject

class MyRepository @Inject constructor(private val myDao: MyDao) {
    suspend fun addCredentials(credential: Credential) = myDao.insertCredential(credential)
    suspend fun getCredential(id: String) = myDao.getCredential(UUID.fromString(id))
    fun getCredentials() = myDao.getAllCredentials()
    suspend fun updateCredentials(credential: Credential) = myDao.updateCredential(credential)
    suspend fun deleteCredential(credential: Credential) = myDao.deleteCredential(credential)
}