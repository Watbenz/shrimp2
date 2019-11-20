package com.ku.sa.shrimp.data

import android.os.Handler
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import co.metalab.asyncawait.async
import com.google.firebase.database.*
import com.google.gson.Gson
import com.ku.sa.shrimp.data.model.LoggedInUser
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private var out: User? = null
    private var success = false

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            // if out is null or password not equal
            selectUser(username)

            val handler = Handler()

            Thread().run {
                handler.postDelayed({

//                        Log.i("FirebaseDB", "password")
//                        Log.i("FirebaseDB", password)
                        Log.i("FirebaseDB", out?.password)
                    if (out != null && out!!.password == password) {
                        Log.i("FirebaseDB", out?.password)
                        success = true
                    }

                }, 1000)
            }



            if (success) {
                return Result.Success(LoggedInUser(username))
            }

            Log.i("FirebaseDB", "failed")
            Log.i("FirebaseDB", success.toString())
            return Result.Fail(User())

//            return Result.Fail(User())

//            return if (success) Result.Success(LoggedInUser(username))
//            else Result.Fail(User(username = username, password = password))
//
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    private fun updateLogin(username: String, st: Boolean) {


        // SELECT username FROM users
        // WHERE name == username
        val mRef = FirebaseDatabase.getInstance().getReference("users")
        val query = mRef.orderByChild("username").equalTo(username)
    }

    private fun selectUser(username: String) {
        // get username from database
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // ignore
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (s in p0.children) {
                        out = s.getValue(User::class.java)
                        Log.i("FirebaseDB", out?.username)
                    }
                }
            }
        }

        // SELECT username FROM users
        // WHERE name == username
        val mRef = FirebaseDatabase.getInstance().getReference("users")
        val query = mRef.orderByChild("username").equalTo(username)
        query.addListenerForSingleValueEvent(listener)
    }


}

