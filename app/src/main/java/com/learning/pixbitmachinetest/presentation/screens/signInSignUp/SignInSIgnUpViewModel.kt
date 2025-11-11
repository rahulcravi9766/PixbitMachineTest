package com.learning.pixbitmachinetest.presentation.screens.signInSignUp

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.pixbitmachinetest.data.remote.model.RegistrationResponse
import com.learning.pixbitmachinetest.data.remote.repository.Repository
import com.learning.pixbitmachinetest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInSIgnUpViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState


    var _registrationResponse: MutableStateFlow<Resource<RegistrationResponse>?> =
        MutableStateFlow(null)
    val registrationResponse: StateFlow<Resource<RegistrationResponse>?> = _registrationResponse


    fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = repository.registerUser(name, email, password, confirmPassword)
                if (response?.success == true) {
                    _registrationResponse.value = Resource.Success(response)
                } else {
                    _registrationResponse.value =
                        Resource.Error(response?.message ?: "Something went wrong")

                }
            } catch (e: Exception) {
                _registrationResponse.value = Resource.Error(e.message ?: "Something went wrong")
            }finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}


data class UiState(
    val isLoading: Boolean = false
)