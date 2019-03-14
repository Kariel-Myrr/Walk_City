package com.example.lesson_1.walkcity.DataBase

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
    private var place : MutableList<MutableList<Place>> = mutableListOf()
    private var resourcePlace : MutableList<MutableList<ItemResource>> = mutableListOf()
    private val countCity = 5
    var settings = Settings()

    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

    private fun initPlace(i: Int, e: Int){
        val stat = (1..6).random()
        //var numNull = (0..0).random()
        //var numLow = (0..5).random()
        //var numSmall = (0..10).random()
        //var numMedium = (5..10).random()
        //var numHigh = (5..15).random()
        when(stat) {
            1 -> {
                place[i][e].type = "glade"
                place[i][e].idItemResource = i * map.y + e + countCity
                resourcePlace[i][e].tree = (0..5).random()
                resourcePlace[i][e].stone = (0..5).random()
                resourcePlace[i][e].iron = (0..0).random()
                resourcePlace[i][e].food = (5..15).random()
                resourcePlace[i][e].water = (0..10).random()
            }
            2 -> {
                place[i][e].type = "forest"
                place[i][e].idItemResource = i * map.y + e + countCity
                resourcePlace[i][e].tree = (5..15).random()
                resourcePlace[i][e].stone = (0..0).random()
                resourcePlace[i][e].iron = (0..0).random()
                resourcePlace[i][e].food = (0..10).random()
                resourcePlace[i][e].water = (0..5).random()
            }
            3 -> {
                place[i][e].type = "lake"
                place[i][e].idItemResource = i * map.y + e + countCity
                resourcePlace[i][e].tree = (0..0).random()
                resourcePlace[i][e].stone = (0..10).random()
                resourcePlace[i][e].iron = (0..5).random()
                resourcePlace[i][e].food = (5..10).random()
                resourcePlace[i][e].water = (5..10).random()
            }
            4 -> {
                place[i][e].type = "sea"
                place[i][e].idItemResource = i * map.y + e + countCity
                resourcePlace[i][e].tree = (0..0).random()
                resourcePlace[i][e].stone = (0..10).random()
                resourcePlace[i][e].iron = (0..5).random()
                resourcePlace[i][e].food = (5..10).random()
                resourcePlace[i][e].water = (5..15).random()
            }
            5 -> {
                place[i][e].type = "desert"
                place[i][e].idItemResource = i * map.y + e + countCity
                resourcePlace[i][e].tree = (0..5).random()
                resourcePlace[i][e].stone = (0..5).random()
                resourcePlace[i][e].iron = (0..10).random()
                resourcePlace[i][e].food = (0..5).random()
                resourcePlace[i][e].water = (0..0).random()
            }
            else -> {
                place[i][e].type = "mountain"
                place[i][e].idItemResource = i * map.y + e + countCity
                resourcePlace[i][e].tree = (0..0).random()
                resourcePlace[i][e].stone = (5..10).random()
                resourcePlace[i][e].iron = (5..15).random()
                resourcePlace[i][e].food = (0..5).random()
                resourcePlace[i][e].water = (0..5).random()
            }
        }
    }

    fun initPlace(i: Int, e: Int, type: String, tree: Int, stone: Int, iron: Int, food: Int, water: Int){
        place[i][e].type = type
        resourcePlace[i][e].tree = tree
        resourcePlace[i][e].stone = stone
        resourcePlace[i][e].iron = iron
        resourcePlace[i][e].food = food
        resourcePlace[i][e].water = water
    }

    private fun initMap(){
        map.x = 5
        map.y = 5
        for(i in 0 until map.y){
            map.idPlace.add(mutableListOf())
            resourcePlace.add(mutableListOf())
            place.add(mutableListOf())
            for(e in 0 until map.x){
                map.idPlace[i].add(i * map.y + e)
                val idPlace = map.idPlace[i][e]
                Log.d("FLAG_TAG", "map[$i][$e]: idPlace = $idPlace")
                resourcePlace[i].add(ItemResource())
                place[i].add(Place())
                initPlace(i, e)
                val type = place[i][e].type
                val idItemResource = place[i][e].idItemResource
                Log.d("FLAG_TAG", "place[$i][$e]: type = $type idItemResource = $idItemResource")
                val tree = resourcePlace[i][e].tree
                val stone = resourcePlace[i][e].stone
                val iron = resourcePlace[i][e].iron
                val food = resourcePlace[i][e].food
                val water = resourcePlace[i][e].water
                Log.d("FLAG_TAG", "resourcePlace[$i][$e]: tree = $tree stone = $stone iron = $iron food = $food water = $water")
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
        tmpCity.idInventory = id
        Log.d("FLAG_TAG", "city: name = ${tmpCity.name} hp = ${tmpCity.hp} type = ${tmpCity.type} active = ${tmpCity.active} people = ${tmpCity.people} damage = ${tmpCity.damage} idInventory = ${tmpCity.idInventory}")
        city.add(tmpCity)
    }

    fun initCity(id: Int, name: String, hp: Int, type: String, active: Int, people: Int, damage: Int){
        city[id].name = name
        city[id].hp = hp
        city[id].type = type
        city[id].active = active
        city[id].people = people
        city[id].damage = damage
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
        tmpResource.tree = 0
        tmpResource.stone = 0
        tmpResource.iron = 0
        tmpResource.food = 2
        tmpResource.water = 3
        Log.d("FLAG_TAG", "resource: tree = ${tmpResource.tree} stone = ${tmpResource.stone} iron = ${tmpResource.iron} food = ${tmpResource.food} water = ${tmpResource.water}")
        resource.add(tmpResource)
    }

    fun initResource(id: Int, tree: Int, stone: Int, iron: Int, food: Int, water: Int){
        resource[id].tree = 0
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
            for(i in 0 until countCity)Log.d("FLAG_TAG", "city: name = ${city[i].name} hp = ${city[i].hp} type = ${city[i].type} active = ${city[i].active} people = ${city[i].people} damage = ${city[i].damage} idInventory = ${city[i].idInventory}")
        }
        else Log.d("FLAG_TAG", "ERROR: cityList.size = ${cityList.size}")
    }

    fun downloadCity(id: Int){
        val cityList = dataBase.cityList("%")
        if(cityList.size != 0) {
            city[id] = cityList[id]
            Log.d("FLAG_TAG", "city: name = ${city[id].name} hp = ${city[id].hp} type = ${city[id].type} active = ${city[id].active} people = ${city[id].people} damage = ${city[id].damage} idInventory = ${city[id].idInventory}")
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
                Log.d("FLAG_TAG", "resource: tree = ${resource[i].tree} stone = ${resource[i].stone} iron = ${resource[i].iron} food = ${resource[i].food} water = ${resource[i].water}")
            }
        }
        else Log.d("FLAG_TAG", "ERROR: resurceList.size = ${resourceList.size}")
    }

    private fun downloadResource(id: Int){
        val resourceList = dataBase.itemResourceList("%")
        if(resourceList.size != 0) {
            resource[id] = resourceList[id]
            Log.d("FLAG_TAG", "resource: tree = ${resource[id].tree} stone = ${resource[id].stone} iron = ${resource[id].iron} food = ${resource[id].food} water = ${resource[id].water}")
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

    private fun downloadPlace(){
        val placeList = dataBase.placeList("%")
        if(placeList.size != 0) {
            for (i in 0 until map.y) {
                place.add(mutableListOf())

                for (e in 0 until map.x) {
                    place[i].add(placeList[i * map.y + e])
                    val type = place[i][e].type
                    val idItemResource = place[i][e].idItemResource
                    Log.d("FLAG_TAG", "place[$i][$e]: type = $type idItemResource = $idItemResource")
                }
            }
        }
        else Log.d("FLAG_TAG", "ERROR: placeList.size = ${placeList.size}")
    }

    fun downloadPlace(i: Int, e: Int){
        val placeList = dataBase.placeList("%")
        if(placeList.size != 0) {
            place[i][e] = placeList[i * map.y + e]
            val type = place[i][e].type
            val idItemResource = place[i][e].idItemResource
            Log.d("FLAG_TAG", "place[$i][$e]: type = $type idItemResource = $idItemResource")
        }
        else Log.d("FLAG_TAG", "ERROR: placeList.size = ${placeList.size}")
    }

    private fun downloadResourcePlace(){
        val resourcePlaceList = dataBase.itemResourceList("%")
        if(resourcePlaceList.size != 0){
            for(i in 0 until map.y){
                resourcePlace.add(mutableListOf())
                for(e in 0 until map.x){
                    resourcePlace[i].add(resourcePlaceList[place[i][e].idItemResource])
                    val tree = resourcePlace[i][e].tree
                    val stone = resourcePlace[i][e].stone
                    val iron = resourcePlace[i][e].iron
                    val food = resourcePlace[i][e].food
                    val water = resourcePlace[i][e].water
                    Log.d("FLAG_TAG", "resourcePlace[$i][$e]: tree = $tree stone = $stone iron = $iron food = $food water = $water")
                }
            }
        }
        else Log.d("FLAG_TAG", "ERROR: resourcePlaceList.size = ${resourcePlaceList.size}")
    }

    fun downloadResourcePlace(i: Int, e: Int){
        val resourcePlaceList = dataBase.itemResourceList("%")
        if(resourcePlaceList.size != 0){
            resourcePlace[i][e] = resourcePlaceList[place[i][e].idItemResource]
            val tree = resourcePlace[i][e].tree
            val stone = resourcePlace[i][e].stone
            val iron = resourcePlace[i][e].iron
            val food = resourcePlace[i][e].food
            val water = resourcePlace[i][e].water
            Log.d("FLAG_TAG", "resourcePlace[$i][$e]: tree = $tree stone = $stone iron = $iron food = $food water = $water")
        }
        else Log.d("FLAG_TAG", "ERROR: resourcePlaceList.size = ${resourcePlaceList.size}")
    }

    fun download(){
        Log.d("FLAG_TAG", "DOWNLOAD DB")
        downloadCity()
        downloadInventory()
        downloadProtection()
        downloadResource()
        downloadWeapon()
        downloadMap()
        downloadPlace()
        downloadResourcePlace()
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
        unloadPlace()
        Log.d("FLAG_TAG", "place unload")
        unloadResourcePlace()
        Log.d("FLAG_TAG", "resource place unload")
        Log.d("FLAG_TAG", "COMLETE UNLOAD DB")
    }

    private fun unloadCity(id: Int){
        val values = ContentValues()
        values.put(DBHandler.name, city[id].name)
        values.put(DBHandler.hp, city[id].hp)
        values.put(DBHandler.type, city[id].type)
        values.put(DBHandler.active, city[id].active)
        values.put(DBHandler.people, city[id].people)
        values.put(DBHandler.damage, city[id].damage)
        values.put(DBHandler.idInventory, city[id].idInventory)
        if(tryingCity() < countCity)dataBase.addCity(values)
        else dataBase.updateCity(values, id + 1)
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
        values.put(DBHandler.tree, resource[id].tree)
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
        val values = ContentValues()
        values.put(DBHandler.x, map.x)
        values.put(DBHandler.y, map.y)
        var mapString = ""
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                mapString += map.idPlace[i][e].toString() + " "
            }
        }
        values.put(DBHandler.idPlace, mapString)
        if(tryingMap() == 0)dataBase.addMap(values)
        else dataBase.updateMap(values, 1)
    }

    private fun unloadPlace(){
        var values : ContentValues
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                values = ContentValues()
                values.put(DBHandler.type, place[i][e].type)
                values.put(DBHandler.idItemResource, place[i][e].idItemResource)
                if(tryingPlace() < map.y * map.x)dataBase.addPlace(values)
                else dataBase.updatePlace(values, i * map.y + e + 1)
            }
        }
    }

    private fun unloadResourcePlace(){
        var values : ContentValues
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                values = ContentValues()
                values.put(DBHandler.tree, resourcePlace[i][e].tree)
                values.put(DBHandler.stone, resourcePlace[i][e].stone)
                values.put(DBHandler.iron, resourcePlace[i][e].iron)
                values.put(DBHandler.food, resourcePlace[i][e].food)
                values.put(DBHandler.water, resourcePlace[i][e].water)
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
        val status : Int
        val cityList = dataBase.cityList("%")
        status = cityList.size
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

    private fun tryingPlace(): Int{
        val status : Int
        val placeList = dataBase.placeList("%")
        status = placeList.size
        return status
    }

    fun settings(): Settings{
        return settings
    }

    fun place(): MutableList<MutableList<Place>>{
        return place
    }
}