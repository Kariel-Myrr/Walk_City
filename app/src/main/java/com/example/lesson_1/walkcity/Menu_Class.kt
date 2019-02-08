package com.example.lesson_1.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.system.exitProcess

class Menu_Class : AppCompatActivity() {
    
    fun changeActtoSettings(demo : View){
        val intent = Intent(this,Settings_Class::class.java)
        startActivity(intent)
    }
    fun changeActtoGameMap(demo : View){
        val intent = Intent(this,Game_Map_Class::class.java)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        play.setOnClickListener(::changeActtoGameMap)
        settings.setOnClickListener(::changeActtoSettings)
        new_game.setOnClickListener(::changeActtoSettings)

        exit.setOnClickListener {
            exitProcess(0)
        }
    }
}


