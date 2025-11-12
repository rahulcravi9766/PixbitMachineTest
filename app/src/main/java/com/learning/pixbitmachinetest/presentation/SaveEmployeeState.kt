package com.learning.pixbitmachinetest.presentation

sealed class SaveEmployeeState {
    object Loading : SaveEmployeeState()
    data class Success(val data: Any) : SaveEmployeeState()
    data class Error(val message: String) : SaveEmployeeState()
    object Empty : SaveEmployeeState()
}