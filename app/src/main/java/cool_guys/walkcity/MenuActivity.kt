package cool_guys.walkcity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cool_guys.walkcity.database.Manager
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    lateinit var manager: Manager

    private fun changeActToSettings(demo: View) {
        val intent = Intent(this@MenuActivity, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun changeActToNewGame(demo: View) {
        Log.d(TAG, "Pressed new game")
        val intent = Intent(this@MenuActivity, GameMapActivity::class.java)
        intent.putExtra("status", "new game")
        startActivity(intent)
    }

    private fun changeActToContinueGame(demo: View) {
        Log.d(TAG, "Menu test sizeCity = ${manager.tryingCity()}")
        if (manager.tryingCity() > 0) {
            Log.d(TAG, "Pressed continue game")
            val intent = Intent(this@MenuActivity, GameMapActivity::class.java)
            intent.putExtra("status", "continue game")
            startActivity(intent)
        } else {
            Log.d(TAG, "Menu test2")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MenuActivity onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MenuActivity onPause()")
        finish()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MenuActivity onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MenuActivity onDestroy()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MenuActivity onResume()")
    }

    private fun changeActToBack() {
        finish()
    }

    override fun onBackPressed() {
        changeActToBack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Init manager")
        manager = Manager(this@MenuActivity)
        Log.d(TAG, "Complete init manager")
        supportActionBar?.hide()
        setContentView(R.layout.activity_menu)
        if (manager.tryingCity() == 0) {
            play.isClickable = false
            play.visibility = View.GONE
        }
        play.setOnClickListener(::changeActToContinueGame)
        settings.setOnClickListener(::changeActToSettings)
        new_game.setOnClickListener(::changeActToNewGame)

        var getIntentFromMenu = intent
        var statgame = ""
        if (getIntentFromMenu.hasExtra("statgame"))
            statgame = getIntentFromMenu.getStringExtra("statgame").orEmpty()
        if (statgame == "lose") {
            val builder = AlertDialog.Builder(this@MenuActivity)
            builder.setTitle("Game Info")
            builder.setMessage("You lose.")
            builder.setPositiveButton("Ok") { dialog, which ->
                Log.d(TAG, "You lose. Yes")
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else if (statgame == "won") {
            val builder = AlertDialog.Builder(this@MenuActivity)
            builder.setTitle("Game Info")
            builder.setMessage("You won.")
            builder.setPositiveButton("Ok") { dialog, which ->
                Log.d(TAG, "You won. Yes")
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    companion object {
        private const val TAG = "MenuActivity"
    }
}
