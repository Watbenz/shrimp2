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

class WorkerAdapter(val users: MutableLiveData<ArrayList<User>>, val pond: Pond) :
    RecyclerView.Adapter<WorkerAdapter.ViewHolder>() {

    val mRef = FirebaseDatabase.getInstance().getReference()
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        holder.apply {
            getWorkName.text = "${users.value!![i].fName} ${users.value!![i].lName}"
            Log.i("dialogjob", "user id: " + users.value!![i].user_id)
            Log.i("dialogjob", "curr id: " + Util.currentUser.user_id)
            getDialogLayout.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(it.context)
                // ...Irrelevant code for customizing the buttons and title

//                val inflater = it..getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                val inflater =
                    it.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val dialogView = inflater.inflate(R.layout.assignwork, null)
                dialogBuilder.setView(dialogView)
                val d = dialogBuilder.create()

                var c = Calendar.getInstance()
                val day = c.get(Calendar.DAY_OF_MONTH)
                val mth = c.get(Calendar.MONTH)
                val year = c.get(Calendar.YEAR)
//                override fun onCreate(savedInstanceState: Bundle?) {
                val work = arrayOf(
                    "ให้อาหารกุ้งรอบเช้า",
                    "ให้อาหารกุ้งรอบเย็น",
                    "เปิดเครื่องตีน้ำ",
                    "น้ำกุ้งลงบ่อ",
                    "หว่านปูน",
                    "เปลี่ยนถ่ายน้ำในบ่อ",
                    "เตรียมบ่อลงกุ้ง"
                )
                // Set the drop down view resource
                // Finally, data bind the spinner object with dapter
                val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
                val adapter = ArrayAdapter(it.context, android.R.layout.simple_spinner_item, work)
                var event = ""
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
                        // Display the selected item text on text view
                        val text_view = dialogView.findViewById<TextView>(R.id.text_view)
                        text_view.text =
                            "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                        event = parent.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

                val btn = dialogView.findViewById<Button>(R.id.btn)
                val datetext = dialogView.findViewById<TextView>(R.id.datetext)
                btn.setOnClickListener {
                    val dpd = DatePickerDialog(
                        it.context,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, day ->
                            // Display Selected date in textbox
                            datetext.setText("วันที่ " + day + " เดือน " + monthOfYear + " ปี " + year)
                        }, year, mth, day
                    )
                    dpd.show()
                }


                val btn2 = dialogView.findViewById<Button>(R.id.btn2)
                val datetext2 = dialogView.findViewById<TextView>(R.id.datetext2)
                btn2.setOnClickListener {

                    val timeSetListener =
                        TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                            c.set(Calendar.HOUR_OF_DAY, hour)
                            c.set(Calendar.MINUTE, minute)
                            val s = SimpleDateFormat("HH:mm").format(c.time)
                            datetext2.setText("เวลา " + s)
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
                confirm.setOnClickListener {
                    val user = users.value!![i]
                    val newJob = Job("", user.user_id, pond.pond_id, "", "", 0, false, Job.JUST_SEND)
                    Log.i("dialogjob", "newJob: $newJob")
                    d.dismiss()
                }

                d.show()
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