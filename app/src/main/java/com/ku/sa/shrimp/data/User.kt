package com.ku.sa.shrimp.data

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("username") val username: String = "",
    @SerializedName("password") val password: String = "",
    @SerializedName("firstname") val fName: String = "",
    @SerializedName("lastname") val lName: String = "",
    @SerializedName("telephone") val tel: String = "",
    @SerializedName("type") val type: Int = -1,
    @SerializedName("login") var isLogin: Boolean = false
) {
//    // auto count id
//    private companion object {
//        var user_id: Int = 1
//    }


//    init {
//        user_id++
//    }
}