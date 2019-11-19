package com.ku.sa.shrimp.data

import java.util.*

data class Shrimp(
    val p_id: Int,
    val shrimpCode: Int,
    val shrimpAmount: Int,
    val shrimpStart: Date
) {
    // auto count id
    private companion object {
        var shrimp_id: Int = 0
    }

    // +3 month since start time
    val shrimpEnd: Date = Calendar.getInstance().let {
        it.time = shrimpStart
        it.add(Calendar.MONTH, 3)
        it.time
    }
    val id: Int = shrimp_id

    init {
        shrimp_id++
    }

}
