package com.learning.pixbitmachinetest.presentation.state

import com.learning.pixbitmachinetest.data.model.EmployeeListItem

sealed class EmployeeProfileState {
    object Empty : EmployeeProfileState()
    object Loading : EmployeeProfileState()
    data class Success(val data: EmployeeListItem) : EmployeeProfileState()
    data class Error(val message: String) : EmployeeProfileState()
}