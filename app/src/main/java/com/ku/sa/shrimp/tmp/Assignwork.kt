package com.ku.sa.shrimp


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.assignwork.*
import java.lang.Math.log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class Assignwork : AppCompatActivity() {

    var c = Calendar.getInstance()
    val d = c.get(Calendar.DAY_OF_MONTH)
    val m = c.get(Calendar.MONTH)
    val y = c.get(Calendar.YEAR)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assignwork)
        // Initializing a String Array
        val work = arrayOf("ให้อาหารกุ้งรอบเช้า","ให้อาหารกุ้งรอบเย็น","เปิดเครื่องตีน้ำ","น้ำกุ้งลงบ่อ","หว่านปูน","เปลี่ยนถ่ายน้ำในบ่อ","เตรียมบ่อลงกุ้ง")
        // Initializing an ArrayAdapter
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,work)
        // Set the drop down view resource
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        // Finally, data bind the spinner object with dapter
        spinner.adapter = adapter;
        // Set an on item selected listener for spinner object
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                text_view.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
            }
            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

        btn.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, day ->
                // Display Selected date in textbox
                datetext.setText("วันที่ " + day  + " เดือน " + monthOfYear  + " ปี " + year)}, y, m, d)
            dpd.show()
        }
        btn2.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                val s = SimpleDateFormat("HH:mm").format(c.time)
                datetext2.setText("เวลา " + s)
            }
            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }
    }

}
