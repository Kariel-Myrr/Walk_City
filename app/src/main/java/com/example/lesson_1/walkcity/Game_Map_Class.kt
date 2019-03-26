package cool_guys.walkcity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import cool_guys.walkcity.DataBase.Manager
import cool_guys.walkcity.DataBase.Settings
import kotlinx.android.synthetic.main.activity_game__map.*



class Game_Map_Class : AppCompatActivity() {

    lateinit var manager : Manager

    companion object {
        var flag_move = false
        var flag_back = false
    }

    override fun onStart() {
        super.onStart()
        var getIntentFromMenu = intent
        var status = ""
        if(getIntentFromMenu.hasExtra("status"))
            status = getIntentFromMenu.getStringExtra("status")
        if(status == "new game")manager.init()
        else manager.download()
        ViewMap.Map = manager.tile
        ViewMap.drawMatr()
    }

    override fun onPause() {
        manager.tile = ViewMap.Map
        manager.unload()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    fun changeActtoBack(){
        val intent = Intent(this@Game_Map_Class,Menu_Class::class.java)
        ViewMap.Map = manager.tile
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game__map)
        manager = Manager(this@Game_Map_Class)
        var settings : Settings
        if(manager.tryingSettings() != 0) {
            manager.downloadSettings()
            settings = manager.settings()
            Log.d("FLAG_TAG", "backDialog = ${settings.backDialog} nextTurnDialog = ${settings.nextTurnDialog}")
            if(settings.nextTurnDialog == 0) flag_move = false
            else flag_move = true
            if(settings.backDialog == 0) flag_back = false
            else flag_back = true
            Log.d("FLAG_TAG", "flag_back = $flag_back flag_move = $flag_move")
        }
        else{
            flag_move = false
            flag_back = false
        }
        inv.setOnClickListener{
            val intent = Intent(this@Game_Map_Class,Inventory_Class::class.java)
            startActivity(intent)
        }
        nt.setOnClickListener{
            if(flag_move == true) {
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
        if (flag_back == true) changeActtoBack()
        else {
            val builder = AlertDialog.Builder(this@Game_Map_Class)
            builder.setTitle("Exit")
            builder.setMessage("Are you sure you want to Exit game?\nAll unsaved progress will be lost.")
            builder.setPositiveButton("YES") { dialog, which ->
                changeActtoBack()
            }
            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(applicationContext, "Game continued.", Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}
