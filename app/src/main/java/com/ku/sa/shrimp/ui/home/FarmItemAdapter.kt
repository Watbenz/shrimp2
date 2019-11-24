package com.ku.sa.shrimp.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.Shrimp
import com.ku.sa.shrimp.data.Util
import com.ku.sa.shrimp.data.model.Pond
import kotlinx.android.synthetic.main.item_farm.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FarmItemAdapter(
    private val shrimp: MutableLiveData<ArrayList<Shrimp>>,
    private val pond: ArrayList<Pond>,
    private val shrimpCode: HashMap<String, String>
) : RecyclerView.Adapter<FarmItemAdapter.ViewHolder>() {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {


        holder.apply {
            val date = Date(shrimp.value!![i].shrimpStart)
            val str = Util.DATE_FORMAT.format(date)
            getTextPondName.text = "บ่อที่ ${i + 1} ${
            shrimpCode[shrimp.value!![i].shrimpCode]}"
            getTextRelease.text = "ลงวันที่ ${str}"
            getTextAmount.text = "${shrimp.value!![i].shrimpAmount} ตัว"
        }
    }

    override fun getItemCount(): Int {
        Log.i("dataget", "Size: " + shrimp.value!!.size.toString())
        return shrimp.value!!.size
    }

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

