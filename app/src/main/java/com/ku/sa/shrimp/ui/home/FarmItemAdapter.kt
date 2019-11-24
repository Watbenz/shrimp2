package com.ku.sa.shrimp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ku.sa.shrimp.R
import kotlinx.android.synthetic.main.item_farm.view.*

class FarmItemAdapter : RecyclerView.Adapter<FarmItemAdapter.ViewHolder>() {
    val mRef = FirebaseDatabase.getInstance().reference
    lateinit var query1: Query
    lateinit var listener: ValueEventListener



    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        // SELECT pid FROM ponds
        // WHERE pid == i
//        query1 = mRef.child("shrimps").orderByChild("pond_id").equalTo(i.toDouble())
//        query1.addValueEventListener(listener)
//
//        listener = object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {}
//            override fun onDataChange(p0: DataSnapshot) {
//                for (s in p0.children) {
//                    val shrimp = s.getValue()
//                }
//            }
//        }

        holder.apply {
            //            var shrimpType = ""
//            farmMan.ponds.value!![i].shrimpType.forEach {shrimpType += "$it "}
//            getTextPondName.text = "บ่อที่ ${farmMan.ponds.value!![i].id.toString()} $shrimpType"
//            getTextRelease.text = "ลงวันที่ ${farmMan.shrimps.value!![i].shrimpStart}"
//            getTextAmount.text = "${farmMan.shrimps.value!![i].shrimpAmount.toString()} ตัว"
        }
    }

    override fun getItemCount(): Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_farm, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val getTextPondName: TextView = view.textView_pond_name
        val getTextRelease: TextView = view.textView_shrimp_release
        val getTextAmount: TextView = view.textView_shrimp_amount
    }
}

