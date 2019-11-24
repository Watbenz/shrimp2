package com.ku.sa.shrimp.data

data class Job(
    var job_id: String = "",
    var user_id: String = "",
    var pond_id: String = "",
    var task_id: String = "",
    var description: String = "",
    var time: Long = -1,
    var output: Boolean = false,
    var accept: Int = JUST_SEND
) {
    companion object {
        val ACCEPT = 1
        val DENY = -1
        val JUST_SEND = 0
    }
}