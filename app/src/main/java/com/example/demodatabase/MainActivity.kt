package com.example.demodatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun abreShared(view: View) {
        val intent: Intent = Intent(this, SharedPrefsActivity::class.java)
        startActivity(intent)
    }

    fun abreSql(view: View) {
        val intent: Intent = Intent(this, SqlActivity::class.java)
        startActivity(intent)
    }

    fun abreRoom(view: View) {
        val intent: Intent = Intent(this, RoomActivity::class.java)
        startActivity(intent)
    }
}