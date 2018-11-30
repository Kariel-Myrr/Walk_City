package com.example.lesson_1.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    fun cliuck(demo : View){
        val intent = Intent(this, Act2::class.java)
        intent.putExtra("te",texxt.text)
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bot.setOnClickListener(::cliuck)

        val data = arrayOf("1", "2", "3", "4")
        val adapter = ArrayAdapter<String>(this, R.layout.botton_list, data)

        list.adapter = adapter
        list.setOnItemClickListener{ parent, view, position, id -> Toast(this, "$id clicked", Toast.LENGTH_LONG).show()}

    }


}
