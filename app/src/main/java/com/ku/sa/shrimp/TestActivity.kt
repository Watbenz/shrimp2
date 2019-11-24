package com.ku.sa.shrimp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val mRef = FirebaseDatabase.getInstance().getReference()

//        applicationContext.resources.getStringArray(R.array.shrimp_type).forEachIndexed { i, it ->
//            mRef.child("shrimp_code").child("${i+1}").setValue(it)
//        }
//
//        applicationContext.resources.getStringArray(R.array.task_name).forEachIndexed { i, it ->
//            mRef.child("task_name").child("${i+1}").setValue(it)
//        }
    }
}
