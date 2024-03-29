package com.ku.sa.shrimp.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.gson.Gson
import com.ku.sa.shrimp.PondInfoActivity
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.Job
import com.ku.sa.shrimp.data.Shrimp
import com.ku.sa.shrimp.data.Util
import com.ku.sa.shrimp.data.model.Pond
import com.ku.sa.shrimp.ui.RecyclerMenuClickListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class HomeFragment : Fragment() {

    private val mRef = FirebaseDatabase.getInstance().reference
    private lateinit var query1: Query
    private lateinit var query2: Query
    private lateinit var query3: Query
    private lateinit var query4: Query
    private lateinit var listener: ValueEventListener
    private lateinit var listener1: ValueEventListener
    private lateinit var listener2: ValueEventListener
    private lateinit var listener3: ValueEventListener
    private lateinit var listener4: ValueEventListener

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // wait for data
        when (Util.currentUser.type) {
            -1 -> {
                Toast.makeText(
                    context,
                    "กำลังรอข้อมูลจากฐานข้อมูล กรุณากดใหม่อีกครั้ง",
                    Toast.LENGTH_LONG
                ).show()
            }
            0 -> {
                setViewForAdmin(root)
            }
            1 -> {
                setViewForWorker(root)
            }
        }

        return root
    }

    private fun setViewForWorker(root: View) {
        val recycler: RecyclerView = root.findViewById(R.id.recyclerView_farm)
        val user = Util.currentUser

        val jobs = MutableLiveData<ArrayList<Job>>()
        val tmp = ArrayList<Job>()
        jobs.value = ArrayList()

        mRef.child("jobs")
            .orderByChild("user_id")
            .equalTo(user.user_id)
            .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                tmp.clear()
                p0.children.forEach { tmp.add(it.getValue(Job::class.java)!!) }
                // sort jobs by time -> lastest first
                tmp.sortWith(Comparator { a, b -> b.time.compareTo(a.time) })
                jobs.value = tmp
            }
        })

        val pondMap = HashMap<String, Int>()
        mRef.child("ponds").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                pondMap.clear()
                p0.children.forEachIndexed { i, it ->
                    pondMap[it.key!!] = i + 1
                }
            }
        })

        val taskName = HashMap<String, String>()
        mRef.child("task_name").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                taskName.clear()
                p0.children.forEachIndexed { i, it ->
                    taskName[it.key!!] = it.child("name").getValue(String::class.java)!!
                }
            }
        })

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = WorkerJobsAdapter(jobs, pondMap, taskName)

        jobs.observe(this, Observer {
            recycler.adapter!!.notifyDataSetChanged()
        })
    }

    private fun setViewForAdmin(root: View) {
        val ponds = ArrayList<Pond>()
        val shrimps = MutableLiveData<ArrayList<Shrimp>>()
        val tmp = ArrayList<Shrimp>()
        val code = HashMap<String, String>()
        val recycler: RecyclerView = root.findViewById(R.id.recyclerView_farm)
        val addPond = root.findViewById<Button>(R.id.button5)

        shrimps.value = ArrayList()
        addPond.isVisible = true


        mRef.child("shrimp_code").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach { s1 ->
                    //                    s1.children.forEach { s2 ->
                    val key = s1.key!!
                    code[s1.key!!] = s1.child("name").getValue(String::class.java)!!
                    Log.i("dataget", "shrimpCode: ${key}")

                }
            }
        })

        val listener3 = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                for (s in p0.children) {
                    val s = s.getValue(Shrimp::class.java)!!
                    tmp.add(s)
                    Log.i("dataget", "shrimp: " + s.toString())
                }
                shrimps.value = tmp
            }
        }

        val listener2 = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                ponds.clear()
                tmp.clear()
                for (s in p0.children) {
                    val p = s.getValue(Pond::class.java)!!
                    Log.i("dataget", "pond: " + p.toString())
                    ponds.add(p)
                    query3 = mRef.child("shrimps").orderByChild("pond_id").equalTo(p.pond_id)
                    query3.addValueEventListener(listener3)

                }
            }
        }

        query2 = mRef.child("ponds")
        query2.addValueEventListener(listener2)

        recycler.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = FarmItemAdapter(shrimps, ponds, code)

            shrimps.observe(this, Observer {
                recycler.adapter!!.notifyDataSetChanged()
            })

            it.addOnItemTouchListener(
                RecyclerMenuClickListener(
                    context,
                    it,
                    object : RecyclerMenuClickListener.OnItemClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            val intent = Intent(activity, PondInfoActivity::class.java)
                            intent.putExtra("pond", Gson().toJson(ponds[position]))
                            intent.putExtra("pos", "$position")
                            startActivity(intent)
                        }

                        override fun onLongItemClick(view: View?, position: Int) {}
                    }
                ))


        }

        val button = root.findViewById<Button>(R.id.button5)
        button.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            // ...Irrelevant code for customizing the buttons and title

            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.pond_create_dialog, null)
            dialogBuilder.setView(dialogView)

            // Load database
            listener1 = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    val work = ArrayList<String>()
                    val ids = ArrayList<String>()
                    for (s in p0.children) {
                        work.add(s.child("name").getValue(String::class.java)!!)
                        ids.add(s.child("s_id").getValue(String::class.java)!!)
                    }

                    // set dropdown
                    val adapter =
                        ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, work)
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

                    val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
                    var selected = -1
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            selected = -1
                        }

                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            selected = p2
                        }
                    }

                    val amount = dialogView.findViewById<EditText>(R.id.shrimp_amount)
                    val save = dialogView.findViewById<Button>(R.id.button)
                    val desc = dialogView.findViewById<EditText>(R.id.description)
                    val d = dialogBuilder.create()
                    d.show()
                    save.setOnClickListener {

                        //                        val shrimpType = if (selected != -1) work[selected] else ""
                        val srimp_amount =
                            if (amount.text.toString() == "") -1 else amount.text.toString().toInt()
                        val pond_desc = desc.text.toString().trim()

                        if (selected == -1) {
                            Toast.makeText(context!!, "กรุณาเลือกพันธุ์กุ้ง", Toast.LENGTH_LONG)
                                .show()
                        } else if (srimp_amount == -1) {
                            Toast.makeText(context!!, "กรุณาใส่จำนวนกุ้ง", Toast.LENGTH_LONG).show()
                        } else {
                            // create new object
                            val pond = Pond("", pond_desc)
                            val shrimp = Shrimp(
                                "",
                                "",
                                ids[selected],
                                srimp_amount,
                                System.currentTimeMillis()
                            )

                            val pkey = mRef.child("ponds").push().key!!
                            pond.pond_id = pkey
                            mRef.child("ponds").child(pond.pond_id).setValue(pond)

                            val skey = mRef.child("shrimps").push().key!!
                            shrimp.s_id = skey
                            shrimp.pond_id = pond.pond_id
                            mRef.child("shrimps").child(shrimp.s_id).setValue(shrimp)

                            Toast.makeText(context!!, "เพิ่มบ่อกุ้ง สำเร็จ", Toast.LENGTH_LONG)
                                .show()
                            d.dismiss()
                        }
                    }
                }
            }

            query1 = mRef.child("shrimp_code")
            query1.addValueEventListener(listener1)


        }
    }
}