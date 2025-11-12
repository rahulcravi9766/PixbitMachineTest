package com.learning.pixbitmachinetest.presentation.screens.signInSignUp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.pixbitmachinetest.common.utils.Constants
import com.learning.pixbitmachinetest.data.remote.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInSIgnUpViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _registrationEvent = MutableSharedFlow<RegistrationEvent>()
    val registrationEvent = _registrationEvent.asSharedFlow()

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            try {
                val response = repository.registerUser(name, email, password, confirmPassword)
                if (response.isSuccessful && response.body() != null) {
                    val accessToken = response.body()!!.accessToken
                    dataStore.edit {
                        it[Constants.AUTH_TOKEN] = accessToken
                    }
                    _registrationEvent.emit(RegistrationEvent.Success)
                    _uiState.value = UiState(isLoading = false)
                } else {
                    val errorMessage = response.message() ?: "Something went wrong"
                    _registrationEvent.emit(RegistrationEvent.Error(errorMessage))
                    _uiState.value = UiState(isLoading = false)
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Something went wrong"
                _registrationEvent.emit(RegistrationEvent.Error(errorMessage))
                _uiState.value = UiState(isLoading = false)
            }
        }
    }


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            try {
                val response = repository.loginUser(email, password)
                if (response.isSuccessful && response.body() != null) {
                    val accessToken = response.body()!!.accessToken
                    dataStore.edit {
                        it[Constants.AUTH_TOKEN] = accessToken
                    }
                    _loginEvent.emit(LoginEvent.Success)
                    _uiState.value = UiState(isLoading = false)
                } else {
                    val errorMessage = response.message() ?: "Something went wrong"
                    _loginEvent.emit(LoginEvent.Error(errorMessage))
                    _uiState.value = UiState(isLoading = false)
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Something went wrong"
                _loginEvent.emit(LoginEvent.Error(errorMessage))
                _uiState.value = UiState(isLoading = false)
            }
        }

    }
}

data class UiState(
    val isLoading: Boolean = false
)

sealed class RegistrationEvent {
    object Success : RegistrationEvent()
    data class Error(val message: String) : RegistrationEvent()
}

sealed class LoginEvent {
    object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}