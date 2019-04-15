package cool_guys.walkcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import cool_guys.walkcity.DataBase.Manager
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.system.exitProcess

class Menu_Class : AppCompatActivity() {
    lateinit var manager : Manager

    fun changeActtoSettings(demo : View){
        val intent = Intent(this@Menu_Class,Settings_Class::class.java)
        startActivity(intent)
    }
    fun changeActtoNewGame(demo : View){
        Log.d("FLAG_TAG", "Pressed new game")
        val intent = Intent(this@Menu_Class,Game_Map_Class::class.java)
        intent.putExtra("status", "new game")
        startActivity(intent)
    }
    fun changeActtoContinueGame(demo : View){
        Log.d("FLAG_TAG", "Menu test sizeCity = ${manager.tryingCity()}")
        if(manager.tryingCity() > 0) {
            Log.d("FLAG_TAG", "Pressed continue game")
            val intent = Intent(this@Menu_Class, Game_Map_Class::class.java)
            intent.putExtra("status", "continue game")
            startActivity(intent)
        }
        else{
            Log.d("FLAG_TAG", "Menu test2")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("FLAG_TAG", "Menu_Class onStart()")

    }

    override fun onPause() {
        super.onPause()
        Log.d("FLAG_TAG", "Menu_Class onPause()")
        finish()
    }

    override fun onStop() {
        super.onStop()
        Log.d("FLAG_TAG", "Menu_Class onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FLAG_TAG", "Menu_Class onDestroy()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FLAG_TAG", "Menu_Class onResume()")
    }

    fun changeActtoBack(){
        finish()
    }

    override fun onBackPressed() {
        changeActtoBack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FLAG_TAG", "Init manager")
        manager = Manager(this@Menu_Class)
        Log.d("FLAG_TAG", "Complete init manager")
        supportActionBar?.hide()
        setContentView(R.layout.activity_menu)
        if(manager.tryingCity() == 0){
            play.isClickable=false
            play.visibility= View.GONE
        }
        play.setOnClickListener(::changeActtoContinueGame)
        settings.setOnClickListener(::changeActtoSettings)
        new_game.setOnClickListener(::changeActtoNewGame)

        var getIntentFromMenu = intent
        var statgame = ""
        if(getIntentFromMenu.hasExtra("statgame"))
            statgame = getIntentFromMenu.getStringExtra("statgame")
        if(statgame == "lose"){
            val builder = AlertDialog.Builder(this@Menu_Class)
            builder.setTitle("Game Info")
            builder.setMessage("You lose.")
            builder.setPositiveButton("Ok") { dialog, which ->
                Log.d("FLAG_TAG", "You lose. Yes")
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        else if(statgame == "won"){
            val builder = AlertDialog.Builder(this@Menu_Class)
            builder.setTitle("Game Info")
            builder.setMessage("You won.")
            builder.setPositiveButton("Ok") { dialog, which ->
                Log.d("FLAG_TAG", "You won. Yes")
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}


