package com.ku.sa.shrimp.data

import java.util.*

data class Shrimp(
    val id: Int,
    val p_id: Int,
    val shrimpCode: Int,
    val shrimpAmount: Int,
    val shrimpStart: String = Util.DATE_FORMAT.format(Calendar.getInstance().time)
)