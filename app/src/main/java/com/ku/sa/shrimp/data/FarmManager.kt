package com.ku.sa.shrimp.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ku.sa.shrimp.R

class FarmManager(context: Context) {

    val sType = MutableLiveData<ArrayList<String>>()
    val uType = MutableLiveData<ArrayList<String>>()
    val taskNames = MutableLiveData<ArrayList<String>>()
    val ponds = MutableLiveData<ArrayList<String>>()
    val shrimps = MutableLiveData<ArrayList<String>>()

    init {
//        val str = "31/12/1998"
//        val date = Util.DATE_FORMAT.parse(str)!!
//
//        val shrimpType = Resources.getSystem().getResourceTypeName(R.array.shrimp_type).toCollection(ArrayList())
//        val shrimpType =
        context.resources.getStringArray(R.array.task_name).forEach {
            Log.i("FirebaseDB", it)
        }
//
//        val taskName = Resources.getSystem().getResourceTypeName(R.array.task_name).toCollection(ArrayList())
//        val userType = Resources.getSystem().getResourceTypeName(R.array.user_type).toCollection(ArrayList())
        val arrPond = arrayListOf("A", "B", "C", "D", "E", "F", "G")
        ponds.value = arrPond


//        val arrShrimp = ArrayList<Shrimp>().apply {
//            add(Shrimp(1, 1, 2000, date))
//            add(Shrimp(1, 2, 2000, date))
//            add(Shrimp(2, 1, 2000, date))
//            add(Shrimp(3, 2, 2000, date))
//            add(Shrimp(4, 1, 2000, date))
//            add(Shrimp(4, 2, 2000, date))
//            add(Shrimp(5, 1, 2000, date))
//        }
    }

}