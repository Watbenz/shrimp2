package com.ku.sa.shrimp.data.model

import com.ku.sa.shrimp.data.Util
import java.util.*
import kotlin.collections.ArrayList

data class Pond(
    var id: Int = 0,
    var shrimpAmount: Int = 0,
    var shrimpType: ArrayList<String> = ArrayList<String>(),
    var dateStart: String = Util.DATE_FORMAT.format(Calendar.getInstance().time)
)