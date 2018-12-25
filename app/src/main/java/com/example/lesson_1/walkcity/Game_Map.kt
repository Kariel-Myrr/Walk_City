package com.example.lesson_1.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game__map.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import kotlin.system.exitProcess

class Game_Map : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game__map)
        inv.setOnClickListener{
            val intent = Intent(this,inventory::class.java)
            startActivity(intent)
        }
        nt.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm Turn")
            builder.setMessage("Are you sure you want to confirm Turn?")
            builder.setPositiveButton("YES"){dialog, which ->
                Toast.makeText(applicationContext,"Ok, turn made, game saved.",Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No"){dialog,which ->
                Toast.makeText(applicationContext,"Turn not confirmed",Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure you want to Exit game?\nAll unsaved progress will be lost.")
        builder.setPositiveButton("YES"){dialog, which ->
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finishActivity(0)
        }
        builder.setNegativeButton("No"){dialog,which ->
            Toast.makeText(applicationContext,"Game continued.",Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
