package com.ku.sa.shrimp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.FarmManager
import com.ku.sa.shrimp.data.Shrimp
import com.ku.sa.shrimp.data.User
import kotlinx.android.synthetic.main.item_farm.view.*

class FarmItemAdapter(val farmMan: FarmManager) : RecyclerView.Adapter<FarmItemAdapter.ViewHolder>()
{
    // counting algorithm count shrimps by bit 00
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        holder.apply {
            getTextPondName.text = "VOLOLO"
        }
    }

    override fun getItemCount(): Int = farmMan.ponds.value!!.size

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_farm, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val getTextPondName: TextView = view.textView_pond_name
//        val getTextRelease: TextView = view.textView_shrimp_release
//        val getTextAmount: TextView = view.textView_shrimp_amount
//        val getWorkerRecycler: RecyclerView = view.recycler_worker
    }
}

