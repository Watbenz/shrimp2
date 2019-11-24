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
            add(Job(1, 1, 1, 1))
            add(Job(2, 2, 1, 2))
            add(Job(3, 3, 2, 3))
            add(Job(4, 2, 3, 3))
            add(Job(5, 1, 4, 1))
            add(Job(6, 1, 5, 2))
        }

        // shrimp dummy
        shrimps.value = ArrayList<Shrimp>().apply {
            add(Shrimp(1, 1, 1, 2000))
            add(Shrimp(1, 1, 2, 2000))
            add(Shrimp(1, 2, 1, 2000))
            add(Shrimp(1, 3, 2, 2000))
            add(Shrimp(1, 4, 1, 2000))
            add(Shrimp(1, 4, 2, 2000))
            add(Shrimp(1, 5, 1, 2000))
        }

        // sea dummy
        ponds.value = ArrayList<Pond>().apply {
            add(Pond(1, 1000))
            add(Pond(2, 1000))
            add(Pond(3, 1000))
            add(Pond(4, 1000))
            add(Pond(5, 1000))
        }

        // user dummy
        users.value = ArrayList<User>().apply {
            User("", "Dif", "1234", "Parin", "Thong", "085", "", 1, false)
            User("", "ITAE", "4321", "BOSSO", "GENO", "085", "", 1, false)
            User("", "KOOKKIK", "1324", "CHRron", "Felel", "085", "", 1, false)
        }

        // linking shrimp and sea
        shrimps.value!!.forEach {
            val pos = it.p_id - 1
            val type = it.shrimpCode - 1
            val typeName = sType.value!![type]

            ponds.value!!.get(pos).shrimpType.add(typeName)
        }
    }

}