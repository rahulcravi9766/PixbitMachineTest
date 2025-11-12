package com.learning.pixbitmachinetest.common.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

fun String.toRB(): RequestBody =
    RequestBody.create("text/plain".toMediaTypeOrNull(), this)


fun File.toMultipart(key: String): MultipartBody.Part {
    val rb = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), this)
    return MultipartBody.Part.createFormData(key, this.name, rb)
}

