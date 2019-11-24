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
import com.ku.sa.shrimp.data.Util
import kotlinx.android.synthetic.main.worker_display_item.view.*

class WorkerProfileAdapter() : RecyclerView.Adapter<WorkerProfileAdapter.ViewHolder>()
{
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val curr = Util.currentUser
        holder.apply {
            fname.text = curr.fName
            lname.text = curr.lName
            uname.text = curr.username
            tel.text = curr.tel
            email.text = curr.email
        }
    }

    override fun getItemCount(): Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.worker_profile_fragment, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val fname: TextView = view.findViewById(R.id.fname_textView)
        val lname: TextView = view.findViewById(R.id.lname_textView)
        val uname: TextView = view.findViewById(R.id.user_textView)
        val tel: TextView = view.findViewById(R.id.tel_textView)
        val email: TextView = view.findViewById(R.id.email_textView)
    }
}