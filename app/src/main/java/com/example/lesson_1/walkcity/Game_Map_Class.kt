package com.example.lesson_1.walkcity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.example.lesson_1.walkcity.DataBase.Manager
import kotlinx.android.synthetic.main.activity_game__map.*
import kotlin.system.exitProcess

class Game_Map_Class : AppCompatActivity() {
    lateinit var manager : Manager

    override fun onStart() {
        super.onStart()
        manager = Manager(this@Game_Map_Class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game__map)


        inv.setOnClickListener{
            val intent = Intent(this@Game_Map_Class,Inventory_Class::class.java)
            startActivity(intent)
        }
        nt.setOnClickListener{
            val flag = getSharedPreferences(Settings_Class.resFile, Context.MODE_PRIVATE).getBoolean(Settings_Class.checkMoveDialog, false)
            Log.d("FLAG_TAG", flag.toString())
            if(flag == true) {
                Toast.makeText(applicationContext, "Turn made, game saved.", Toast.LENGTH_SHORT).show()
            }
            else {
                val builder = AlertDialog.Builder(this@Game_Map_Class)
                builder.setTitle("Confirm Turn")
                builder.setMessage("Are you sure you want to confirm Turn?")
                builder.setPositiveButton("YES") { dialog, which ->
                    Toast.makeText(applicationContext, "Ok, turn made, game saved.", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(applicationContext, "Turn not confirmed", Toast.LENGTH_SHORT).show()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun onBackPressed() {
        val flag = getSharedPreferences(Settings_Class.resFile, Context.MODE_PRIVATE).getBoolean(Settings_Class.checkBackDialog, false)
        if(flag == true) exitProcess(0)
        val builder = AlertDialog.Builder(this@Game_Map_Class)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure you want to Exit game?\nAll unsaved progress will be lost.")
        builder.setPositiveButton("YES"){dialog, which ->
            exitProcess(0)
        }
        builder.setNegativeButton("No"){dialog,which ->
            Toast.makeText(applicationContext,"Game continued.",Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
