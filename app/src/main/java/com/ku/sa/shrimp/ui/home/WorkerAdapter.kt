package com.ku.sa.shrimp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.FarmManager
import com.ku.sa.shrimp.data.User
import kotlinx.android.synthetic.main.item_farm.view.*
import java.util.*
import kotlin.collections.ArrayList

class WorkerAdapter(val farmManager: FarmManager) : RecyclerView.Adapter<WorkerAdapter.ViewHolder>()
{
    // counting algorithm count shrimps by bit 00

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {


        holder.apply {

        }
    }

    override fun getItemCount(): Int = 4

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_farm, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    }
}

