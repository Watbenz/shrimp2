package com.ku.sa.shrimp.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ku.sa.shrimp.InfoActivity

import com.ku.sa.shrimp.R
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import kotlin.collections.ArrayList


class LoginActivity : AppCompatActivity() {

    // login firebase variable
    private val mAuth = FirebaseAuth.getInstance()
    private val mAuthListener = FirebaseAuth.AuthStateListener {
        val user: FirebaseUser? = it.currentUser
        if (user == null) {
//            TODO("user is sign in")
//            Toast.makeText(this, "has no account", Toast.LENGTH_LONG).show()
        } else {
//            TODO("user is not sign in")
//            Toast.makeText(this, "has account", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = username
        val password = password
        val login = login
        val loading = loading

        // create LoginViewModel
        val loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val liveUser = MutableLiveData<String>()
        val livePass = MutableLiveData<String>()

        username.editText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { liveUser.value = p0.toString().trim() }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        password.editText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { livePass.value = p0.toString() }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        var userValid = false
        var passValid = false
        liveUser.observe(this, Observer<String> {
            userValid = it != ""
            if (it == "") username.error = "กรุณาใส่ Email"
            checkButton(userValid, passValid)
        })
        livePass.observe(this, Observer<String> {
            passValid = (it != "" && it.length >= 5)

            if (it == "") password.error = "กรุณาใส่ password"
            else if (it.length < 6) password.error = "password ต้องมีมากกว่า 5 ตัวอักษรขึ้นไป"
            checkButton(userValid, passValid)
        })


        login.setOnClickListener {
            // login
            mAuth.signInWithEmailAndPassword(liveUser.value!!, livePass.value!!)
                .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, InfoActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Email หรือ Password ของท่านไม่ถูกต้อง", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkButton(userValid: Boolean, passValid: Boolean) {
        login.isEnabled =  (userValid && passValid)
    }


    // create account
//        mAuth.createUserWithEmailAndPassword(uname, upass).addOnCompleteListener(this) { task ->
//            if (!task.isSuccessful) {
//                Toast.makeText(this@LoginActivity, "Authentication failed.", Toast.LENGTH_LONG).show()
//            }
//        }


}
