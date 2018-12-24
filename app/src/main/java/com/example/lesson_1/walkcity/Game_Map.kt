package com.example.lesson_1.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        nt.setOnClickListener {
            alert ("Make turn?") {
                title = "----------"
                positiveButton("Yes") {

                }
                negativeButton("No") {
                    toast("Ok")
                }

            }
        }
    }

    override fun onBackPressed() {
        alert ("Are you sure you want to quit?\nAll unsaved progress will be lost."){
            title = "Quit"
            positiveButton ("Yes") {
//                val intent = Intent(this,Menu::class.java)
//                startActivity(intent)
                exitProcess(0)
            }
            negativeButton ("No"){
                toast("Ok")
            }
        }
    }
}
