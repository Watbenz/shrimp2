package com.ku.sa.shrimp.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ku.sa.shrimp.PondInfoActivity
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.ui.RecyclerMenuClickListener


class HomeFragment : Fragment() {

    private val mRef = FirebaseDatabase.getInstance().getReference("shrimp_code")
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var query: Query
    private lateinit var listener: ValueEventListener

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
//        val farmMan = FarmManager(context!!.applicationContext)
        recycler.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = FarmItemAdapter()
            it.addOnItemTouchListener(
                RecyclerMenuClickListener(
                    context,
                    it,
                    object : RecyclerMenuClickListener.OnItemClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            val intent = Intent(activity, PondInfoActivity::class.java)
                            intent.putExtra("position", position)
                            startActivity(intent)

                            Log.i("FirebaseDB", "item")
                        }

                        override fun onLongItemClick(view: View?, position: Int) {
                            // ignore
                        }
                    }
                ))
        }


        val button = root.findViewById<Button>(R.id.button5)
        button.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            // ...Irrelevant code for customizing the buttons and title

            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.pond_crate_dialog, null)
            dialogBuilder.setView(dialogView)

            // Load database
            listener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    val work = ArrayList<String>()
                    for (s in p0.children) {
                        work.add(s.child("name").getValue(String::class.java)!!)
                    }

                    val adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, work)
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                    val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(p0: AdapterView<*>?) {}
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
                    }


                }


            }
            mRef.addValueEventListener(listener)



            dialogBuilder.create().show()
        }


        return root
    }
}