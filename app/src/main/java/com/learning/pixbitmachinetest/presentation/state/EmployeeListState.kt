package com.learning.pixbitmachinetest.presentation.state

import com.learning.pixbitmachinetest.data.model.EmployeeListResponse

sealed class EmployeeListState {
    object Empty : EmployeeListState()
    object Loading : EmployeeListState()
    data class Success(val data: EmployeeListResponse) : EmployeeListState()
    data class Error(val message: String) : EmployeeListState()
}