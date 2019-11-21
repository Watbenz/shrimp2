package com.ku.sa.shrimp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.FarmManager
import com.ku.sa.shrimp.data.Util
import com.ku.sa.shrimp.data.Shrimp
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
        recycler.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = FarmItemAdapter(FarmManager(context!!.applicationContext))
        }


        return root
    }
}