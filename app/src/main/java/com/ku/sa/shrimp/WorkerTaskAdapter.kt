package com.ku.sa.shrimp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.data.Job
import com.ku.sa.shrimp.data.User
import com.ku.sa.shrimp.data.Util
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class WorkerTaskAdapter(
    val jobs: MutableLiveData<ArrayList<Job>>,
    val taskName: ArrayList<Pair<String, String>>,
    val user: User
) :
    RecyclerView.Adapter<WorkerTaskAdapter.ViewHolder>() {

    lateinit var context: Context

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val taskMap = HashMap<String, String>()
        taskName.forEach { taskMap[it.first] = it.second }

        holder.also {
            val job = jobs.value!![i]
            val date = Date(job.time)
            it.getDetailTextView.text = "- ${taskMap[job.task_id]}"
            it.getdateTimeTextView.text = " ${Util.DATE_TIME_FORMAT.format(date)}"

            val st = it.getStatus
            val stText = it.getStatusText
            var color = -1
            when (job.output) {
                Job.JUST_SEND -> {
                    color = ContextCompat.getColor(context, R.color.red_new)
                    st.setColorFilter(color)
                    stText.text = "(ใหม่)"
                    stText.setTextColor(color)
                }
                Job.ACCEPT -> {
                    color = ContextCompat.getColor(context, R.color.yellow_accept)
                    st.setColorFilter(color)
                    stText.text = "(รับทราบ)"
                    stText.setTextColor(color)
                }
                Job.DONE -> {
                    color = ContextCompat.getColor(context, R.color.green_success)
                    st.setColorFilter(color)
                    stText.text = "(สำเร็จ)"
                    stText.setTextColor(color)
                }
                Job.AWAY -> {
                    color = ContextCompat.getColor(context, R.color.grey_away)
                    st.setColorFilter(color)
                    stText.visibility = View.GONE
                }
            }


        }
    }

    override fun getItemCount(): Int {
        Log.i("dialogjob", "inner count: " + jobs.value!!.size.toString())
        return jobs.value!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.worker_task_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val getDetailTextView: TextView = view.findViewById(R.id.detail_textView)
        val getdateTimeTextView: TextView = view.findViewById(R.id.testView_worker_date_time)
        val getStatus: ImageView = view.findViewById(R.id.status)
        val getStatusText: TextView = view.findViewById(R.id.status_text)
    }
}