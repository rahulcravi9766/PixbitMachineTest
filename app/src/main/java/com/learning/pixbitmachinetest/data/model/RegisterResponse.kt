package com.learning.pixbitmachinetest.data.model

import com.squareup.moshi.Json

data class RegisterResponse(
    @Json(name = "access_token")
    val accessToken: String
)
