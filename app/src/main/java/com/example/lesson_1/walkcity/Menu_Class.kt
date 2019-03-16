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
    fun getValueInt(KEY_NAME: String): Int {
        val sharedPref: SharedPreferences = getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
        return sharedPref.getInt(KEY_NAME, 0)
    }

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
        var st : Int = getValueInt("exitingApp")
        val PREFS_NAME = "exitingApp"
        val sharedPref: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        if(st == 1){
            editor.putInt(PREFS_NAME, 0)
            editor.commit()
            finish()
            System.exit(0)
        }

        editor.putInt(PREFS_NAME, 0)
        editor.commit()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        play.setOnClickListener(::changeActtoContinueGame)
        settings.setOnClickListener(::changeActtoSettings)
        new_game.setOnClickListener(::changeActtoNewGame)

        editor.putInt(PREFS_NAME, 0)
        editor.commit()


        exit.setOnClickListener {
            editor.putInt(PREFS_NAME, 1)
            editor.commit()
            finish()
            System.exit(0)
        }
    }
}


