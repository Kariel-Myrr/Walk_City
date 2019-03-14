package com.example.lesson_1.walkcity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Inventory_Class : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        fun getValueInt(KEY_NAME: String): Int {
            val sharedPref: SharedPreferences = getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
            return sharedPref.getInt(KEY_NAME, 0)
        }


        var st : Int = getValueInt("exitingApp")
        if(st == 1){
            finish()
            System.exit(0)
        }




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_botton_list)

    }



}