package cool_guys.walkcity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cool_guys.walkcity.database.Manager
import cool_guys.walkcity.database.Settings
import kotlinx.android.synthetic.main.activity_game_map.*

class GameMapActivity : AppCompatActivity() {

    lateinit var manager: Manager

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Game_Map_Class onStart()")
        val getIntentFromMenu = intent
        var status = ""
        if (getIntentFromMenu.hasExtra("status"))
            status = getIntentFromMenu.getStringExtra("status").orEmpty()
        if (status == "new game") {
            manager.init()
            val builder = AlertDialog.Builder(this@GameMapActivity)
            builder.setTitle("Game Guide")
            builder.setMessage(
                "Вы город, он выделен белыми рамками.\n" +
                        "На поле есть другие города, которые к вам враждебны.\n" +
                        "За ход можно перейти на соседнюю клету или атаковать соседний город с помощью одного клика.\n" +
                        "С помощью длинного клика можно узнать информацию о городе или о местности.\n" +
                        "Ресурсы можно собирать на новой местности или при уничтожении других городов.\n" +
                        "В инвентаре, если достаточно ресурсов, можно сделать оружие или броню.\n" +
                        "Предметов оружия можно сделать не более 10 штук.\n" +
                        "1 очко брони защищает от 1 очка атаки.\n" +
                        "Улучшение города достигается при увеличении популяции:\n" +
                        "0 - 5 человек town.\n" +
                        "6 - 20 человек village +5 очков здоровья.\n" +
                        "21 - 50 человек city +10 очков здоровья.\n" +
                        "от 50 и более человек metropolis +20 очков здоровья.\n" +
                        "Помните, что каждое перемещение тратит 5 очков топлива и каждый ход отнимается столькоже очков еды, сколько населения в городе, при отсутствии еды каждый ход умирает 1 человек.\n" +
                        "Условие выйгрыша: уничтожте столицу в нижнем углу."
            )
            builder.setPositiveButton("Ok") { dialog, which ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else manager.download()
        ViewMap.map = manager.tile
        ViewMap.CityArr = manager.city
        ViewMap.manager = manager
        ViewMap.drawMatr()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Game_Map_Class onPause()")
        manager.unload()
        finish()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Game_Map_Class onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Game_Map_Class onDestroy()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Game_Map_Class onResume()")
    }

    private fun changeActToBack() {
        val intent = Intent(this@GameMapActivity, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun nextTurn() {
        Toast.makeText(applicationContext, "Turn made, game saved.", Toast.LENGTH_SHORT).show()
        val stat = manager.check()
        if (stat == 1) {
            //manager.delCity()
            val intent = Intent(this@GameMapActivity, MenuActivity::class.java)
            intent.putExtra("statgame", "lose")
            startActivity(intent)
        } else if (stat == 2) {
            //manager.delCity()
            val intent = Intent(this@GameMapActivity, MenuActivity::class.java)
            intent.putExtra("statgame", "won")
            startActivity(intent)
        } else {
            manager.nextTurn()
            val intent = Intent(this@GameMapActivity, GameMapActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        Log.d(TAG, "Game_Map_Class onCreate()")
        setContentView(R.layout.activity_game_map)
        manager = Manager(this@GameMapActivity)
        var settings: Settings
        if (manager.tryingSettings() != 0) {
            manager.downloadSettings()
            settings = manager.settings()
            Log.d(
                TAG,
                "backDialog = ${settings.backDialog} nextTurnDialog = ${settings.nextTurnDialog}"
            )
            flag_move = settings.nextTurnDialog != 0
            flag_back = settings.backDialog != 0
            Log.d(TAG, "flag_back = $flag_back flag_move = $flag_move")
        } else {
            flag_move = false
            flag_back = false
        }
        inv.setOnClickListener {
            manager.unload()
            val intent = Intent(this@GameMapActivity, InventoryActivity::class.java)
            startActivity(intent)
        }
        nt.setOnClickListener {
            if (flag_move) nextTurn()
            else {
                val builder = AlertDialog.Builder(this@GameMapActivity)
                builder.setTitle("Confirm Turn")
                builder.setMessage("Are you sure you want to confirm Turn?")
                builder.setPositiveButton("YES") { dialog, which ->
                    nextTurn()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(applicationContext, "Turn not confirmed", Toast.LENGTH_SHORT)
                        .show()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun onBackPressed() {
        if (flag_back) changeActToBack()
        else {
            val builder = AlertDialog.Builder(this@GameMapActivity)
            builder.setTitle("Exit")
            builder.setMessage("Are you sure you want to Exit game?\nAll unsaved progress will be lost.")
            builder.setPositiveButton("YES") { dialog, which ->
                changeActToBack()
            }
            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(applicationContext, "Game continued.", Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    companion object {
        var flag_move = false
        var flag_back = false
        private const val TAG = "GameMapActivity"
    }
}
