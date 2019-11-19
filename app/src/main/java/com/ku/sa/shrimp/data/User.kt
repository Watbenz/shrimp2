package com.ku.sa.shrimp.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("firstname") val fName: String,
    @SerializedName("lastname") val lName: String,
    @SerializedName("telephone") val tel: String,
    @SerializedName("type") val type: Int,
    @SerializedName("login") var isLogin: Boolean
) {
    // auto count id
    private companion object {
        var user_id: Int = 0
    }

    @SerializedName("id") val _id: Int = user_id

    init {
        user_id++
    }
}