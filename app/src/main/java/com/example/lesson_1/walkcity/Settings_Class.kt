package com.example.lesson_1.walkcity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class Settings_Class : AppCompatActivity() {

    companion object {
        val checkMoveDialog = "checkMoveDialog"
        val checkBackDialog = "checkBackDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        switch_back_button.setOnCheckedChangeListener{_, isChecked->
            val spInstance = getSharedPreferences(localClassName, MODE_PRIVATE)
            val editor = spInstance.edit()
            editor.putBoolean(checkBackDialog, isChecked)
            editor.apply()
        }

        switch_turns.setOnCheckedChangeListener { _, isChecked ->
            val spInstance = getSharedPreferences(localClassName, MODE_PRIVATE)
            val editor = spInstance.edit()
            editor.putBoolean(checkMoveDialog, isChecked)
            editor.apply()
        }
    }
}
