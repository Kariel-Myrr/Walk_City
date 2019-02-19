package com.example.lesson_1.walkcity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_1.walkcity.DataBase.Manager
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

        var manager = Manager(this)
        manager.download()
        var settings = manager.settings()

        var flag_move : Boolean = false
        var flag_back :Boolean = false

        if(settings.backDialog == 0) flag_back = false
        if(settings.nextTurnDialog == 0) flag_move = false
        if(settings.backDialog == 1) flag_back = true
        if(settings.nextTurnDialog == 1) flag_move = true


        switch_back_button.isChecked = flag_back
        switch_turns.isChecked = flag_move


        switch_back_button.setOnCheckedChangeListener{_, isChecked->
            settings.backDialog = 1
        }

        switch_turns.setOnCheckedChangeListener { _, isChecked ->
            settings.nextTurnDialog = 1
        }

        settings_back.setOnClickListener {
            exitProcess(0)
        }
    }
}
