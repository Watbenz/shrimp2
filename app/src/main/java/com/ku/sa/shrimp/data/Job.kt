package com.ku.sa.shrimp.data

import java.util.*

data class Job(
    var id: Int = -1,
    var u_id: Int = -1,
    var p_id: Int = -1,
    var t_id: Int = -1,
    var time: String = "",
    var output: Boolean = false
)