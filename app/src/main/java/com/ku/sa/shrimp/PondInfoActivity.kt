package com.ku.sa.shrimp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ku.sa.shrimp.data.User
import kotlinx.android.synthetic.main.fragment_notifications.*

class PondInfoActivity : AppCompatActivity() {

    private val mRef = FirebaseDatabase.getInstance().reference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notifications)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val users = MutableLiveData<ArrayList<User>>()
        users.value = arrayListOf()
        val tmp = ArrayList<User>()

        mRef.child("users").orderByChild("type").equalTo(1.0).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                tmp.clear()
                p0.children.forEach {
                    val u = it.getValue(User::class.java)!!
                    tmp.add(u)
                }
                users.value = tmp
            }

        })

        val pos = intent.getStringExtra("position")!!.toInt()
        imageView.setImageResource(R.drawable.shrimp)
        worker_label.text = "รายชื่อลูกจ้าง"
        worker_name_textView.text = "บ่อที่ $pos"
        position_textView.isVisible = false

        employee_recycler.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = WorkerAdapter(users)
        }

        users.observe(this, Observer {
            employee_recycler.adapter!!.notifyDataSetChanged()
        })




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->  {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
