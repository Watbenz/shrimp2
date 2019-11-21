package com.ku.sa.shrimp.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.PondInfoActivity
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.FarmManager
import com.ku.sa.shrimp.data.Util
import com.ku.sa.shrimp.data.Shrimp
import com.ku.sa.shrimp.ui.RecyclerMenuClickListener
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recycler: RecyclerView = root.findViewById(R.id.recyclerView_farm)
        val farmMan = FarmManager(context!!.applicationContext)
        recycler.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = FarmItemAdapter(farmMan, context!!)
            it.addOnItemTouchListener(RecyclerMenuClickListener(context, it, object : RecyclerMenuClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val intent = Intent(activity, PondInfoActivity::class.java)
//                    intent.putExtra(value, )
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    // ignore
                }
            }
        ))}


        return root
    }
}