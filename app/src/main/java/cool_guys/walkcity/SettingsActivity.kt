package cool_guys.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cool_guys.walkcity.database.Manager
import cool_guys.walkcity.database.Settings
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {
    lateinit var manager: Manager

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "SettingsActivity onStart()")
        manager = Manager(this@SettingsActivity)
        if (manager.tryingSettings() != 0) manager.downloadSettings()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "SettingsActivity onPause()")
        flag_back = switch_back_button.isChecked
        flag_move = switch_turns.isChecked
        if (flag_back == false) manager.settings.backDialog = 0
        else manager.settings.backDialog = 1
        if (flag_move == false) manager.settings.nextTurnDialog = 0
        else manager.settings.nextTurnDialog = 1
        manager.unloadSettings()
        finish()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "SettingsActivity onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SettingsActivity onDestroy()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "SettingsActivity onResume()")
    }

    fun changeActtoBack(demo: View) {
        val intent = Intent(this@SettingsActivity, MenuActivity::class.java)
        startActivity(intent)
    }

    fun changeActtoBack() {
        val intent = Intent(this@SettingsActivity, MenuActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        Log.d(TAG, "SettingsActivity onCreate()")
        setContentView(R.layout.activity_settings)
        onStart()
        val settings: Settings
        if (manager.tryingSettings() != 0) {
            settings = manager.settings()
            flag_move = settings.nextTurnDialog != 0
            flag_back = settings.backDialog != 0
        } else {
            flag_move = false
            flag_back = false
        }
        switch_back_button.isChecked = flag_back
        switch_turns.isChecked = flag_move
        settings_back.setOnClickListener(::changeActtoBack)
    }

    override fun onBackPressed() {
        changeActtoBack()
    }


    companion object {
        var flag_move = false
        var flag_back = false

        private const val TAG = "SettingsActivity"
    }

}
