package cool_guys.walkcity.DataBase

import android.content.ContentValues
import android.content.Context
import android.util.Log
import java.util.*

class Manager(context: Context){

    var city : MutableList<CityData> = mutableListOf()
    private var inventory : MutableList<InventoryData> = mutableListOf()
    private var protection : MutableList<ItemProtection> = mutableListOf()
    private var resource : MutableList<ItemResource> = mutableListOf()
    private var weapon : MutableList<ItemWeapon> = mutableListOf()
    private var dataBase = DBHandler(context)
    private var map = Map()
    var tile : MutableList<MutableList<Tile>> = mutableListOf()
    private var resourceTile: MutableList<MutableList<ItemResource>> = mutableListOf()
    private val countCity = 6
    var settings = Settings()

    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

    private fun initTile(i: Int, e: Int){
        val stat = (1..6).random()
        //var numNull = (0..0).random()
        //var numLow = (0..5).random()
        //var numSmall = (0..10).random()
        //var numMedium = (5..10).random()
        //var numHigh = (5..15).random()
        when(stat) {
            1 -> {
                tile[i][e].type = "field"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..5).random()
                resourceTile[i][e].stone = (0..5).random()
                resourceTile[i][e].iron = (0..0).random()
                resourceTile[i][e].food = (5..15).random()
                resourceTile[i][e].fuel = (0..10).random()
                resourceTile[i][e].people = (0..5).random()
            }
            2 -> {
                tile[i][e].type = "hill"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (5..15).random()
                resourceTile[i][e].stone = (0..0).random()
                resourceTile[i][e].iron = (0..0).random()
                resourceTile[i][e].food = (0..10).random()
                resourceTile[i][e].fuel = (0..5).random()
                resourceTile[i][e].people = (0..5).random()
            }
            3 -> {
                tile[i][e].type = "lake"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..0).random()
                resourceTile[i][e].stone = (0..10).random()
                resourceTile[i][e].iron = (0..5).random()
                resourceTile[i][e].food = (5..10).random()
                resourceTile[i][e].fuel = (5..10).random()
                resourceTile[i][e].people = (0..5).random()
            }
            4 -> {
                tile[i][e].type = "sea"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..0).random()
                resourceTile[i][e].stone = (0..10).random()
                resourceTile[i][e].iron = (0..5).random()
                resourceTile[i][e].food = (5..10).random()
                resourceTile[i][e].fuel = (5..15).random()
                resourceTile[i][e].people = (0..5).random()
            }
            5 -> {
                tile[i][e].type = "desert"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..5).random()
                resourceTile[i][e].stone = (0..5).random()
                resourceTile[i][e].iron = (0..10).random()
                resourceTile[i][e].food = (0..5).random()
                resourceTile[i][e].fuel = (0..0).random()
                resourceTile[i][e].people = (0..5).random()
            }
            else -> {
                tile[i][e].type = "forest"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..0).random()
                resourceTile[i][e].stone = (5..10).random()
                resourceTile[i][e].iron = (5..15).random()
                resourceTile[i][e].food = (0..5).random()
                resourceTile[i][e].fuel = (0..5).random()
                resourceTile[i][e].people = (0..5).random()
            }
        }
    }

    fun initTile(i: Int, e: Int, type: String, tree: Int, stone: Int, iron: Int, food: Int, fuel: Int){
        tile[i][e].type = type
        resourceTile[i][e].wood = tree
        resourceTile[i][e].stone = stone
        resourceTile[i][e].iron = iron
        resourceTile[i][e].food = food
        resourceTile[i][e].fuel = fuel
    }

    private fun initMap(){
        map.x = 6
        map.y = 6
        for(i in 0 until map.y){
            map.idTile.add(mutableListOf())
            resourceTile.add(mutableListOf())
            tile.add(mutableListOf())
            for(e in 0 until map.x){
                map.idTile[i].add(i * map.y + e)
                val idTile = map.idTile[i][e]
                Log.d("FLAG_TAG", "map[$i][$e]: idTile = $idTile")
                resourceTile[i].add(ItemResource())
                tile[i].add(Tile())
                initTile(i, e)
                val type = tile[i][e].type
                val idItemResource = tile[i][e].idItemResource
                Log.d("FLAG_TAG", "tile[$i][$e]: type = $type idItemResource = $idItemResource")
                val tree = resourceTile[i][e].wood
                val stone = resourceTile[i][e].stone
                val iron = resourceTile[i][e].iron
                val food = resourceTile[i][e].food
                val fuel = resourceTile[i][e].fuel
                val people = resourceTile[i][e].people
                Log.d("FLAG_TAG", "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food fuel = $fuel people = $people")
            }
        }

    }

    private fun initCity(id: Int){
        val tmpCity = CityData()
        if(id == 0)tmpCity.name = "my"
        else if(id == 1)tmpCity.name = "drisch"
        else if(id == 2)tmpCity.name = "loh"
        else if(id == 3)tmpCity.name = "lazy"
        else if(id == 4)tmpCity.name = "dummy"
        else if(id == 5)tmpCity.name = "stupid"
        tmpCity.hp = 10
        tmpCity.type = "town"
        tmpCity.active = 1
        tmpCity.people = 1
        tmpCity.damage = 0
        tmpCity.protection = 0
        tmpCity.idInventory = id
        tmpCity.x = id
        tmpCity.y = id
        Log.d("FLAG_TAG", "city: name = ${tmpCity.name} hp = ${tmpCity.hp} type = ${tmpCity.type} active = ${tmpCity.active} people = ${tmpCity.people} damage = ${tmpCity.damage} protection = ${tmpCity.protection} idInventory = ${tmpCity.idInventory} x = ${tmpCity.x} y = ${tmpCity.y}")
        city.add(tmpCity)
    }

    fun initCity(id: Int, name: String, hp: Int, type: String, active: Int, people: Int, damage: Int, protection: Int, x: Int, y: Int){
        city[id].name = name
        city[id].hp = hp
        city[id].type = type
        city[id].active = active
        city[id].people = people
        city[id].damage = damage
        city[id].protection = protection
        city[id].x = x
        city[id].y = y
    }

    private fun initInventory(id: Int){
        val tmpInventory = InventoryData()
        tmpInventory.idItemProtection = id
        tmpInventory.idItemResource = id
        tmpInventory.idItemWeapon = id
        Log.d("FLAG_TAG", "inventory: idItemProtection = ${tmpInventory.idItemProtection} idItemResource = ${tmpInventory.idItemResource} idItemWeapon = ${tmpInventory.idItemWeapon}")
        inventory.add(tmpInventory)
    }

    private fun initProtection(){
        val tmpProtection = ItemProtection()
        tmpProtection.slot = 0
        Log.d("FLAG_TAG", "protection: slot = ${tmpProtection.slot}")
        protection.add(tmpProtection)
    }

    fun initProtection(id: Int, slot: Int){
        protection[id].slot = slot
    }

    private fun initResource(){
        val tmpResource = ItemResource()
        tmpResource.wood = 0
        tmpResource.stone = 0
        tmpResource.iron = 0
        tmpResource.food = 5
        tmpResource.fuel = 10
        tmpResource.people = 1
        Log.d("FLAG_TAG", "resource: wood = ${tmpResource.wood} stone = ${tmpResource.stone} iron = ${tmpResource.iron} food = ${tmpResource.food} fuel = ${tmpResource.fuel}")
        resource.add(tmpResource)
    }

    fun initResource(id: Int, tree: Int, stone: Int, iron: Int, food: Int, fuel: Int){
        resource[id].wood = 0
        resource[id].stone = 0
        resource[id].iron = 0
        resource[id].food = 2
        resource[id].fuel = 10
    }

    private fun initWeapon(){
        val tmpWeapon = ItemWeapon()
        tmpWeapon.storage = mutableListOf()
        tmpWeapon.slots = mutableListOf()
        weapon.add(tmpWeapon)
    }

    fun initWeapon(id: Int, storage: MutableList<Int>, slots: MutableList<Int>){
        weapon[id].storage = storage
        weapon[id].slots = slots
    }

    fun allocation(){
        if(city[0].x + 1 < map.x){
            if(tile[city[0].x + 1][city[0].y].busy == false)tile[city[0].x + 1][city[0].y].allocation = 1
            else tile[city[0].x + 1][city[0].y].allocation = 2
        }
        if(city[0].x - 1 >= 0){
            if(tile[city[0].x - 1][city[0].y].busy == false)tile[city[0].x - 1][city[0].y].allocation = 1
            else tile[city[0].x - 1][city[0].y].allocation = 2
        }
        if(city[0].y + 1 < map.y){
            if(tile[city[0].x][city[0].y + 1].busy == false)tile[city[0].x][city[0].y + 1].allocation = 1
            else tile[city[0].x][city[0].y + 1].allocation = 2
        }
        if(city[0].y - 1 >= 0){
            if(tile[city[0].x][city[0].y - 1].busy == false)tile[city[0].x][city[0].y - 1].allocation = 1
            else tile[city[0].x][city[0].y - 1].allocation = 2
        }
    }

    fun init(){
        Log.d("FLAG_TAG", "INIT MANAGER")
        for(i in 0 until countCity)initCity(i)
        for(i in 0 until countCity)initInventory(i)
        for(i in 0 until countCity)initProtection()
        for(i in 0 until countCity)initResource()
        for(i in 0 until countCity)initWeapon()
        initMap()
        for(i in 0..5){
            tile[city[i].x][city[i].y].city = city[i]
            tile[city[i].x][city[i].y].busy = true
            Log.d("FLAG_TAG", "tile[city[i].x][city[i].y].city.id = ${tile[city[i].x][city[i].y].city.id}")
        }
        allocation()
        Log.d("FLAG_TAG", "COMLETE INIT MANAGER")
    }

    private fun downloadCity(){
        val cityList = dataBase.cityList("%")
        if(cityList.size != 0) {
            city = cityList
            for(i in 0 until countCity)Log.d("FLAG_TAG", "city: name = ${city[i].name} hp = ${city[i].hp} type = ${city[i].type} active = ${city[i].active} people = ${city[i].people} damage = ${city[i].damage} protection = ${city[i].protection} idInventory = ${city[i].idInventory} x = ${city[i].x} y = ${city[i].y}")
        }
        else Log.d("FLAG_TAG", "ERROR: cityList.size = ${cityList.size}")
    }

    private fun downloadCity(id: Int){
        val cityList = dataBase.cityList("%")
        if(cityList.size != 0) {
            city[id] = cityList[id]
            Log.d("FLAG_TAG", "city: name = ${city[id].name} hp = ${city[id].hp} type = ${city[id].type} active = ${city[id].active} people = ${city[id].people} damage = ${city[id].damage} protection = ${city[id].protection} idInventory = ${city[id].idInventory} x = ${city[id].x} y = ${city[id].y}")
        }
        else Log.d("FLAG_TAG", "ERROR: cityList.size = ${cityList.size}")
    }

    private fun downloadInventory(){
        val inventoryList = dataBase.inventoryList("%")
        if(inventoryList.size != 0) {
            inventory = inventoryList
            for(i in 0 until countCity)Log.d("FLAG_TAG", "inventory: idItemProtection = ${inventory[i].idItemProtection} idItemResource = ${inventory[i].idItemResource} idItemWeapon = ${inventory[i].idItemWeapon}")
        }
        else Log.d("FLAG_TAG", "ERROR: invenoryList.size = ${inventoryList.size}")
    }

    private fun downloadInventory(id: Int){
        val inventoryList = dataBase.inventoryList("%")
        if(inventoryList.size != 0) {
            inventory[id] = inventoryList[id]
            Log.d("FLAG_TAG", "inventory: idItemProtection = ${inventory[id].idItemProtection} idItemResource = ${inventory[id].idItemResource} idItemWeapon = ${inventory[id].idItemWeapon}")
        }
        else Log.d("FLAG_TAG", "ERROR: invenoryList.size = ${inventoryList.size}")
    }

    private fun downloadProtection(){
        val protectionList = dataBase.itemProtectionList("%")
        if(protectionList.size != 0) {
            protection = protectionList
            for(i in 0 until countCity)Log.d("FLAG_TAG", "protection: slot = ${protection[i].slot}")
        }
        else Log.d("FLAG_TAG", "ERROR: protectionList.size = ${protectionList.size}")
    }

    private fun downloadProtection(id: Int){
        val protectionList = dataBase.itemProtectionList("%")
        if(protectionList.size != 0) {
            protection[id] = protectionList[id]
            Log.d("FLAG_TAG", "protection: slot = ${protection[id].slot}")
        }
        else Log.d("FLAG_TAG", "ERROR: protectionList.size = ${protectionList.size}")
    }

    private fun downloadResource(){
        val resourceList = dataBase.itemResourceList("%")
        if(resourceList.size != 0) {
            for(i in 0 until countCity){
                resource.add(resourceList[i])
                Log.d("FLAG_TAG", "resource: wood = ${resource[i].wood} stone = ${resource[i].stone} iron = ${resource[i].iron} food = ${resource[i].food} fuel = ${resource[i].fuel} people = ${resource[i].people}")
            }
        }
        else Log.d("FLAG_TAG", "ERROR: resurceList.size = ${resourceList.size}")
    }

    private fun downloadResource(id: Int){
        val resourceList = dataBase.itemResourceList("%")
        if(resourceList.size != 0) {
            resource[id] = resourceList[id]
            Log.d("FLAG_TAG", "resource: wood = ${resource[id].wood} stone = ${resource[id].stone} iron = ${resource[id].iron} food = ${resource[id].food} fuel = ${resource[id].fuel}")
        }
        else Log.d("FLAG_TAG", "ERROR: resurceList.size = ${resourceList.size}")
    }

    private fun downloadWeapon(){
        //Log.d("FLAG_TAG", "downloadWeapon() test 1")
        val weaponList = dataBase.itemWeaponList("%")
        if(weaponList.size != 0) {
            //Log.d("FLAG_TAG", "downloadWeapon() test 2")
            weapon = weaponList
            //Log.d("FLAG_TAG", "downloadWeapon() test 3")
            for(i in 0 until countCity)Log.d("FLAG_TAG", "weapon: weapon.slots.size = ${weapon[i].slots.size} weapn.storage.size = ${weapon[i].storage.size}")
            //Log.d("FLAG_TAG", "downloadWeapon() test 4")
        }
        else Log.d("FLAG_TAG", "ERROR: weaponList.size = ${weaponList.size}")
        //Log.d("FLAG_TAG", "downloadWeapon() test 5")
    }

    private fun downloadWeapon(id: Int){
        val weaponList = dataBase.itemWeaponList("%")
        if(weaponList.size != 0) {
            weapon[id] = weaponList[id]
            Log.d("FLAG_TAG", "weapon: weapon.slots.size = ${weapon[id].slots.size} weapn.storage.size = ${weapon[id].storage.size}")
        }
        else Log.d("FLAG_TAG", "ERROR: weaponList.size = ${weaponList.size}")
    }

    private fun downloadMap(){
        val tmpMap = dataBase.mapInfo("%")
        if(tmpMap.size != 0) {
            map = tmpMap[0]
            Log.d("FLAG_TAG", "map: map.x = ${map.x} map.y = ${map.y}")
        }
        else Log.d("FLAG_TAG", "ERROR: _map.size = ${tmpMap.size}")
    }

    private fun downloadTile(){
        val tileList = dataBase.tileList("%")
        if(tileList.size != 0) {
            for (i in 0 until map.y) {
                tile.add(mutableListOf())

                for (e in 0 until map.x) {
                    tile[i].add(tileList[i * map.y + e])
                    val type = tile[i][e].type
                    val idItemResource = tile[i][e].idItemResource
                    Log.d("FLAG_TAG", "tile[$i][$e]: type = $type idItemResource = $idItemResource")
                }
            }
        }
        else Log.d("FLAG_TAG", "ERROR: tileList.size = ${tileList.size}")
    }

    fun downloadTile(i: Int, e: Int){
        val tileList = dataBase.tileList("%")
        if(tileList.size != 0) {
            tile[i][e] = tileList[i * map.y + e]
            val type = tile[i][e].type
            val idItemResource = tile[i][e].idItemResource
            Log.d("FLAG_TAG", "tile[$i][$e]: type = $type idItemResource = $idItemResource")
        }
        else Log.d("FLAG_TAG", "ERROR: tileList.size = ${tileList.size}")
    }

    private fun downloadResourceTile(){
        val resourceTileList = dataBase.itemResourceList("%")
        if(resourceTileList.size != 0){
            for(i in 0 until map.y){
                resourceTile.add(mutableListOf())
                for(e in 0 until map.x){
                    resourceTile[i].add(resourceTileList[tile[i][e].idItemResource])
                    val tree = resourceTile[i][e].wood
                    val stone = resourceTile[i][e].stone
                    val iron = resourceTile[i][e].iron
                    val food = resourceTile[i][e].food
                    val fuel = resourceTile[i][e].fuel
                    val people = resourceTile[i][e].people
                    Log.d("FLAG_TAG", "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food fuel = $fuel people = $people")
                }
            }
        }
        else Log.d("FLAG_TAG", "ERROR: resourceTileList.size = ${resourceTileList.size}")
    }

    fun downloadResourceTile(i: Int, e: Int){
        val resourceTileList = dataBase.itemResourceList("%")
        if(resourceTileList.size != 0){
            resourceTile[i][e] = resourceTileList[tile[i][e].idItemResource]
            val tree = resourceTile[i][e].wood
            val stone = resourceTile[i][e].stone
            val iron = resourceTile[i][e].iron
            val food = resourceTile[i][e].food
            val fuel = resourceTile[i][e].fuel
            Log.d("FLAG_TAG", "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food fuel = $fuel")
        }
        else Log.d("FLAG_TAG", "ERROR: resourceTileList.size = ${resourceTileList.size}")
    }

    fun download(){
        Log.d("FLAG_TAG", "DOWNLOAD DB")
        downloadCity()
        downloadInventory()
        downloadProtection()
        downloadResource()
        downloadWeapon()
        downloadMap()
        downloadTile()
        downloadResourceTile()
        for(i in 0..5){
            if(city[i].active == 0)continue
            tile[city[i].x][city[i].y].city = city[i]
            tile[city[i].x][city[i].y].city.id -= 1
            tile[city[i].x][city[i].y].busy = true
            Log.d("FLAG_TAG", "tile[city[i].x][city[i].y].city.id = ${tile[city[i].x][city[i].y].city.id}")

        }
        for(i in 0..5){
            for(e in 0..5)Log.d("FLAG_TAG", "tile[$i][$e].busy = ${tile[i][e].busy}")
        }
        allocation()
        Log.d("FLAG_TAG", "COMLETE DOWNLOAD DB")
    }

    fun unload(){
        Log.d("FLAG_TAG", "UNLOAD DB")
        for(i in 0 until countCity)unloadCity(i)
        Log.d("FLAG_TAG", "city unload")
        for(i in 0 until countCity)unloadInvenory(i)
        Log.d("FLAG_TAG", "inventory unload")
        for(i in 0 until countCity)unloadProtection(i)
        Log.d("FLAG_TAG", "protection unload")
        for(i in 0 until countCity)unloadResource(i)
        Log.d("FLAG_TAG", "resource unload")
        for(i in 0 until countCity)unloadWeapon(i)
        Log.d("FLAG_TAG", "weapon unload")
        unloadMap()
        Log.d("FLAG_TAG", "map unload")
        unloadTile()
        Log.d("FLAG_TAG", "tile unload")
        unloadResourceTile()
        Log.d("FLAG_TAG", "resource tile unload")
        Log.d("FLAG_TAG", "COMPLETE UNLOAD DB")
    }

    private fun unloadCity(id: Int){
        //Log.d("FLAG_TAG", "unload city $id")
        val values = ContentValues()
        values.put(DBHandler.name, city[id].name)
        values.put(DBHandler.hp, city[id].hp)
        values.put(DBHandler.type, city[id].type)
        values.put(DBHandler.active, city[id].active)
        values.put(DBHandler.people, city[id].people)
        values.put(DBHandler.damage, city[id].damage)
        values.put(DBHandler.protection, city[id].protection)
        values.put(DBHandler.idInventory, city[id].idInventory)
        values.put(DBHandler.x, city[id].x)
        values.put(DBHandler.y, city[id].y)
        //Log.d("FLAG_TAG", "test unload city 1")
        if(tryingCity() < countCity)dataBase.addCity(values)
        else dataBase.updateCity(values, id + 1)
        //Log.d("FLAG_TAG", "test unload city 2")
    }

    private  fun unloadInvenory(id: Int){
        val values = ContentValues()
        values.put(DBHandler.idItemResource, inventory[id].idItemResource)
        values.put(DBHandler.idItemProtection, inventory[id].idItemProtection)
        values.put(DBHandler.idItemWeapon, inventory[id].idItemWeapon)
        if(tryingInventory() < countCity)dataBase.addInventoryData(values)
        else dataBase.updateInventoryData(values, id + 1)
    }

    private fun unloadProtection(id: Int){
        val values = ContentValues()
        values.put(DBHandler.slot, protection[id].slot)
        if(tryingProtection() < countCity)dataBase.addItemProtection(values)
        else dataBase.updateItemProtection(values, id + 1)
    }

    private fun unloadResource(id: Int){
        val values = ContentValues()
        values.put(DBHandler.wood, resource[id].wood)
        values.put(DBHandler.stone, resource[id].stone)
        values.put(DBHandler.iron, resource[id].iron)
        values.put(DBHandler.food, resource[id].food)
        values.put(DBHandler.fuel, resource[id].fuel)
        values.put(DBHandler.people, resource[id].people)
        if(tryingResource() < countCity)dataBase.addItemResource(values)
        else dataBase.updateItemResource(values, id + 1)
    }

    private fun unloadWeapon(id: Int){
        //Log.d("FLAG_TAG", "unloadWeapon() test 1")
        val values = ContentValues()
        var slotsString = ""
        for(i in 0 until weapon[id].slots.size)slotsString += weapon[id].slots[i].toString() + " "
        values.put(DBHandler.slots, slotsString)
        var storageString = ""
        for (i in 0 until weapon[id].storage.size)storageString += weapon[id].storage[i].toString() + " "
        values.put(DBHandler.storage, storageString)
        //Log.d("FLAG_TAG", "unloadWeapon() test 2")
        if(tryingWeapon() < countCity)dataBase.addItemWeapon(values)
        else dataBase.updateItemWeapon(values, id + 1)
        //Log.d("FLAG_TAG", "unloadWeapon() test 3")
    }

    private fun unloadMap(){
        //Log.d("FLAG_TAG", "x = ${map.x} y = ${map.y}")
        val values = ContentValues()
        values.put(DBHandler.x, map.x)
        values.put(DBHandler.y, map.y)
        var mapString = ""
        //Log.d("FLAG_TAG", "Test map 1")
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                //Log.d("FLAG_TAG", "i = $i e = $e")
                mapString += map.idTile[i][e].toString() + " "
                //Log.d("FLAG_TAG", "Ok")
            }
        }
        //Log.d("FLAG_TAG", "Test map 2")
        values.put(DBHandler.idTile, mapString)
        if(tryingMap() == 0)dataBase.addMap(values)
        else dataBase.updateMap(values, 1)
        //Log.d("FLAG_TAG", "Test map 3")
    }

    private fun unloadTile(){
        var values : ContentValues
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                values = ContentValues()
                values.put(DBHandler.type, tile[i][e].type)
                values.put(DBHandler.idItemResource, tile[i][e].idItemResource)
                if(tryingTile() < map.y * map.x)dataBase.addTile(values)
                else dataBase.updateTile(values, i * map.y + e + 1)
            }
        }
    }

    private fun unloadResourceTile(){
        var values : ContentValues
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                values = ContentValues()
                values.put(DBHandler.wood, resourceTile[i][e].wood)
                values.put(DBHandler.stone, resourceTile[i][e].stone)
                values.put(DBHandler.iron, resourceTile[i][e].iron)
                values.put(DBHandler.food, resourceTile[i][e].food)
                values.put(DBHandler.fuel, resourceTile[i][e].fuel)
                values.put(DBHandler.people, resourceTile[i][e].people)
                if(tryingResource() < map.y * map.x + countCity)dataBase.addItemResource(values)
                else dataBase.updateItemResource(values, i * map.y + e + 1 + countCity)
            }
        }
    }

    fun unloadSettings(){
        val values = ContentValues()
        values.put(DBHandler.backDialog, settings.backDialog)
        values.put(DBHandler.nextTurnDialog, settings.nextTurnDialog)
        if(tryingSettings() == 0)dataBase.addSettings(values)
        else dataBase.updateSettings(values, 1)
    }

    fun downloadSettings(){
        val settingsList = dataBase.settingsList("%")
        settings  = settingsList[0]
    }

    private fun tryingCity(): Int{
        //Log.d("FLAG_TAG", "trying city test 1")
        val status : Int
        val cityList = dataBase.cityList("%")
        status = cityList.size
        //Log.d("FLAG_TAG", "status = $status")
        return status
    }

    fun tryingSettings(): Int{
        val status : Int
        val settingsList = dataBase.settingsList("%")
        status = settingsList.size
        return status
    }

    private fun tryingInventory(): Int{
        val status : Int
        val invetoryList = dataBase.inventoryList("%")
        status = invetoryList.size
        return status
    }

    private fun tryingProtection(): Int{
        val status : Int
        val protectionList = dataBase.itemProtectionList("%")
        status = protectionList.size
        return status
    }

    private fun tryingResource(): Int{
        val status : Int
        val resourceList = dataBase.itemResourceList("%")
        status = resourceList.size
        return status
    }

    private fun tryingWeapon(): Int{
        //Log.d("FLAG_TAG", "tryingWeapon() test 1")
        val status : Int
        val weaponList = dataBase.itemWeaponList("%")
        status = weaponList.size
        //Log.d("FLAG_TAG", "tryingWeapon() test 2 satus = $status")
        return status
    }

    private fun tryingMap(): Int{
        val status : Int
        val mapList = dataBase.mapInfo("%")
        status = mapList.size
        return status
    }

    private fun tryingTile(): Int{
        val status : Int
        val tileList = dataBase.tileList("%")
        status = tileList.size
        //Log.d("FLAG_TAG", "$status")
        return status
    }

    fun settings(): Settings{
        return settings
    }

    fun tile(): MutableList<MutableList<Tile>>{
        return tile
    }

    fun clearTile(cityId: Int){
        Log.d("FLAG_TAG", "clearTile() cityId = $cityId")
        val x = city[cityId].x
        val y = city[cityId].y
        resource[cityId].wood += resourceTile[y][x].wood
        resourceTile[y][x].wood = 0
        resource[cityId].stone += resourceTile[y][x].stone
        resourceTile[y][x].stone = 0
        resource[cityId].iron += resourceTile[y][x].iron
        resourceTile[y][x].iron = 0
        resource[cityId].food += resourceTile[y][x].food
        resourceTile[y][x].food = 0
        resource[cityId].fuel += resourceTile[y][x].fuel
        resourceTile[y][x].fuel = 0
        resource[cityId].people += resourceTile[y][x].people
        resourceTile[y][x].people = 0
    }

    fun removeCity(id: Int, newX: Int, newY: Int): Int{
        if(resource[id].fuel >= 5) {
            tile[city[id].x][city[id].y].busy = false
            resource[id].fuel -= 5
            city[id].x = newX
            city[id].y = newY
            return 1
        }
        return 0
    }

    fun craftWeapon(cityId: Int, type: Int): Int{
        when(type){
            1 -> {
                if(resource[cityId].wood >= 2 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 2
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 1 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 1 or not enough space in storage")
                    return 0
                }
            }
            2 -> {
                if(resource[cityId].wood >= 3 && resource[cityId].stone >= 1 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 3
                    resource[cityId].stone -= 1
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 2 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 2 or not enough space in storage")
                    return 0
                }
            }
            3 -> {
                if(resource[cityId].wood >= 1 && resource[cityId].stone >= 2 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 1
                    resource[cityId].stone -= 2
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 3 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 3 or not enough space in storage")
                    return 0
                }
            }
            4 -> {
                if(resource[cityId].wood >= 3 && resource[cityId].stone >= 3 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 3
                    resource[cityId].stone -= 3
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 4 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 4 or not enough space in storage")
                    return 0
                }
            }
            5 -> {
                if(resource[cityId].wood >= 2 && resource[cityId].stone >= 4 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 2
                    resource[cityId].stone -= 4
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 5 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 5 or not enough space in storage")
                    return 0
                }
            }
            6 -> {
                if(resource[cityId].wood >= 4 && resource[cityId].stone >= 6 && resource[cityId].iron >= 2 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 4
                    resource[cityId].stone -= 6
                    resource[cityId].iron -= 2
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 6 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 6 or not enough space in storage")
                    return 0
                }
            }
        }
        return 0
    }

    fun clearStorage(cityId: Int, num: Int){
        if(num < weapon[cityId].storage.size){
            weapon[cityId].storage.removeAt(num)
            Log.d("FLAG_TAG", "Weapon $num removed")
        }
        else Log.d("FLAG_TAG", "Weapon $num not found")
    }

    fun changeWeapon(cityId: Int, num: Int){
        if(num < weapon[cityId].storage.size){
            if(weapon[cityId].slots.size == 0){
                weapon[cityId].slots.add(weapon[cityId].storage[num])
                weapon[cityId].storage.removeAt(num)
            }
            else if(weapon[cityId].slots.size == 1){
                weapon[cityId].slots.add(weapon[cityId].slots[0])
                weapon[cityId].slots[0] = weapon[cityId].storage[num]
                weapon[cityId].storage.removeAt(num)
            }
            else{
                val type = weapon[cityId].slots[1]
                weapon[cityId].slots[1] = weapon[cityId].slots[0]
                weapon[cityId].slots[0] = weapon[cityId].storage[num]
                weapon[cityId].storage[num] = type
            }
        }
        else Log.d("FLAG_TAG", "Weapon $num not found")
    }

    fun craftProtection(cityId: Int, type: Int): Int{
        when(type){
            1 -> {
                if(resource[cityId].wood >= 5){
                    resource[cityId].wood -= 5
                    protection[cityId].slot = 1
                    Log.d("FLAG_TAG", "Type 1 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 1")
                    return 0
                }
            }
            2 -> {
                if(resource[cityId].wood >= 10 && resource[cityId].stone >= 5){
                    resource[cityId].wood -= 10
                    resource[cityId].stone -= 5
                    protection[cityId].slot = 2
                    Log.d("FLAG_TAG", "Type 2 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 2")
                    return 0
                }
            }
            3 -> {
                if(resource[cityId].wood >= 5 && resource[cityId].stone >= 15){
                    resource[cityId].wood -= 5
                    resource[cityId].stone -= 15
                    protection[cityId].slot = 3
                    Log.d("FLAG_TAG", "Type 3 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 3")
                    return 0
                }
            }
            4 -> {
                if(resource[cityId].wood >= 10 && resource[cityId].iron >= 10){
                    resource[cityId].wood -= 10
                    resource[cityId].iron -= 10
                    protection[cityId].slot = 4
                    Log.d("FLAG_TAG", "Type 4 made")
                    return 1
                }
                else{
                    Log.d("FLAG_TAG", "Not enough resources for type 4")
                    return 0
                }
            }
        }
        return 0
    }

    fun recountDamage(cityId: Int){
        city[cityId].damage = 0
        for(i in 0 until weapon[cityId].slots.size){
            when(weapon[cityId].slots[i]){
                1 -> city[cityId].damage += 1
                2 -> city[cityId].damage += 2
                3 -> city[cityId].damage += 3
                4 -> city[cityId].damage += 4
                5 -> city[cityId].damage += 5
                6 -> city[cityId].damage += 8
            }
        }
    }



    fun recountProtection(cityId: Int){
        city[cityId].protection = 0
        when(protection[cityId].slot){
            1 -> city[cityId].protection += 1
            2 -> city[cityId].protection += 2
            3 -> city[cityId].protection += 3
            4 -> city[cityId].protection += 4
        }
    }

    fun giveHomeCity(): CityData{
        return city[0]
    }

    fun giveHomeResource(): ItemResource{
        return resource[0]
    }

    fun giveHomeProtection(): ItemProtection{
        return protection[0]
    }

    fun giveHomeWeapon(): ItemWeapon{
        return weapon[0]
    }

    fun attacCity(cityId1: Int, cityId2: Int): Int{
        if(city[cityId2].protection < city[cityId1].damage){
            city[cityId2].hp += city[cityId2].protection
            city[cityId2].hp -= city[cityId1].damage
            if(city[cityId2].hp <= 0){
                city[cityId2].active = 0
                return 1
            }
        }
        return 0
    }

    fun lootingCity(cityId1: Int, cityId2: Int){
        resource[cityId1].wood += resource[cityId2].wood
        resource[cityId2].wood = 0
        resource[cityId1].stone += resource[cityId2].stone
        resource[cityId2].stone = 0
        resource[cityId1].iron += resource[cityId2].iron
        resource[cityId2].iron = 0
        resource[cityId1].food += resource[cityId2].food
        resource[cityId2].food = 0
        resource[cityId1].fuel += resource[cityId2].fuel
        resource[cityId2].fuel = 0
        resource[cityId1].people += resource[cityId2].people
        resource[cityId2].people = 0
    }

    fun attacLogic(cityId : Int): Int{
        var stat = 1
        if(city[cityId].x + 1 < map.x && stat == 1){
            if(tile[city[cityId].x + 1][city[cityId].y].busy == true){
                if(attacCity(cityId, tile[city[cityId].x + 1][city[cityId].y].city.id) == 1){
                    lootingCity(cityId, tile[city[cityId].x + 1][city[cityId].y].city.id)
                    removeCity(cityId, city[cityId].x + 1, city[cityId].y)
                }
                stat = 0
            }
        }
        if(city[cityId].y + 1 < map.y && stat == 1){
            if(tile[city[cityId].x][city[cityId].y + 1].busy == true){
                if(attacCity(cityId, tile[city[cityId].x][city[cityId].y + 1].city.id) == 1){
                    lootingCity(cityId, tile[city[cityId].x][city[cityId].y + 1].city.id)
                    removeCity(cityId, city[cityId].x, city[cityId].y + 1)
                }
                stat = 0
            }
        }
        if(city[cityId].x - 1 >= 0 && stat == 1){
            if(tile[city[cityId].x - 1][city[cityId].y].busy == true){
                if(attacCity(cityId, tile[city[cityId].x - 1][city[cityId].y].city.id) == 1){
                    lootingCity(cityId, tile[city[cityId].x - 1][city[cityId].y].city.id)
                    removeCity(cityId, city[cityId].x - 1, city[cityId].y)
                }
                stat = 0
            }
        }
        if(city[cityId].y - 1 >= 0 && stat == 1){
            if(tile[city[cityId].x][city[cityId].y - 1].busy == true){
                if(attacCity(cityId, tile[city[cityId].x][city[cityId].y - 1].city.id ) == 1){
                    lootingCity(cityId, tile[city[cityId].x][city[cityId].y - 1].city.id)
                    removeCity(cityId, city[cityId].x, city[cityId].y - 1)
                }
                stat = 0
            }
        }
        return stat
    }

    fun moveLogic(cityId: Int){
        var stat = 1
        if(city[cityId].x + 1 < map.x && stat == 1){
            if(tile[city[cityId].x + 1][city[cityId].y].busy == false){
                if(removeCity(cityId, city[cityId].x + 1, city[cityId].y) == 1){
                    tile[city[cityId].x][city[cityId].y].busy = true
                    stat = 0
                }
            }
        }
        if(city[cityId].y + 1 < map.y && stat == 1){
            if(tile[city[cityId].x][city[cityId].y + 1].busy == false){
                if(removeCity(cityId, city[cityId].x, city[cityId].y + 1) == 1) {
                    tile[city[cityId].x][city[cityId].y].busy = true
                    stat = 0
                }
            }
        }
        if(city[cityId].x - 1 >= 0 && stat == 1){
            if(tile[city[cityId].x - 1][city[cityId].y].busy == false){
                if(removeCity(cityId, city[cityId].x - 1, city[cityId].y) == 1) {
                    tile[city[cityId].x][city[cityId].y].busy = true
                    stat = 0
                }
            }
        }
        if(city[cityId].y - 1 >= 0 && stat == 1){
            if(tile[city[cityId].x][city[cityId].y - 1].busy == false){
                if(removeCity(cityId, city[cityId].x, city[cityId].y - 1) == 1) {
                    tile[city[cityId].x][city[cityId].y].busy = true
                }
            }
        }
    }

    fun craftLogic(cityId: Int){
        var stat = 0
        stat = craftWeapon(cityId, 6)
        if(stat == 0)stat = craftWeapon(cityId, 5)
        if(stat == 0)stat = craftWeapon(cityId, 4)
        if(stat == 0)stat = craftWeapon(cityId, 3)
        if(stat == 0)stat = craftWeapon(cityId, 2)
        if(stat == 0)stat = craftWeapon(cityId, 1)

        stat = 0
        stat = craftProtection(cityId, 4)
        if(stat == 0)stat = craftProtection(cityId, 3)
        if(stat == 0)stat = craftProtection(cityId, 2)
        if(stat == 0)stat = craftProtection(cityId, 1)
    }

    fun changeLogic(cityId: Int){
        var max: Int = 0
        for(a in weapon[cityId].slots)if(a > max)max = a
        for(i in 0 until weapon[cityId].storage.size)if(weapon[cityId].storage[i] > max || weapon[cityId].slots.size < 2)changeWeapon(cityId, i)
    }

    fun recountPeople(cityId: Int){
        if(resource[cityId].food == 0)resource[cityId].people -= 1
        else{
            resource[cityId].food -= resource[cityId].people
            if(resource[cityId].food < 0)resource[cityId].food = 0
        }
        city[cityId].people = resource[cityId].people
    }

    fun renameCity(cityId: Int){
        if(city[cityId].people < 6){
            if(city[cityId].type == "village")city[cityId].hp -= 5
            city[cityId].type = "town"
        }
        else if(city[cityId].people < 21){
            if(city[cityId].type == "town")city[cityId].hp += 5
            if(city[cityId].type == "city")city[cityId].hp -= 10
            city[cityId].type = "village"
        }
        else if(city[cityId].people < 51){
            if(city[cityId].type == "village")city[cityId].hp += 10
            if(city[cityId].type == "metropolis")city[cityId].hp -= 20
            city[cityId].type = "city"
        }
        else{
            if(city[cityId].type == "city")city[cityId].hp += 20
            city[cityId].type = "metropolis"
        }
    }

    fun recountActive(cityId: Int){
        if(city[cityId].hp <= 0 || resource[cityId].people <= 0)city[cityId].active = 0
    }

    fun nextTurn(){
        Log.d("FLAG_TAG", "next turn test 1")
        for(i in 0 until countCity){
            if(city[i].active == 0)continue
            clearTile(i)
            if(attacLogic(i) != 0)moveLogic(i)
            clearTile(i)
            craftLogic(i)
            changeLogic(i)
            recountDamage(i)
            recountProtection(i)
            recountPeople(i)
            renameCity(i)
            recountActive(i)
        }
    }
}