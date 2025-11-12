package com.learning.pixbitmachinetest.data.remote.repository

import com.learning.pixbitmachinetest.data.model.Designation
import com.learning.pixbitmachinetest.data.model.RegisterResponse
import com.learning.pixbitmachinetest.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(val api: ApiService) {

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Response<RegisterResponse> {

        return api.registerUser(name, email, password, confirmPassword)

    }

    suspend fun loginUser(
        email: String,
        password: String
    ): Response<RegisterResponse> {

        return api.loginUser(email, password)

    }

    suspend fun saveEmployee(
        firstName: RequestBody,
        lastName: RequestBody,
        dob: RequestBody,
        designation: RequestBody,
        gender: RequestBody,
        mobile: RequestBody,
        email: RequestBody,
        address: RequestBody,
        contractPeriod: RequestBody,
        totalSalary: RequestBody,
        profilePic: MultipartBody.Part?,
        resume: MultipartBody.Part?,
        monthlyPayments: List<MultipartBody.Part>
    ): Response<Any> {
        return api.saveEmployee(
            firstName,
            lastName,
            dob,
            designation,
            gender,
            mobile,
            email,
            address,
            contractPeriod,
            totalSalary,
            profilePic,
            resume,
            monthlyPayments
        )
    }

    suspend fun getDesignationList(): Response<List<Designation>>{
        return api.getDesignationsList()
    }
}