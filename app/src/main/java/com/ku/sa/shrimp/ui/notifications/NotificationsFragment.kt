package com.ku.sa.shrimp.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ku.sa.shrimp.LoginActivity
import com.ku.sa.shrimp.R
import com.ku.sa.shrimp.RegisterActivity
import com.ku.sa.shrimp.data.User
import com.ku.sa.shrimp.data.Util


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        val register = root.findViewById<Button>(R.id.register)
        register.setOnClickListener {
            val intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
        }

        val logout = root.findViewById<Button>(R.id.button_logout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val pos = root.findViewById<TextView>(R.id.position_textView)
        val label = root.findViewById<TextView>(R.id.worker_label)
        val recycler = root.findViewById<RecyclerView>(R.id.employee_recycler)
        val name = root.findViewById<TextView>(R.id.worker_name_textView)


        name.text = "${Util.currentUser.fName} ${Util.currentUser.lName}"

        Log.i("registery", "current: " + Util.currentUser.toString())

        // set layout display for each user
        when (Util.currentUser.type) {
            0 -> {
                pos.text = "หัวหน้า"
                Log.i("registery", "on check")

                // load data form db
                // SELECT type FROM users
                // WHERE type == 1
                val db = FirebaseDatabase.getInstance().getReference("users")
                val query = db.orderByChild("type").equalTo(1.0)

                var arr = MutableLiveData<ArrayList<User>>()
                arr.value = ArrayList()
                recycler.also {
                    it.layoutManager = LinearLayoutManager(context)
                    it.adapter = WorkerDisplayAdapter(arr, false)
                }

                arr.observe(this, Observer {
                    recycler.adapter!!.notifyDataSetChanged()
                })


                query.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {
                        Log.i("registery", "onDataChange")
                        val tmp = ArrayList<User>()
                        for (s in p0.children) {
                            Log.i("registery", "data: " + s.getValue(User::class.java).toString())
                            tmp.add(s.getValue(User::class.java)!!)
                        }
                        arr.value = tmp
                    }
                })

            }
            // hide register
            1 -> {
                pos.text = "ลูกน้อง"
                label.text = "ข้อมูลทั่วไป"
                register.isVisible = false
                recycler.also {
                    it.layoutManager = LinearLayoutManager(context)
                    it.adapter = WorkerProfileAdapter()
                }
            }
        }



        return root
    }

}