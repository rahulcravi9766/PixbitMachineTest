package com.learning.pixbitmachinetest.presentation.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.pixbitmachinetest.data.remote.repository.Repository
import com.learning.pixbitmachinetest.presentation.state.EmployeeProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeProfileViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _employeeProfileState = MutableStateFlow<EmployeeProfileState>(EmployeeProfileState.Empty)
    val employeeProfileState: StateFlow<EmployeeProfileState> = _employeeProfileState

    init {
        savedStateHandle.get<Int>("employeeId")?.let {
            getEmployeeProfile(it)
        }
    }

    private fun getEmployeeProfile(employeeId: Int) {
        viewModelScope.launch {
            _employeeProfileState.value = EmployeeProfileState.Loading
            try {
                val response = repository.getEmployees()
                if (response.isSuccessful) {
                    val employee = response.body()?.data?.find { it.id == employeeId }
                    if (employee != null) {
                        _employeeProfileState.value = EmployeeProfileState.Success(employee)
                    } else {
                        _employeeProfileState.value = EmployeeProfileState.Error("Employee not found")
                    }
                } else {
                    _employeeProfileState.value = EmployeeProfileState.Error("An error occurred")
                }
            } catch (e: Exception) {
                _employeeProfileState.value = EmployeeProfileState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}