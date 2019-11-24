package com.ku.sa.shrimp.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.ku.sa.shrimp.R

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        applicationContext.resources.getStringArray(R.array.shrimp_type).forEach {
            val a = FirebaseDatabase.getInstance().getReference("shrimp_code").push()
            val key = a.key
            a.child("name").setValue(it)
            a.child("s_id").setValue(key)
        }
    }
}
