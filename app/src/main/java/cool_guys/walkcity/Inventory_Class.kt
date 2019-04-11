package cool_guys.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.*
import cool_guys.walkcity.DataBase.*
import cool_guys.walkcity.R.layout.activity_inventory


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
        var popul = city.people
        var wood = resource.wood
        var iron = resource.iron
        var stone = resource.stone
        var food = resource.food
        var fuel = resource.fuel

        val resourceList : ArrayList<ResourceBlock>  = ArrayList()
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.population, "Population ", popul.toString()))
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.wood, "Wood ", wood.toString()))
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.iron, "Iron ", iron.toString()))
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.stone, "Stone ", stone.toString()))
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.food, "Food ", food.toString()))
        resourceList.add(ResourceBlock(cool_guys.walkcity.R.drawable.water, "Fuel ", fuel.toString()))

        val swamp = findViewById<LinearLayout>(cool_guys.walkcity.R.id.inventory)
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

        val swamp2 = findViewById<ScrollView>(cool_guys.walkcity.R.id.craftScroll)

        var button : Button = findViewById<Button>(cool_guys.walkcity.R.id.craft_list_button)

        var craftList : ArrayList<CraftBlock> = ArrayList()
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 1", "Damage = 1\nCosts: 2 wood", button))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 2", "Damage = 2\nCosts: 3 wood & 1 stone", button))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 3", "Damage = 3\nCosts: 1 wood & 2 stone", button))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 4", "Damage = 4\nCosts: 3 wood & 4 stone", button))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 5", "Damage = 3\nCosts: 2 wood & 4 stone", button))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 6", "Damage = 8\nCosts: 4 wood & 6 stone & 2 iron", button))

        craftList.forEach {
            val inflate = layoutInflater.inflate(R.layout.craft_list_item, null)
            var img = inflate.findViewById<ImageView>(R.id.imageView)
            var txt1 = inflate.findViewById<TextView>(R.id.craftTextView1)
            var txt2 = inflate.findViewById<TextView>(R.id.craftTextView2)

            var draw  = ContextCompat.getDrawable(this, it.imageResource)
            img.setImageDrawable(draw)

            txt1.setText(it.text1)
            txt2.setText(it.text2)

            swamp2.addView(inflate)
        }

        craftList[0].but.setOnClickListener {
            //craft weapon 1 here
        }
        craftList[1].but.setOnClickListener {
            //craft weapon 2 here
        }
        craftList[2].but.setOnClickListener {
            //craft weapon 3 here
        }
        craftList[3].but.setOnClickListener {
            //craft weapon 4 here
        }
        craftList[4].but.setOnClickListener {
            //craft weapon 5 here
        }
        craftList[5].but.setOnClickListener {
            //craft weapon 6 here
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