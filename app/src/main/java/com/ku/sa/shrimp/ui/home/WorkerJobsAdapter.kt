package com.ku.sa.shrimp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.Job
import com.ku.sa.shrimp.data.Util
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class WorkerJobsAdapter(
    val jobs: MutableLiveData<ArrayList<Job>>,
    val pondMap: HashMap<String, Int>,
    val taskName: HashMap<String, String>
) : RecyclerView.Adapter<WorkerJobsAdapter.ViewHolder>() {

    lateinit var context: Context
    val mRef = FirebaseDatabase.getInstance().reference

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        val job = jobs.value!![i]
        holder.apply {
            getPondName.text = "บ่อที่ ${pondMap[job.pond_id]}"
            getTaskName.text = "${taskName[job.task_id]}"
            getTimeText.text = Util.DATE_TIME_FORMAT.format(Date(job.time))

            val btn = getStatusBtn
            if (job.output == Job.JUST_SEND) {
                btn.visibility = View.VISIBLE
                val color = ContextCompat.getDrawable(context, R.drawable.ripple_check_button)!!
                btn.background = color
                btn.setTextColor(Color.parseColor("#FFFFFF"))
                btn.text = "รับงาน"

                btn.setOnClickListener {
                    // update database
                    mRef.child("jobs").child(job.job_id).child("output").setValue(Job.ACCEPT)
                    job.output = Job.ACCEPT
                    Toast.makeText(context, "รับงานสำเร็จ", Toast.LENGTH_LONG).show()
                }
            }
            if (job.output == Job.ACCEPT) {
                btn.visibility = View.VISIBLE
                val color = ContextCompat.getDrawable(context, R.drawable.ripple_check_button2)!!
                btn.background = color
                btn.setTextColor(Color.parseColor("#FFFFFF"))
                btn.text = "ทำสำเร็จ"

                btn.setOnClickListener {
                    // update database
                    mRef.child("jobs").child(job.job_id).child("output").setValue(Job.DONE)
                    job.output = Job.DONE
                    Toast.makeText(context, "ทำงานสำเร็จแล้ว", Toast.LENGTH_LONG).show()
                }
            }

            if (job.output == Job.DONE) {
                btn.visibility = View.VISIBLE
                btn.text = "   "
                val bg = ContextCompat.getDrawable(context, R.drawable.ic_check_circle_black_24dp)!!
//                DrawableCompat.setTint(bg, R.color.green_light)
                DrawableCompat.setTint(
                    DrawableCompat.wrap(bg),
                    ContextCompat.getColor(context, R.color.green_light)
                );
//                bg.setFil = context.resources.getColor(R.color.green_light)
                btn.background = bg
            }
        }
    }

    override fun getItemCount(): Int {
        Log.i("dialogjob", "${jobs.value!!.size}")
        return jobs.value!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_farm, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val getPondName = view.findViewById<TextView>(R.id.textView_pond_name)
        val getTaskName = view.findViewById<TextView>(R.id.textView_shrimp_release)
        val getTimeText = view.findViewById<TextView>(R.id.textView_shrimp_amount)
        val getStatusBtn = view.findViewById<Button>(R.id.status_button)
    }
}
