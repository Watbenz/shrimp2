package com.ku.sa.shrimp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ku.sa.shrimp.data.User
import com.ku.sa.shrimp.data.Util
import kotlinx.android.synthetic.main.activity_register.*
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class RegisterActivity : AppCompatActivity() {
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editTextArray = arrayListOf(
            fname_editText.editText!!,          // 0
            lname_editText.editText!!,          // 1
            username_editText.editText!!,       // 2
            pass_editText.editText!!,           // 3
            email_editText.editText!!,          // 4
            tel_editText.editText!!             // 5
        )

        val dataArray = ArrayList<MutableLiveData<String>>()
        for (i in 0..editTextArray.size) { dataArray.add(MutableLiveData()) }

        // add after text changed
        editTextArray.forEachIndexed { i, it ->
            when (i) {
                in 0..3 -> {
                    val updateText = object : TextWatcher {
                        override fun afterTextChanged(p0: Editable?) { dataArray[i].value = p0.toString().trim() }
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    }
                    it.addTextChangedListener(updateText)
                }
                else -> {
                    it.setHintTextColor(Color.GREEN)
                    val updateText = object : TextWatcher {
                        override fun afterTextChanged(p0: Editable?) { dataArray[i].value = p0.toString() }
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    }
                    it.addTextChangedListener(updateText)
                }
            }
        }


        // observe text
        dataArray.forEachIndexed { i, each ->
            when (i) {
                in 0..3 -> {
                    each.observe(this, Observer {
                        if (i == 2 && it.contains(Regex("[&=_'${'-'}+,<>${'{'}${'}'}{}.!]"))) {
                            editTextArray[i].error ="ไม่สารถใช้ตัวอักษร & = ${'_'} - + < > ${'['} ${']'} { } . ! ในชื่อได้"
                        }
                        else if (i == 3 && it.length < 6) {
                                editTextArray[i].error ="รหัสผ่านต้องมากกว่า 5 ตัวอักษร"
                        }
                        else {
                            if (it == "") {
                                editTextArray[i].error ="กรุณาเติมข้อมูลลงในช่องว่าง"
                            }
                            else if (it.length > 30) {
                                editTextArray[i].error ="ข้อมูลไม่สามารถมากกว่า 30 ตัวอักษร"
                            }
                            else {
                                editTextArray[i].error = null
                            }
                        }
                    })
                }
                4 -> {
                    each.observe(this, Observer {
                        editTextArray[i].error = if (it.length > 30) "ข้อมูลไม่สามารถมากกว่า 30 ตัวอักษร" else null
                    })
                }
                5 -> {
                    each.observe(this, Observer {
                            editTextArray[i].error = if (it.length != 10 || it != "") "กรุณากรอกเบอร์โทรศัพท์ให้ครบ 10 หลัก" else null
                    })
                }
            }
        }

        register_button.setOnClickListener {
            Log.i("registery", "button pressed")
            editTextArray.forEachIndexed { i, edit ->
                if (edit.error != null)  {
                    Toast.makeText(applicationContext, "กรุณากรอกข้อมูลให้ถูกต้อง", Toast.LENGTH_LONG).show()
                    Log.i("registery", "data failed1")

                    return@setOnClickListener
                }
                when (i) {
                    in 0..3 -> {
                        if (dataArray[i].value == null || dataArray[i].value == "") {
                            Toast.makeText(applicationContext, "กรุณากรอกข้อมูลให้ถูกต้อง", Toast.LENGTH_LONG).show()
                            Log.i("registery", "data failed2")
                            return@setOnClickListener
                        }
                    }
                }
            }


            val username = dataArray[2].value!!
            val password = dataArray[3].value!!
            val fname = dataArray[0].value!!
            val lname = dataArray[1].value!!
            val tel = if (dataArray[5].value == null) "" else dataArray[5].value
            val email = if (dataArray[4].value == null) "" else dataArray[4].value
            val newUser = User("", username, password, fname, lname, tel!!, email!!,1)

            Log.i("registery", "waiting")
            val convertedName = Util.convert(username)
            mAuth.createUserWithEmailAndPassword(convertedName, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i("registery", "success")

                    // create data on firebase
                    val user = task.result!!.user!!.uid
                    newUser.id = user
                    val mRef = FirebaseDatabase.getInstance().getReference("users")
                    mRef.child(user).setValue(newUser)

                    Toast.makeText(applicationContext, "คุณ $fname $lname ถูกเพิ่มเข้าสู่ระบบแล้ว", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().apply {
                        signOut()
                        signInWithEmailAndPassword(Util.convert(Util.currentUser.username), Util.currentUser.password)
                    }
                    finish()
                }
                else {
                    Log.i("registery", "sign up failed")
                    Toast.makeText(applicationContext, "ฐานข้อมูลผิดพลาด", Toast.LENGTH_LONG).show()
                }
            }
        }
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


