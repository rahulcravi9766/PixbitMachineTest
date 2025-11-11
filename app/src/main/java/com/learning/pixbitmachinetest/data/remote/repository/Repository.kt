package com.learning.pixbitmachinetest.data.remote.repository

import com.learning.pixbitmachinetest.data.model.RegisterResponse
import com.learning.pixbitmachinetest.data.remote.ApiService
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
}