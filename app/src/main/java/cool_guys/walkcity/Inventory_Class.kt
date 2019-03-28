package cool_guys.walkcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cool_guys.walkcity.R.layout.activity_botton_list
import kotlinx.android.synthetic.main.activity_game__map.*

class Inventory_Class : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FLAG_TAG", "Inventory_Class onCreate()")
        setContentView(activity_botton_list)

    }

    override fun onStart() {
        super.onStart()
        Log.d("FLAG_TAG", "Inventory_Class onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FLAG_TAG", "Inventory_Class onPause()")
        finish()
    }

    override fun onStop() {
        super.onStop()
        Log.d("FLAG_TAG", "Inventory_Class onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FLAG_TAG", "Inventory_Class onDestroy()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FLAG_TAG", "Inventory_Class onResume()")
    }

    fun changeActtoBack(){
        val intent = Intent(this@Inventory_Class,Game_Map_Class::class.java)
        intent.putExtra("status", "continue game")
        startActivity(intent)
    }

    override fun onBackPressed() {
        changeActtoBack()
    }
}