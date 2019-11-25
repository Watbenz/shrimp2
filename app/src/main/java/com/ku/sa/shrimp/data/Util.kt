package com.ku.sa.shrimp.data

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import kotlin.math.abs

class Util {
    companion object {
        @SuppressLint("SimpleDateFormat")
        val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy")
        val DATE_TIME_FORMAT = SimpleDateFormat("dd/MM/yyyy HH:mm")
        var currentUser: User = User()

        fun convert(old: String) : String {
            val byteArray = old.toByteArray(Charsets.UTF_16)
            var output = ""
            byteArray.forEach {
                val num = 'A'.toInt() + (abs(it.toInt()) % 26)
                output +=  num.toChar()
            }
            if (output.length > 25) output = output.subSequence(0, 25).toString()
            return "$output@gmail.com"
        }
    }
}