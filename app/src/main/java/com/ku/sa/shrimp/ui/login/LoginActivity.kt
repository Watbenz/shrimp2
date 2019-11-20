package com.ku.sa.shrimp.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.metalab.asyncawait.async
import com.google.android.gms.gcm.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.database.FirebaseDatabase

import com.ku.sa.shrimp.R
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.ku.sa.shrimp.MainActivity
import com.ku.sa.shrimp.data.User
import com.ku.sa.shrimp.data.model.LoggedInUser
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private val FILE_NAME = "login.json"
    private val data = MutableLiveData<ArrayList<User>>().apply { value = ArrayList() }
    private var out: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        // create LoginViewModel
        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory(applicationContext))
            .get(LoginViewModel::class.java)

        // check if user online
//        val watbenz = User(1, "watbenz", "123", "a", "b", "085", 1, false)
//        register(watbenz)
//        val log = readUsername()
//        Log.i("FirebaseDB", log?.username)
        checkOnline()
//
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser()
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        data.observe(this@LoginActivity, Observer<ArrayList<User>> { arr ->
            val textView = findViewById<TextView>(R.id.testText)
            var text = ""
            arr.forEach {
                text += " ${it.username}"
            }
            textView.text = text
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun checkOnline() {
        val local = readUsername() ?: return
        selectUser(local.username)

        val handler = Handler()
        handler.postDelayed({
            if (out?.isLogin!!) {
                openHome()
            }
        }, 3000)
    }

    fun openHome() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateUiWithUser() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    // get name from file
    private fun selectUser(name: String) {
        // get username from database
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // ignore
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (s in p0.children) {
                        out = s.getValue(User::class.java)
                    }
                }
            }
        }

        // SELECT username FROM users
        // WHERE name == username
        val mRef = FirebaseDatabase.getInstance().getReference("users")
        val query = mRef.orderByChild("username").equalTo(name)
        query.addListenerForSingleValueEvent(listener)
    }


    @SuppressLint("ShowToast")
    fun register(user: User) {
        // check duplicate online
        var dup = false
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // ignore
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    dup = true
                }
            }
        }

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")

        // SELECT username FROM users
        // WHERE name == username
        val query = myRef.orderByChild("username").equalTo(user.username)
        query.addListenerForSingleValueEvent(listener)

        val handler = Handler()
        handler.postDelayed({
            if (dup == true) {
                Toast.makeText(this, "มี user นี้อยู่ในระบบแล้ว", Toast.LENGTH_LONG)
            } else {
                myRef.push().setValue(user)
            }
        }, 3000)

    }

    private fun writeUsername(name: String) {
        // save file in login.json
        val data = LoggedInUser(name)
        val json = Gson().toJson(data)

        // check context is not null and write file
        applicationContext.let { it ->
            it.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it?.write(json.toByteArray())
            }
        }
    }

    private fun readUsername(): LoggedInUser? {
        val file: File = baseContext.getFileStreamPath(FILE_NAME)
        if (!file.exists()) {
            return null
        }
        val fis = openFileInput(FILE_NAME)
        val reader = BufferedReader(InputStreamReader(fis))

        return Gson().fromJson(reader, LoggedInUser::class.java)
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

//        val listener = object: ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                // ignore
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                data.value?.clear()
//
//                if (p0.exists()) {
//                    for (s in p0.children) {
//                        val u = s.getValue(User::class.java)
//                        data.value!!.add(u!!)
//                    }
//
//                    data.observe(this@LoginActivity, Observer<ArrayList<User>> { arr ->
//                        val textView = findViewById<TextView>(R.id.testText)
//                        var text = ""
//                        arr.forEach{
//                            text += " ${it.username}"
//                        }
//                        textView.text = text
//                    })
//                }
//            }
//        }


// register user
//        val u1 = User(1, "Dif", "1234", "Parin", "Thong", "085", 1, true)
//        val u2 = User(2, "ITAE", "4321", "BOSSO", "GENO", "085", 1, true)
//        val u3 = User(3, "KOOKKIK", "1324", "CHRron", "Felel", "085", 1, true)
//        loginViewModel.loginRepository.register(u1)
//        loginViewModel.loginRepository.register(u2)
//        loginViewModel.loginRepository.register(u3)

