package cool_guys.walkcity

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
        Log.d("FLAG_TAG", "Pressed continue game")
        val intent = Intent(this@Menu_Class,Game_Map_Class::class.java)
        intent.putExtra("status", "continue game")
        startActivity(intent)
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
        supportActionBar?.hide()
        setContentView(R.layout.activity_menu)
        play.setOnClickListener(::changeActtoContinueGame)
        settings.setOnClickListener(::changeActtoSettings)
        new_game.setOnClickListener(::changeActtoNewGame)
    }
}


