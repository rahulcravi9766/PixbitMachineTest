package com.learning.pixbitmachinetest.presentation.screens.addEmployee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.pixbitmachinetest.data.model.Designation
import com.learning.pixbitmachinetest.data.remote.repository.Repository
import com.learning.pixbitmachinetest.model.MonthlyPayment
import com.learning.pixbitmachinetest.presentation.DesignationState
import com.learning.pixbitmachinetest.presentation.SaveEmployeeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _saveEmployeeState = MutableStateFlow<SaveEmployeeState>(SaveEmployeeState.Empty)
    val saveEmployeeState: StateFlow<SaveEmployeeState> = _saveEmployeeState

    private val _designationState = MutableStateFlow<DesignationState>(DesignationState.Empty)
    val designationState: StateFlow<DesignationState> = _designationState

    init {
        getDesignations()
    }

    private fun getDesignations() {
        viewModelScope.launch {
            _designationState.value = DesignationState.Loading
            try {
                val response = repository.getDesignationList()
                if (response.isSuccessful) {
                    _designationState.value = DesignationState.Success(response.body() ?: emptyList())
                } else {
                    _designationState.value = DesignationState.Error("An error occurred")
                }
            } catch (e: Exception) {
                _designationState.value = DesignationState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }


    fun saveEmployee(
        firstName: String,
        lastName: String,
        dob: String,
        designation: String,
        gender: String,
        mobile: String,
        email: String,
        address: String,
        contractPeriod: String,
        totalSalary: String,
        profilePic: File?,
        resume: File?,
        monthlyPayments: List<MonthlyPayment>
    ) {
        viewModelScope.launch {
            _saveEmployeeState.value = SaveEmployeeState.Loading
            try {
                val response = repository.saveEmployee(
                    firstName = firstName.toRequestBody("text/plain".toMediaTypeOrNull()),
                    lastName = lastName.toRequestBody("text/plain".toMediaTypeOrNull()),
                    dob = dob.toRequestBody("text/plain".toMediaTypeOrNull()),
                    designation = designation.toRequestBody("text/plain".toMediaTypeOrNull()),
                    gender = gender.toRequestBody("text/plain".toMediaTypeOrNull()),
                    mobile = mobile.toRequestBody("text/plain".toMediaTypeOrNull()),
                    email = email.toRequestBody("text/plain".toMediaTypeOrNull()),
                    address = address.toRequestBody("text/plain".toMediaTypeOrNull()),
                    contractPeriod = contractPeriod.toRequestBody("text/plain".toMediaTypeOrNull()),
                    totalSalary = totalSalary.toRequestBody("text/plain".toMediaTypeOrNull()),
                    profilePic = profilePic?.let {
                        MultipartBody.Part.createFormData(
                            "profile_picture",
                            it.name,
                            it.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                    },
                    resume = resume?.let {
                        MultipartBody.Part.createFormData(
                            "resume",
                            it.name,
                            it.asRequestBody("*/*".toMediaTypeOrNull())
                        )
                    },
                    monthlyPayments = monthlyPayments.flatMapIndexed { index, payment ->
                        listOf(
                            MultipartBody.Part.createFormData("monthly_payments[$index][date]", payment.date),
                            MultipartBody.Part.createFormData("monthly_payments[$index][amount]", payment.amount),
                            MultipartBody.Part.createFormData("monthly_payments[$index][percentage]", payment.percentage),
                            MultipartBody.Part.createFormData("monthly_payments[$index][remark]", payment.remark)
                        )
                    }
                )

                if (response.isSuccessful) {
                    _saveEmployeeState.value = SaveEmployeeState.Success(response.body() ?: "Success")
                } else {
                    _saveEmployeeState.value = SaveEmployeeState.Error("An error occurred")
                }
            } catch (e: Exception) {
                _saveEmployeeState.value = SaveEmployeeState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
