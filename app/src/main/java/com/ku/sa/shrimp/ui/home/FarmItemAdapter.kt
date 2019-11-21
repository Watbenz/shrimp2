package com.ku.sa.shrimp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.FarmManager
import kotlinx.android.synthetic.main.item_farm.view.*

class FarmItemAdapter(val farmMan: FarmManager, val context: Context) : RecyclerView.Adapter<FarmItemAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        holder.apply {
            var shrimpType = ""
            farmMan.ponds.value!![i].shrimpType.forEach {shrimpType += "$it "}
            getTextPondName.text = "บ่อที่ ${farmMan.ponds.value!![i].id.toString()} $shrimpType"
            getTextRelease.text = "ลงวันที่ ${farmMan.shrimps.value!![i].shrimpStart}"
            getTextAmount.text = "${farmMan.shrimps.value!![i].shrimpAmount.toString()} ตัว"
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
        val getTextRelease: TextView = view.textView_shrimp_release
        val getTextAmount: TextView = view.textView_shrimp_amount
    }
}

