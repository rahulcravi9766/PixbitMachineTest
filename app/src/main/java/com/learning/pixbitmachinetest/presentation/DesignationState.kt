package com.learning.pixbitmachinetest.presentation

import com.learning.pixbitmachinetest.data.model.Designation

sealed class DesignationState {
    object Loading : DesignationState()
    data class Success(val data: List<Designation>) : DesignationState()
    data class Error(val message: String) : DesignationState()
    object Empty : DesignationState()
}