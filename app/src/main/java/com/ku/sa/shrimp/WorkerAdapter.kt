package com.ku.sa.shrimp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.ku.sa.shrimp.data.Job
import com.ku.sa.shrimp.data.User
import com.ku.sa.shrimp.data.Util
import com.ku.sa.shrimp.data.model.Pond
import kotlinx.android.synthetic.main.assignwork.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class WorkerAdapter(
    private val users: MutableLiveData<ArrayList<User>>,
    private val pond: Pond,
    private val tasks: ArrayList<Pair<String, String>>
) :
    RecyclerView.Adapter<WorkerAdapter.ViewHolder>() {

    val mRef = FirebaseDatabase.getInstance().reference
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.also { h ->
            h.getWorkName.text = "${users.value!![i].fName} ${users.value!![i].lName}"
            h.getDialogLayout.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(it.context)
                // ...Irrelevant code for customizing the buttons and title

                val inflater =
                    it.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val dialogView = inflater.inflate(R.layout.assignwork, null)
                dialogBuilder.setView(dialogView)
                val dia = dialogBuilder.create()


                val work = ArrayList<String>()
                tasks.forEach { work.add(it.second) }

                // Set the drop down view resource
                // Finally, data bind the spinner object with dapter
                val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
                val adapter = ArrayAdapter(it.context, android.R.layout.simple_spinner_item, work)
                var task_id = ""
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                spinner.adapter = adapter

                // Set an on item selected listener for spinner object
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        task_id = tasks[position].first
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

                var c = Calendar.getInstance()
                val d = c.get(Calendar.DAY_OF_MONTH)
                val m = c.get(Calendar.MONTH)
                val y = c.get(Calendar.YEAR)
//                var h: Int = -1
//                var m: Int = -1
                val btn = dialogView.findViewById<Button>(R.id.btn)
                val datetext = dialogView.findViewById<TextView>(R.id.datetext)
                datetext.text = Util.DATE_FORMAT.format(Calendar.getInstance().time)
                btn.setOnClickListener {
                    val dpd = DatePickerDialog(
                        it.context,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, day ->
                            // Display Selected date in textbox
                            c.set(year, monthOfYear, day)
                            datetext.text = Util.DATE_FORMAT.format(c.time)
                        }, y, m, d
                    )
                    dpd.show()
                }

                val btn2 = dialogView.findViewById<Button>(R.id.btn2)
                val datetext2 = dialogView.findViewById<TextView>(R.id.datetext2)
                datetext2.text = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
                btn2.setOnClickListener {
                    val timeSetListener =
                        TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                            c.set(Calendar.HOUR_OF_DAY, hour)
                            c.set(Calendar.MINUTE, minute)
                            val s = SimpleDateFormat("HH:mm").format(c.time)
                            datetext2.text = "$s"
                        }
                    TimePickerDialog(
                        it.context,
                        timeSetListener,
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true
                    ).show()

                }

                // click confirm
                val confirm = dialogView.findViewById<Button>(R.id.confirm)
                val next3month = Calendar.getInstance()
                val desc = dialogView.findViewById<EditText>(R.id.description)
                next3month.add(Calendar.MONTH, 3)
                confirm.setOnClickListener { _ ->
                    if (c.time.time <= System.currentTimeMillis()) {
                        Toast.makeText(it.context, "วันที่และเวลาไม่สามรถน้อยกว่าเวลาปัจจุบันได้", Toast.LENGTH_LONG).show()
                    }
                    else if (c.time.time > next3month.time.time) {
                        Toast.makeText(it.context, "วันที่และเวลาไม่สามรถมากกว่า 3 เดือนได้", Toast.LENGTH_LONG).show()
                    }
                    else {
                        val user = users.value!![i]
                        val newJob = Job("", user.user_id, pond.pond_id, task_id, desc.text.toString().trim(), c.time.time, Job.JUST_SEND)

                        val key = mRef.child("jobs").push().key!!
                        newJob.job_id = key
                        mRef.child("jobs").child(newJob.job_id).setValue(newJob)

                        dia.dismiss()
                    }

                }

                dia.show()
            }


        }

    }

    override fun getItemCount(): Int = users.value!!.size

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.worker_display_item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        val getTextPondName: TextView = view.textView_pond_name
        val getWorkName: TextView = view.findViewById(R.id.worker_name)!!
        val getRecycler: RecyclerView = view.findViewById(R.id.display_task_recycler)
        val getDialogLayout: LinearLayout = view.findViewById(R.id.add_work_layout)
    }
}