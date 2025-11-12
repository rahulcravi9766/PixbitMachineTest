package com.learning.pixbitmachinetest.data.model

import com.squareup.moshi.Json

data class SaveEmployeeResponse(
    @Json(name = "data") val data: EmployeeData
)

data class EmployeeData(
    @Json(name = "id") val id: Int,
    @Json(name = "user_id") val userId: Int,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "profile_image_url") val profileImageUrl: String,
    @Json(name = "resume") val resume: String,
    @Json(name = "date_of_birth") val dateOfBirth: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "email") val email: String,
    @Json(name = "designation") val designation: String,
    @Json(name = "mobile_number") val mobileNumber: String,
    @Json(name = "address") val address: String,
    @Json(name = "contract_period") val contractPeriod: String,
    @Json(name = "total_salary") val totalSalary: String,
    @Json(name = "created_at") val createdAt: String
)
