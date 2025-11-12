package com.learning.pixbitmachinetest.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.pixbitmachinetest.data.remote.repository.Repository
import com.learning.pixbitmachinetest.presentation.state.EmployeeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _employeeListState = MutableStateFlow<EmployeeListState>(EmployeeListState.Empty)
    val employeeListState: StateFlow<EmployeeListState> = _employeeListState

    init {
        getEmployees()
    }

    private fun getEmployees() {
        viewModelScope.launch {
            _employeeListState.value = EmployeeListState.Loading
            try {
                val response = repository.getEmployees()
                if (response.isSuccessful) {
                    _employeeListState.value = EmployeeListState.Success(response.body()!!)
                } else {
                    _employeeListState.value = EmployeeListState.Error("An error occurred")
                }
            } catch (e: Exception) {
                _employeeListState.value = EmployeeListState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}