package com.example.lesson_1.walkcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.system.exitProcess

class Menu_Class : AppCompatActivity() {
    fun changeActtoSettings(demo : View){
        val intent = Intent(this@Menu_Class,Settings_Class::class.java)
        startActivity(intent)
    }
    fun changeActtoNewGame(demo : View){
        val intent = Intent(this@Menu_Class,Game_Map_Class::class.java)
        intent.putExtra("status", "new game")
        startActivity(intent)
    }
    fun changeActtoContinueGame(demo : View){
        val intent = Intent(this@Menu_Class,Game_Map_Class::class.java)
        intent.putExtra("status", "continue game")
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        play.setOnClickListener(::changeActtoContinueGame)
        settings.setOnClickListener(::changeActtoSettings)
        new_game.setOnClickListener(::changeActtoNewGame)
    }
}


