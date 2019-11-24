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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ku.sa.shrimp.PondInfoActivity
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.data.Shrimp
import com.ku.sa.shrimp.data.model.Pond
import com.ku.sa.shrimp.ui.RecyclerMenuClickListener
import kotlinx.android.synthetic.main.pond_crate_dialog.*


class HomeFragment : Fragment() {

    private val mRef = FirebaseDatabase.getInstance().getReference()
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var query1: Query
    private lateinit var query2: Query
    private lateinit var listener: ValueEventListener
    private lateinit var listener1: ValueEventListener
    private lateinit var listener2: ValueEventListener

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
                    val ids = ArrayList<String>()
                    for (s in p0.children) {
                        work.add(s.child("name").getValue(String::class.java)!!)
                        ids.add(s.child("s_id").getValue(String::class.java)!!)
                    }

                    // set dropdown
                    val adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, work)
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

                    val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
                    var selected  = -1
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(p0: AdapterView<*>?) { selected = -1 }
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { selected = p2 }
                    }

                    val amount =  dialogView.findViewById<EditText>(R.id.shrimp_amount)
                    val save = dialogView.findViewById<Button>(R.id.button)
                    val desc = dialogView.findViewById<EditText>(R.id.description)
                    val d = dialogBuilder.create()
                    d.show()
                    save.setOnClickListener {

//                        val shrimpType = if (selected != -1) work[selected] else ""
                        val srimp_amount = if (amount.text.toString() == "") -1 else amount.text.toString().toInt()
                        val pond_desc = desc.text.toString().trim()

                        if (selected == -1) {
                            Toast.makeText(context!!, "กรุณาเลือกพันธุ์กุ้ง", Toast.LENGTH_LONG).show()
                        }
                        else if (srimp_amount == -1) {
                            Toast.makeText(context!!, "กรุณาใส่จำนวนกุ้ง", Toast.LENGTH_LONG).show()
                        }
                        else {
                            // create new object
                            val pond = Pond("", pond_desc)
                            val shrimp = Shrimp("", "", ids[selected], srimp_amount, System.currentTimeMillis())

                            val pkey = mRef.child("ponds").push().key!!
                            pond.pond_id = pkey
                            mRef.child("ponds").child(pond.pond_id).setValue(pond)

                            val skey = mRef.child("shrimps").push().key!!
                            shrimp.s_id = skey
                            shrimp.pond_id = pond.pond_id
                            mRef.child("shrimps").child(shrimp.s_id).setValue(shrimp)

                            Toast.makeText(context!!, "เพิ่มบ่อกุ้ง สำเร็จ", Toast.LENGTH_LONG).show()
                            d.dismiss()
                        }
                    }
                }
            }

            query1 = mRef.child("shrimp_code")
            query1.addValueEventListener(listener)



        }



        return root
    }

    override fun onPause() {
        super.onPause()
        query1.removeEventListener(listener)
    }
}