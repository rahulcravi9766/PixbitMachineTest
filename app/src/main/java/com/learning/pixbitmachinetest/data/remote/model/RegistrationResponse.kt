package com.learning.pixbitmachinetest.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)
