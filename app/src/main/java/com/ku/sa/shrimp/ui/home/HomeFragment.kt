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

        // TODO: add shrimp and pond by firebase
        val str = "31/12/1998"
        val date = Util.DATE_FORMAT.parse(str)!!
//
        val arrShrimp = ArrayList<Shrimp>().apply {
            add(Shrimp(1, 1, 2000, date))
            add(Shrimp(1, 2, 2000, date))
            add(Shrimp(2, 1, 2000, date))
            add(Shrimp(3, 2, 2000, date))
            add(Shrimp(4, 1, 2000, date))
            add(Shrimp(4, 2, 2000, date))
            add(Shrimp(5, 1, 2000, date))
        }
        val shrimpType = resources.getStringArray(R.array.shrimpType).toCollection(ArrayList())
        val arrPond = arrayListOf("A", "B", "C", "D", "E", "F", "G")
//        val arrPond = arrayListOf("A", "B")

        val recycler: RecyclerView = root.findViewById(R.id.recyclerView_farm)
        recycler.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = FarmItemAdapter(arrPond, arrShrimp, shrimpType)
        }


        return root
    }
}