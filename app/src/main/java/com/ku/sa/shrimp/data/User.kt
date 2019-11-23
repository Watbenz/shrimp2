package com.ku.sa.shrimp.data

data class User(
    var id: String = "",
    var username: String = "",
    var password: String = "",
    var fName: String = "",
    var lName: String = "",
    var tel: String = "",
    var type: Int = -1,
    var isLogin: Boolean = false
) {
//    // auto count id
//    private companion object {
//        var user_id: Int = 1
//    }


//    init {
//        user_id++
//    }
}