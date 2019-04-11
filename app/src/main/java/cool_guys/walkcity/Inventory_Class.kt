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

        val resourceList1 : ArrayList<ResourceBlock>  = ArrayList()
        resourceList1.add(ResourceBlock(cool_guys.walkcity.R.drawable.stone, "Stone ", stone.toString()))
        resourceList1.add(ResourceBlock(cool_guys.walkcity.R.drawable.wood, "Wood ", wood.toString()))
        resourceList1.add(ResourceBlock(cool_guys.walkcity.R.drawable.iron, "Iron ", iron.toString()))
        val resourceList2 : ArrayList<ResourceBlock>  = ArrayList()
        resourceList2.add(ResourceBlock(cool_guys.walkcity.R.drawable.population, "Population ", popul.toString()))
        resourceList2.add(ResourceBlock(cool_guys.walkcity.R.drawable.food, "Food ", food.toString()))
        resourceList2.add(ResourceBlock(cool_guys.walkcity.R.drawable.water, "Fuel ", fuel.toString()))

        val swamp1 = findViewById<LinearLayout>(cool_guys.walkcity.R.id.linearRes1)
        resourceList1.forEach {
            val inflate = layoutInflater.inflate(R.layout.resource_list_item, null)
            var img = inflate.findViewById<ImageView>(R.id.imageView)
            var txt1 = inflate.findViewById<TextView>(R.id.textView1)
            var txt2 = inflate.findViewById<TextView>(R.id.textView2)

            var draw  = ContextCompat.getDrawable(this, it.imageResource)
            img.setImageDrawable(draw)

            txt1.setText(it.text1)
            txt2.setText(it.text2)
            swamp1.addView(inflate)
        }
        val swamp12 = findViewById<LinearLayout>(cool_guys.walkcity.R.id.linearRes2)
        resourceList2.forEach {
            val inflate = layoutInflater.inflate(R.layout.resource_list_item, null)
            var img = inflate.findViewById<ImageView>(R.id.imageView)
            var txt1 = inflate.findViewById<TextView>(R.id.textView1)
            var txt2 = inflate.findViewById<TextView>(R.id.textView2)

            var draw  = ContextCompat.getDrawable(this, it.imageResource)
            img.setImageDrawable(draw)

            txt1.setText(it.text1)
            txt2.setText(it.text2)
            swamp12.addView(inflate)
        }
        val swamp2 = findViewById<LinearLayout>(cool_guys.walkcity.R.id.linear_craft)


        var craftList : ArrayList<CraftBlock> = ArrayList()
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 1", "Damage = 1\nCosts: 2 wood", 2, 0, 0))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 2", "Damage = 2\nCosts: 3 wood & 1 stone", 3, 1, 0))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 3", "Damage = 3\nCosts: 1 wood & 2 stone",1, 2, 0))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 4", "Damage = 4\nCosts: 3 wood & 4 stone", 3, 4, 0))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 5", "Damage = 3\nCosts: 2 wood & 4 stone", 2, 4, 0))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.stone, "Weapon 6", "Damage = 8\nCosts: 4 wood & 6 stone\n& 2 iron", 4, 6, 2))

        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.iron, "Protection 1", "Defence = 1\nCosts: 5 wood", 5, 0, 0))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.iron, "Protection 2", "Defence = 2\nCosts: 10 wood & 5 stone", 10, 5, 0))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.iron, "Protection 3", "Defence = 3\nCosts: 5 wood & 15 stone",5, 15, 0))
        craftList.add(CraftBlock(cool_guys.walkcity.R.drawable.iron, "Protection 4", "Defence = 4\nCosts: 10 wood & 10 iron", 10, 0, 10))

        craftList.forEach {
            if(wood >= it.craft_wood && stone >= it.craft_stone && iron >= it.craft_iron) {
                val inflate = layoutInflater.inflate(R.layout.craft_list_item, null)
                var img = inflate.findViewById<ImageView>(R.id.imageView)
                var txt1 = inflate.findViewById<TextView>(R.id.craftTextView1)
                var txt2 = inflate.findViewById<TextView>(R.id.craftTextView2)

                var draw = ContextCompat.getDrawable(this, it.imageResource)
                img.setImageDrawable(draw)

                txt1.setText(it.text1)
                txt2.setText(it.text2)

                swamp2.addView(inflate)
            }
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