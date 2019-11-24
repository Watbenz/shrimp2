package com.ku.sa.shrimp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class PondInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notifications)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


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
