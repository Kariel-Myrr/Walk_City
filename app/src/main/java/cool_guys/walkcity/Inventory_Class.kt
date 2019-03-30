package cool_guys.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cool_guys.walkcity.DataBase.*
import cool_guys.walkcity.R.layout.activity_inventory
import kotlinx.android.synthetic.main.activity_inventory.*


class Inventory_Class : AppCompatActivity() {
    lateinit var manager : Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FLAG_TAG", "Inventory_Class onCreate()")
        setContentView(activity_inventory)
        manager = Manager(this@Inventory_Class)
        var city : CityData
        var resource : ItemResource
        var protection : ItemProtection
        var weapon : ItemWeapon
        city = manager.giveHomeCity()
        resource = manager.giveHomeResource()
        protection = manager.giveHomeProtection()
        weapon = manager.giveHomeWeapon()
        setContentView(resource_bar)
        val ResourceList : ArrayList<ResourceBlock>  = ArrayList()
        ResourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.population, "Population", "Line 2"))
        ResourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.wood, "Wood", "Line 4"))
        ResourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.iron, "Iron", "Line 6"))




    }


    override fun onStart() {
        super.onStart()
        Log.d("FLAG_TAG", "Inventory_Class onStart()")
        manager.download()

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