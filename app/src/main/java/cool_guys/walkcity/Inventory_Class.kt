package cool_guys.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import cool_guys.walkcity.DataBase.*
import cool_guys.walkcity.R.layout.activity_inventory
import cool_guys.walkcity.R.layout.resource_list_item
import kotlinx.android.synthetic.main.activity_inventory.*
import org.jetbrains.anko.intentFor


class Inventory_Class : AppCompatActivity() {
    lateinit var manager : Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FLAG_TAG", "Inventory_Class onCreate()")
        setContentView(activity_inventory)
        manager = Manager(this@Inventory_Class)
        manager.download()
        var city : CityData
        var resource : ItemResource
        var protection : ItemProtection
        var weapon : ItemWeapon
        city = manager.giveHomeCity()
        resource = manager.giveHomeResource()
        protection = manager.giveHomeProtection()
        weapon = manager.giveHomeWeapon()
 //       setContentView(resource_bar)
        val resourceList : ArrayList<ResourceBlock>  = ArrayList()
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.population, "Population", "Line 2"))
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.wood, "Wood", "Line 4"))
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.iron, "Iron", "Line 6"))
        val swamp = findViewById<LinearLayout>(R.id.inventory)
        resourceList.forEach {
            val inflate = layoutInflater.inflate(R.layout.resource_list_item, null)
            var img = inflate.findViewById<ImageView>(R.id.imageView)
            var txt1 = inflate.findViewById<TextView>(R.id.textView1)
            var txt2 = inflate.findViewById<TextView>(R.id.textView2)

            var draw  = ContextCompat.getDrawable(this, it.imageResource)
            img.setImageDrawable(draw)

            txt1.setText(it.text1)
            txt2.setText(it.text2)
            swamp.addView(inflate)
        }


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