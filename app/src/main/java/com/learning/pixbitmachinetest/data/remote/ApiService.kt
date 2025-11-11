package com.learning.pixbitmachinetest.data.remote

import com.learning.pixbitmachinetest.data.remote.model.RegistrationResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
     @Field("name") name: String,
     @Field("email") email: String,
     @Field("password") password: String,
     @Field("confirm_password") confirmPassword: String
    ): RegistrationResponse?
}