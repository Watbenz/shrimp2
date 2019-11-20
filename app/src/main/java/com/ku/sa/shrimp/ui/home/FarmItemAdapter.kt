package com.ku.sa.shrimp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.Shrimp
import kotlinx.android.synthetic.main.item_farm.view.*
import java.util.*
import kotlin.collections.ArrayList

class FarmItemAdapter(val ponds: ArrayList<String>, val shrimps: ArrayList<Shrimp>, val type: ArrayList<String>) : RecyclerView.Adapter<FarmItemAdapter.ViewHolder>()
{
    // counting algorithm count shrimps by bit 00

    private val __shrimpAtPond = ArrayList<Int>(Collections.nCopies(ponds.size, 0)).apply {
//        shrimps.forEach{
//            var point = 0
//            when (it.shrimpCode) {
//                1 -> point = 1
//                2 -> point = 2
//            }
//            val pos = it.p_id
//            this[pos] += point
//        }
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
//
//        var headText = "บ่อที่ $i"
//        var sType = __shrimpAtPond[i]
//
//        if (sType and 1 == 1) headText += " ${type[0]}"
//        sType = sType shl 1
//        if (sType and 1 == 1) headText += " ${type[1]}"



        holder.apply {
            getTextPondName.text = "VOLOLO"
//            getTextRelease.text =
        }
    }

    override fun getItemCount(): Int = ponds.size

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

