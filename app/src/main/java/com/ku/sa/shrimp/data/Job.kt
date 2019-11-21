package com.ku.sa.shrimp.data

import java.util.*

data class Job(
    var id: Int = -1,
    var user: Int = -1,
    var pond: Int = -1,
    var task: Int = -1,
    var time: String = "",
    var output: Boolean = false
)