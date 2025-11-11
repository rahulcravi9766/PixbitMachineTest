package com.learning.pixbitmachinetest.data.remote.repository

import android.util.Log
import com.learning.pixbitmachinetest.data.remote.ApiService
import com.learning.pixbitmachinetest.data.remote.model.RegistrationResponse
import com.learning.pixbitmachinetest.utils.Resource
import okhttp3.ResponseBody
import javax.inject.Inject


class Repository @Inject constructor(val api: ApiService) {

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): RegistrationResponse? {

        return api.registerUser(name, email, password, confirmPassword)

    }
}