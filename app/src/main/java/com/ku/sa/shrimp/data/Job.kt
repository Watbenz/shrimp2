package com.ku.sa.shrimp.data

data class Job(
    var job_id: String = "",
    var user_id: String = "",
    var pond_id: String = "",
    var task_id: String = "",
    var description: String = "",
    var time: Long = -1,
    var output: Int = JUST_SEND
) {
    companion object {
        const val DONE = 2
        const val ACCEPT = 1
        const val JUST_SEND = 0
        const val AWAY = -1
    }
}