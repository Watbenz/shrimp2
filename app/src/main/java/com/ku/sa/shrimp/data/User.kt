package com.ku.sa.shrimp.data

data class User(
    var user_id: String = "",
    var username: String = "",
    var password: String = "",
    var fName: String = "",
    var lName: String = "",
    var tel: String = "",
    var email: String = "",
    var type: Int = -1,
    var isLogin: Boolean = false
)