package com.learning.pixbitmachinetest.data.model

import com.squareup.moshi.Json

data class EmployeeListResponse(
    @Json(name = "data") val data: List<EmployeeListItem>,
    @Json(name = "links") val links: Links,
    @Json(name = "meta") val meta: Meta
)

data class EmployeeListItem(
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
    @Json(name = "contract_period") val contractPeriod: Int,
    @Json(name = "total_salary") val totalSalary: Int,
    @Json(name = "monthly_payments") val monthlyPayments: List<MonthlyPaymentData>,
    @Json(name = "created_at") val createdAt: String
)

data class MonthlyPaymentData(
    @Json(name = "id") val id: Int,
    @Json(name = "payment_date") val paymentDate: String,
    @Json(name = "amount") val amount: Int,
    @Json(name = "amount_percentage") val amountPercentage: Int,
    @Json(name = "remarks") val remarks: String,
    @Json(name = "created_at") val createdAt: String
)

data class Links(
    @Json(name = "first") val first: String,
    @Json(name = "last") val last: String,
    @Json(name = "prev") val prev: String?,
    @Json(name = "next") val next: String?
)

data class Meta(
    @Json(name = "current_page") val currentPage: Int,
    @Json(name = "from") val from: Int,
    @Json(name = "last_page") val lastPage: Int,
    @Json(name = "links") val links: List<MetaLink>,
    @Json(name = "path") val path: String,
    @Json(name = "per_page") val perPage: Int,
    @Json(name = "to") val to: Int,
    @Json(name = "total") val total: Int
)

data class MetaLink(
    @Json(name = "url") val url: String?,
    @Json(name = "label") val label: String,
    @Json(name = "active") val active: Boolean
)
