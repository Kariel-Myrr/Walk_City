package com.example.lesson_1.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class Act2 : AppCompatActivity() {


    fun cliuck(demo : View){
        bot.text = intent.getStringExtra("te")

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act2)
        bot.setOnClickListener(::cliuck)
    }
}
