package cool_guys.walkcity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.*
import cool_guys.walkcity.database.*
import cool_guys.walkcity.R.layout.activity_inventory
import kotlinx.android.synthetic.main.activity_inventory.*


class InventoryActivity : AppCompatActivity() {
    lateinit var manager : Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        Log.d("FLAG_TAG", "Inventory_Class onCreate()")
        setContentView(activity_inventory)
        manager = Manager(this@InventoryActivity)
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
        /*val swamp2 = findViewById<LinearLayout>(cool_guys.walkcity.R.id.linear_craft)

        var weaponCraftList: ArrayList<CraftFragment> = ArrayList()
        weaponCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.weapon, "Weapon 1", "Damage = 1\nCosts: 2 wood", 2, 0, 0, 0, 1))
        //weaponCraftList[0].manager = manager
        weaponCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.weapon, "Weapon 2", "Damage = 2\nCosts: 3 wood & 1 stone", 3, 1, 0, 0, 2))
        //weaponCraftList[1].manager = manager
        weaponCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.weapon, "Weapon 3", "Damage = 3\nCosts: 1 wood & 2 stone",1, 2, 0, 0, 3))
        //weaponCraftList[2].manager = manager
        weaponCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.weapon, "Weapon 4", "Damage = 4\nCosts: 3 wood & 4 stone", 3, 4, 0, 0, 4))
        //weaponCraftList[3].manager = manager
        weaponCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.weapon, "Weapon 5", "Damage = 3\nCosts: 2 wood & 4 stone", 2, 4, 0, 0, 5))
        //weaponCraftList[4].manager = manager
        weaponCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.weapon, "Weapon 6", "Damage = 8\nCosts: 4 wood & 6 stone\n& 2 iron", 4, 6, 2, 0, 6))
        //weaponCraftList[5].manager = manager

        var protectionCraftList : ArrayList<CraftFragment> = ArrayList()
        protectionCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.protection, "Protection 1", "Defence = 1\nCosts: 5 wood", 5, 0, 0, 1, 1))
        protectionCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.protection, "Protection 2", "Defence = 2\nCosts: 10 wood & 5 stone", 10, 5, 0, 1, 2))
        protectionCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.protection, "Protection 3", "Defence = 3\nCosts: 5 wood & 15 stone",5, 15, 0, 1, 3))
        protectionCraftList.add(CraftFragment.create(cool_guys.walkcity.R.drawable.protection, "Protection 4", "Defence = 4\nCosts: 10 wood & 10 iron", 10, 0, 10, 1, 4))

        weaponCraftList.forEach {
            if(wood >= it.craft_wood && stone >= it.craft_stone && iron >= it.craft_iron) {
                supportFragmentManager.beginTransaction().add(cool_guys.walkcity.R.id.linear_craft, it, "craftList").commit()
            }
        }

        protectionCraftList.forEach {
            if (wood >= it.craft_wood && stone >= it.craft_stone && iron >= it.craft_iron) {
                supportFragmentManager.beginTransaction().add(cool_guys.walkcity.R.id.linear_craft, it, "craftList").commit()
            }
        }*/
        if(wood < 2){
            craft_list_button_1.isClickable=false
            craft_list_button_1.visibility= View.GONE
        }
        if(wood < 3 || stone < 1){
            craft_list_button.isClickable=false
            craft_list_button.visibility= View.GONE
        }
        if(wood < 1 || stone < 2){
            craft_list_button_2.isClickable=false
            craft_list_button_2.visibility= View.GONE
        }
        if(wood < 3 || stone < 3){
            craft_list_button_3.isClickable=false
            craft_list_button_3.visibility= View.GONE
        }
        if(wood < 2 || stone < 4){
            craft_list_button_4.isClickable=false
            craft_list_button_4.visibility= View.GONE
        }
        if(wood < 4 || stone < 6 || iron < 2){
            craft_list_button_5.isClickable=false
            craft_list_button_5.visibility= View.GONE
        }
        if(wood < 5){
            craft_list_button_6.isClickable=false
            craft_list_button_6.visibility= View.GONE
        }
        if(wood < 10 || stone < 5){
            craft_list_button_7.isClickable=false
            craft_list_button_7.visibility= View.GONE
        }
        if(wood < 5 || stone < 15){
            craft_list_button_8.isClickable=false
            craft_list_button_8.visibility= View.GONE
        }
        if(wood < 10 || iron < 10){
            craft_list_button_9.isClickable=false
            craft_list_button_9.visibility= View.GONE
        }

        craft_list_button_1.setOnClickListener{
            manager.craftWeapon(0, 1)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button.setOnClickListener{
            manager.craftWeapon(0, 2)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button_2.setOnClickListener{
            manager.craftWeapon(0, 3)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button_3.setOnClickListener{
            manager.craftWeapon(0, 4)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button_4.setOnClickListener{
            manager.craftWeapon(0, 5)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button_5.setOnClickListener{
            manager.craftWeapon(0, 6)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button_6.setOnClickListener{
            manager.craftProtection(0, 1)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button_7.setOnClickListener{
            manager.craftProtection(0, 2)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button_8.setOnClickListener{
            manager.craftProtection(0, 3)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
        }
        craft_list_button_9.setOnClickListener{
            manager.craftProtection(0, 4)
            manager.unload()
            val intent = Intent(this@InventoryActivity,InventoryActivity::class.java)
            startActivity(intent)
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
        val intent = Intent(this@InventoryActivity,GameMapActivity::class.java)
        intent.putExtra("status", "continue game")
        startActivity(intent)
    }

    override fun onBackPressed() {
        changeActtoBack()
    }
}