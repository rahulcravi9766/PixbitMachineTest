package com.learning.pixbitmachinetest.data.remote

import com.learning.pixbitmachinetest.data.model.Designation
import com.learning.pixbitmachinetest.data.model.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<RegisterResponse>



    @Multipart
    @POST("employees")
    suspend fun saveEmployee(
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("date_of_birth") dob: RequestBody,
        @Part("designation") designation: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("mobile_number") mobile: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody,
        @Part("contract_period") contractPeriod: RequestBody,
        @Part("total_salary") totalSalary: RequestBody,
        @Part profilePic: MultipartBody.Part?,
        @Part resume: MultipartBody.Part?,
        @Part monthlyPayments: List<MultipartBody.Part>
    ): Response<Any>


    @GET("designations")
    suspend fun getDesignationsList(): Response<List<Designation>>
}