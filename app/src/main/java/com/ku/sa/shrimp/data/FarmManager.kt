package com.ku.sa.shrimp.data

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.model.Pond

class FarmManager(context: Context) {

    val users = MutableLiveData<ArrayList<User>>()
    val sType = MutableLiveData<ArrayList<String>>()
    val uType = MutableLiveData<ArrayList<String>>()
    val taskNames = MutableLiveData<ArrayList<String>>()
    val ponds = MutableLiveData<ArrayList<Pond>>()
    val shrimps = MutableLiveData<ArrayList<Shrimp>>()
    val jobs = MutableLiveData<ArrayList<Job>>()

    init {
        // get shrimp type
        sType.value = ArrayList()
        context.resources.getStringArray(R.array.shrimp_type).forEach { sType.value!!.add(it) }

        // task name
        taskNames.value = ArrayList()
        context.resources.getStringArray(R.array.task_name).forEach { taskNames.value!!.add(it) }

        // user type
        uType.value = ArrayList()
        context.resources.getStringArray(R.array.user_type).forEach { uType.value!!.add(it) }

        // TODO: dummy sea get from firebase

        // job dummy
        jobs.value = ArrayList<Job>().apply {
            add(Job())
            add(Job())
            add(Job())
            add(Job())
            add(Job())
            add(Job())
        }

        // shrimp dummy
        shrimps.value = ArrayList<Shrimp>().apply {
            add(Shrimp())
            add(Shrimp())
            add(Shrimp())
            add(Shrimp())
            add(Shrimp())
            add(Shrimp())
            add(Shrimp())
        }

        // sea dummy
        ponds.value = ArrayList<Pond>().apply {
            add(Pond(description = "บ่อข้างบ้าน"))
            add(Pond(description = "บ่อหน้าหมู่บ้าน"))
            add(Pond(description = ""))
            add(Pond(description = ""))
            add(Pond(description = ""))
        }


    }

}