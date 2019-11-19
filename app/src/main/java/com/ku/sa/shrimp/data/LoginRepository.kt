package com.ku.sa.shrimp.data

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.ku.sa.shrimp.data.model.LoggedInUser
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.OutputStreamWriter


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource, private val context: Context) {

    // in-memory cache of the loggedInUser object
    val FILE_NAME = "login.json"

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
//        checkOnlineState()
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

//    fun checkOnlineState(){
//        val name = readUsername()
//    }

    private fun writeUsername(name: String) {
        // save file in login.json
        val data = LoggedInUser(name)
        val json = Gson().toJson(data)
        // check context is not null and write file
        context.let { it ->
            it.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it?.write(json.toByteArray())
            }
        }
    }

//    private fun readUsername(): String {
//        // get user name from file
//        val fis = InputStreamReader(context!!.openFileInput(FILE_NAME))
//        val loginData = Gson().fromJson(BufferedReader(fis), LoggedInUser::class.java)
//        return loginData.username
//    }

    fun register(user: User) {

    }

//    fun checkHasAccount(user: User): Boolean {
//
//    }
    // TODO: check login from firebase
//    fun checkLogin(): Boolean {
//
//        // check has child on database
//        val rootRef = FirebaseDatabase.getInstance().reference
//        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                // ignore
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.hasChild("name")) {
//                    // run some code
//                }
//            }
//        })
//
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("message")
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                Toast.makeText(context, "Database Failed", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue(String::class.java)
//            }
//        })
//
//    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
