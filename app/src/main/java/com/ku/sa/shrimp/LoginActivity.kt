package com.ku.sa.shrimp

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.ku.sa.shrimp.ui.login.LoginViewModel
import com.ku.sa.shrimp.ui.login.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    // login firebase variable
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = username
        val password = password
        val login = login
        val loading = loading

        // create LoginViewModel
        val loginViewModel = ViewModelProviders.of(this,
            LoginViewModelFactory()
        )
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
            userValid = false
            if (it == "") username.error = "กรุณาใส่ Email"
            else  {
                username.error = null
                userValid = true
            }
            checkButton(userValid, passValid)
        })
        livePass.observe(this, Observer<String> {
            passValid = false

            when {
                it == "" -> password.error = "กรุณาใส่ password"
                it.length <= 5 -> password.error = "password ต้องมีมากกว่า 5 ตัวอักษรขึ้นไป"
                else -> {
                    password.error = null
                    passValid = true
                }
            }
            checkButton(userValid, passValid)
        })


        login.setOnClickListener {
            // login.
            loading.visibility = View.VISIBLE
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
            loading.visibility = View.GONE
        }
    }

    private fun checkButton(userValid: Boolean, passValid: Boolean) {
        login.isEnabled =  (userValid && passValid)
    }
}
