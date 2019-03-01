package com.example.lesson_1.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.lesson_1.walkcity.DataBase.Manager
import com.example.lesson_1.walkcity.DataBase.Settings
import kotlinx.android.synthetic.main.activity_settings.*
import kotlin.system.exitProcess

class Settings_Class : AppCompatActivity() {
    lateinit var manager : Manager

    companion object {
        var flag_move = false
        var flag_back = false
    }

    override fun onStart() {
        super.onStart()
        manager = Manager(this@Settings_Class)
        if(manager.tryingSettings() != 0)manager.downloadSettings()
    }

    override fun onPause() {
        super.onPause()
        flag_back = switch_back_button.isChecked
        flag_move = switch_turns.isChecked
        if(flag_back == false)manager.settings.backDialog = 0
        else manager.settings.backDialog = 1
        if (flag_move == false)manager.settings.nextTurnDialog = 0
        else manager.settings.nextTurnDialog = 1
        manager.unloadSettings()
    }

    fun changeActtoBack(demo : View){
        val intent = Intent(this@Settings_Class,Menu_Class::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        onStart()
        var settings : Settings
        if(manager.tryingSettings() != 0) {
            settings = manager.settings()
            if(settings.nextTurnDialog == 0) flag_move = false
            else flag_move = true
            if(settings.backDialog == 0) flag_back = false
            else flag_back = true
        }
        else{
            flag_move = false
            flag_back = false
        }
        switch_back_button.isChecked = flag_back
        switch_turns.isChecked = flag_move
        settings_back.setOnClickListener(::changeActtoBack)
    }
}
