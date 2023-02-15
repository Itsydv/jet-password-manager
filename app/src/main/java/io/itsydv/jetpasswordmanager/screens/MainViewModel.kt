package io.itsydv.jetpasswordmanager.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.itsydv.jetpasswordmanager.model.Credential
import io.itsydv.jetpasswordmanager.repository.MyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MyRepository) : ViewModel() {

    private val _credentials = MutableStateFlow<List<Credential>>(emptyList())
    val credentials = _credentials.asStateFlow()

    init {
        getAllCredentials()
    }

    private fun getAllCredentials() = viewModelScope.launch {
        repository.getCredentials().distinctUntilChanged().collect {
            _credentials.value = it
        }
    }

    fun addCredentials(credential: Credential) =
        viewModelScope.launch { repository.addCredentials(credential) }

    suspend fun getCredential(id: String) = repository.getCredential(id)

    fun updateCredentials(credential: Credential) =
        viewModelScope.launch { repository.updateCredentials(credential) }

    fun deleteCredentials(credential: Credential) =
        viewModelScope.launch { repository.deleteCredential(credential) }
}