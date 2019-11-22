package com.ku.sa.shrimp

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_pond_info.*

class PondInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pond_info)
        setSupportActionBar(toolbar)


//        var bundle :Bundle? = intent.extras
//        var message = bundle!!.getString("value")
//
//        Log.i("FirebaseDB", message!!)



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
