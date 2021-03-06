package cool_guys.walkcity.DataBase

import android.content.ContentValues
import android.content.Context
import android.util.Log
import java.util.*

class Manager(context: Context){

    private var city : MutableList<CityData> = mutableListOf()
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
                resourceTile[i][e].water = (0..10).random()
            }
            2 -> {
                tile[i][e].type = "hill"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (5..15).random()
                resourceTile[i][e].stone = (0..0).random()
                resourceTile[i][e].iron = (0..0).random()
                resourceTile[i][e].food = (0..10).random()
                resourceTile[i][e].water = (0..5).random()
            }
            3 -> {
                tile[i][e].type = "lake"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..0).random()
                resourceTile[i][e].stone = (0..10).random()
                resourceTile[i][e].iron = (0..5).random()
                resourceTile[i][e].food = (5..10).random()
                resourceTile[i][e].water = (5..10).random()
            }
            4 -> {
                tile[i][e].type = "sea"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..0).random()
                resourceTile[i][e].stone = (0..10).random()
                resourceTile[i][e].iron = (0..5).random()
                resourceTile[i][e].food = (5..10).random()
                resourceTile[i][e].water = (5..15).random()
            }
            5 -> {
                tile[i][e].type = "desert"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..5).random()
                resourceTile[i][e].stone = (0..5).random()
                resourceTile[i][e].iron = (0..10).random()
                resourceTile[i][e].food = (0..5).random()
                resourceTile[i][e].water = (0..0).random()
            }
            else -> {
                tile[i][e].type = "forest"
                tile[i][e].idItemResource = i * map.y + e + countCity
                resourceTile[i][e].wood = (0..0).random()
                resourceTile[i][e].stone = (5..10).random()
                resourceTile[i][e].iron = (5..15).random()
                resourceTile[i][e].food = (0..5).random()
                resourceTile[i][e].water = (0..5).random()
            }
        }
    }

    fun initTile(i: Int, e: Int, type: String, tree: Int, stone: Int, iron: Int, food: Int, water: Int){
        tile[i][e].type = type
        resourceTile[i][e].wood = tree
        resourceTile[i][e].stone = stone
        resourceTile[i][e].iron = iron
        resourceTile[i][e].food = food
        resourceTile[i][e].water = water
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
                val water = resourceTile[i][e].water
                Log.d("FLAG_TAG", "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food water = $water")
            }
        }

    }

    private fun initCity(id: Int){
        val tmpCity = CityData()
        if(id == 0)tmpCity.name = "Home"
        else tmpCity.name = "City[$id]"
        tmpCity.hp = 10
        tmpCity.type = "village"
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
        tmpResource.food = 2
        tmpResource.water = 3
        Log.d("FLAG_TAG", "resource: wood = ${tmpResource.wood} stone = ${tmpResource.stone} iron = ${tmpResource.iron} food = ${tmpResource.food} water = ${tmpResource.water}")
        resource.add(tmpResource)
    }

    fun initResource(id: Int, tree: Int, stone: Int, iron: Int, food: Int, water: Int){
        resource[id].wood = 0
        resource[id].stone = 0
        resource[id].iron = 0
        resource[id].food = 2
        resource[id].water = 3
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

    fun init(){
        Log.d("FLAG_TAG", "INIT MANAGER")
        for(i in 0 until countCity)initCity(i)
        for(i in 0 until countCity)initInventory(i)
        for(i in 0 until countCity)initProtection()
        for(i in 0 until countCity)initResource()
        for(i in 0 until countCity)initWeapon()
        initMap()
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
                Log.d("FLAG_TAG", "resource: wood = ${resource[i].wood} stone = ${resource[i].stone} iron = ${resource[i].iron} food = ${resource[i].food} water = ${resource[i].water}")
            }
        }
        else Log.d("FLAG_TAG", "ERROR: resurceList.size = ${resourceList.size}")
    }

    private fun downloadResource(id: Int){
        val resourceList = dataBase.itemResourceList("%")
        if(resourceList.size != 0) {
            resource[id] = resourceList[id]
            Log.d("FLAG_TAG", "resource: wood = ${resource[id].wood} stone = ${resource[id].stone} iron = ${resource[id].iron} food = ${resource[id].food} water = ${resource[id].water}")
        }
        else Log.d("FLAG_TAG", "ERROR: resurceList.size = ${resourceList.size}")
    }

    private fun downloadWeapon(){
        val weaponList = dataBase.itemWeaponList("%")
        if(weaponList.size != 0) {
            weapon = weaponList
            for(i in 0 until countCity)Log.d("FLAG_TAG", "weapon: weapon.slots.size = ${weapon[i].slots.size} weapn.storage.size = ${weapon[i].storage.size}")
        }
        else Log.d("FLAG_TAG", "ERROR: weaponList.size = ${weaponList.size}")
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
                    val water = resourceTile[i][e].water
                    Log.d("FLAG_TAG", "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food water = $water")
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
            val water = resourceTile[i][e].water
            Log.d("FLAG_TAG", "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food water = $water")
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
        Log.d("FLAG_TAG", "unload city $id")
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
        Log.d("FLAG_TAG", "test unload city 1")
        if(tryingCity() < countCity)dataBase.addCity(values)
        else dataBase.updateCity(values, id + 1)
        Log.d("FLAG_TAG", "test unload city 2")
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
        values.put(DBHandler.water, resource[id].water)
        if(tryingResource() < countCity)dataBase.addItemResource(values)
        else dataBase.updateItemResource(values, id + 1)
    }

    private fun unloadWeapon(id: Int){
        val values = ContentValues()
        var slotsString = ""
        for(i in 0 until weapon[id].slots.size)slotsString += weapon[id].slots[i].toString() + " "
        values.put(DBHandler.slots, slotsString)
        var storageString = ""
        for (i in 0 until weapon[id].storage.size)storageString += weapon[id].storage[i].toString() + " "
        values.put(DBHandler.storage, storageString)
        if(tryingWeapon() < countCity)dataBase.addItemWeapon(values)
        else dataBase.updateItemWeapon(values, id + 1)
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
                values.put(DBHandler.water, resourceTile[i][e].water)
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
        Log.d("FLAG_TAG", "trying city test 1")
        val status : Int
        val cityList = dataBase.cityList("%")
        status = cityList.size
        Log.d("FLAG_TAG", "status = $status")
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
        val status : Int
        val weaponList = dataBase.itemWeaponList("%")
        status = weaponList.size
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
        resource[cityId].water += resourceTile[y][x].water
        resourceTile[y][x].water = 0
    }

    fun removeCity(id: Int, newX: Int, newY: Int){
        city[id].x = newX
        city[id].y = newY
    }

    fun craftWeapon(cityId: Int, type: Int){
        when(type){
            1 -> {
                if(resource[cityId].wood >= 2 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 2
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 1 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 1 or not enough space in storage")
            }
            2 -> {
                if(resource[cityId].wood >= 3 && resource[cityId].stone >= 1 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 3
                    resource[cityId].stone -= 1
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 2 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 2 or not enough space in storage")
            }
            3 -> {
                if(resource[cityId].wood >= 1 && resource[cityId].stone >= 2 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 1
                    resource[cityId].stone -= 2
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 3 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 3 or not enough space in storage")
            }
            4 -> {
                if(resource[cityId].wood >= 3 && resource[cityId].stone >= 3 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 3
                    resource[cityId].stone -= 3
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 4 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 4 or not enough space in storage")
            }
            5 -> {
                if(resource[cityId].wood >= 2 && resource[cityId].stone >= 4 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 2
                    resource[cityId].stone -= 4
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 5 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 5 or not enough space in storage")
            }
            6 -> {
                if(resource[cityId].wood >= 4 && resource[cityId].stone >= 6 && resource[cityId].iron >= 2 && weapon[cityId].storage.size < 10){
                    resource[cityId].wood -= 4
                    resource[cityId].stone -= 6
                    resource[cityId].iron -= 2
                    weapon[cityId].storage.add(type)
                    Log.d("FLAG_TAG", "Type 6 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 6 or not enough space in storage")
            }
        }
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
            if(weapon[cityId].storage[num] == 1 || weapon[cityId].storage[num] == 2 || weapon[cityId].storage[num] == 5){
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
            else{
                if(weapon[cityId].slots.size == 0){
                    weapon[cityId].slots.add(weapon[cityId].storage[num])
                    weapon[cityId].storage.removeAt(num)
                }
                else if(weapon[cityId].slots.size == 1){
                    val type = weapon[cityId].slots[0]
                    weapon[cityId].slots[0] = weapon[cityId].storage[num]
                    weapon[cityId].storage[num] = type
                }
                else{
                    if(weapon[cityId].storage.size < 10){
                        val type1 = weapon[cityId].slots[0]
                        val type2 = weapon[cityId].slots[1]
                        weapon[cityId].slots.removeAt(1)
                        weapon[cityId].slots[0] = weapon[cityId].storage[num]
                        weapon[cityId].storage[num] = type1
                        weapon[cityId].storage.add(type2)
                    }
                    else Log.d("FLAG_TAG", "Not enough space in storage")
                }
            }
        }
        else Log.d("FLAG_TAG", "Weapon $num not found")
    }

    fun craftProtection(cityId: Int, type: Int){
        when(type){
            1 -> {
                if(resource[cityId].wood >= 5){
                    resource[cityId].wood -= 5
                    protection[cityId].slot = 1
                    Log.d("FLAG_TAG", "Type 1 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 1")
            }
            2 -> {
                if(resource[cityId].wood >= 10 && resource[cityId].stone >= 5){
                    resource[cityId].wood -= 10
                    resource[cityId].stone -= 5
                    protection[cityId].slot = 2
                    Log.d("FLAG_TAG", "Type 2 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 2")
            }
            3 -> {
                if(resource[cityId].wood >= 5 && resource[cityId].stone >= 15){
                    resource[cityId].wood -= 5
                    resource[cityId].stone -= 15
                    protection[cityId].slot = 3
                    Log.d("FLAG_TAG", "Type 3 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 3")
            }
            4 -> {
                if(resource[cityId].wood >= 10 && resource[cityId].iron >= 10){
                    resource[cityId].wood -= 10
                    resource[cityId].iron -= 10
                    protection[cityId].slot = 4
                    Log.d("FLAG_TAG", "Type 4 made")
                }
                else Log.d("FLAG_TAG", "Not enough resources for type 4")
            }
        }
    }

    fun recountDamage(cityId: Int){
        city[cityId].damage = 0
        for(i in 0 until weapon[cityId].slots.size){
            when(weapon[cityId].slots[i]){
                1 -> city[cityId].damage += 1
                2 -> city[cityId].damage += 2
                3 -> city[cityId].damage += 3
                4 -> city[cityId].damage += 4
                5 -> city[cityId].damage += 3
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
}