package com.ku.sa.shrimp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.data.User

class WorkerAdapter(val users: MutableLiveData<ArrayList<User>>) : RecyclerView.Adapter<WorkerAdapter.ViewHolder>() {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.apply {
            getWorkName.text = "${users.value!![i].fName} ${users.value!![i].lName}"
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
        val getWorkName = view.findViewById<TextView>(R.id.worker_name)

    }
}