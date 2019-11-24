package com.ku.sa.shrimp.ui.notifications

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.User
import kotlinx.android.synthetic.main.worker_display_item.view.*

class WorkerDisplayAdapter(private val arr: MutableLiveData<ArrayList<User>>, private val flag: Boolean) : RecyclerView.Adapter<WorkerDisplayAdapter.ViewHolder>()
{
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        Log.i("registery", "arrdata: " + arr.toString())

        holder.apply {
            getTextName.text = " ${arr.value!![i].fName} ${arr.value!![i].lName}"
            addWork.isVisible = flag
        }
    }

    override fun getItemCount(): Int {
        Log.i("registery", "size: " + arr.value!!.size)
        return arr.value!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.worker_display_item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val getTextName: TextView = view.worker_name
        val addWork = view.add_work_layout

    }
}