package com.ku.sa.shrimp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ku.sa.shrimp.data.User
import com.ku.sa.shrimp.data.Util
import com.ku.sa.shrimp.ui.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.activity_loading.*

class LoadingActivity : AppCompatActivity() {

    // login firebase variable
    private val mAuth = FirebaseAuth.getInstance()
    private val mAuthListener = FirebaseAuth.AuthStateListener {
        val user: FirebaseUser? = it.currentUser
        // if already login, login to app, if not, let's login
        if (user != null) {
            // get data from db
            val mRef = FirebaseDatabase.getInstance().getReference("users").child(user.uid)
            Log.i("registery", "login in")
            Log.i("registery", user.uid)
            mRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    Util.currentUser = p0.getValue(User::class.java)!!
                }
            })
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
//            Util.currentUser =

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
    }
}
