package com.example.lesson_1.walkcity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_settings.*
import kotlin.system.exitProcess

class Settings_Class : AppCompatActivity() {

    companion object {
        val checkMoveDialog = "checkMoveDialog"
        val checkBackDialog = "checkBackDialog"
        val resFile = "demo_filename"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        switch_back_button.setOnCheckedChangeListener{_, isChecked->
            val spInstance = getSharedPreferences(resFile, MODE_PRIVATE)
            val editor = spInstance.edit()
            editor.putBoolean(checkBackDialog, isChecked)
            editor.commit()
        }

        switch_turns.setOnCheckedChangeListener { _, isChecked ->
            val spInstance = getSharedPreferences(resFile, MODE_PRIVATE)
            val editor = spInstance.edit()
            editor.putBoolean(checkMoveDialog, isChecked)
            editor.commit()

            val flag = getSharedPreferences(resFile, Context.MODE_PRIVATE).getBoolean(Settings_Class.checkMoveDialog, false)
            Log.d("FLAG_TAG", flag.toString())
        }

        settings_back.setOnClickListener {
            exitProcess(0)
        }
    }
}
