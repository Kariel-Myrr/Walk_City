package com.example.lesson_1.walkcity.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import java.util.*

class Manager(context: Context){

    var city = CityData()
    var inventory = InventoryData()
    var protection = ItemProtection()
    var resource = ItemResource()
    var weapon = ItemWeapon()
    var DataBase = DBHandler(context)
    var settings = Settings()
    var map = Map()
    var place : MutableList<MutableList<Place>> = mutableListOf()
    var resourcePlace : MutableList<MutableList<ItemResource>> = mutableListOf()

    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

    private fun initPlace(i: Int, e: Int){
        var stat = (1..6).random()
        var numNull = (0..0).random()
        var numLow = (0..5).random()
        var numSmall = (0..10).random()
        var numMedium = (5..10).random()
        var numHigh = (5..15).random()
        if(stat == 1){
            place[i][e].type = "glade"
            place[i][e].idItemResource = i * map.y + e + 1
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (0..5).random()
            resourcePlace[i][e].stone = (0..5).random()
            resourcePlace[i][e].iron = (0..0).random()
            resourcePlace[i][e].food = (5..15).random()
            resourcePlace[i][e].water = (0..10).random()
        }
        else if(stat == 2){
            place[i][e].type = "forest"
            place[i][e].idItemResource = i * map.y + e + 1
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (5..15).random()
            resourcePlace[i][e].stone = (0..0).random()
            resourcePlace[i][e].iron = (0..0).random()
            resourcePlace[i][e].food = (0..10).random()
            resourcePlace[i][e].water = (0..5).random()
        }
        else if(stat == 3){
            place[i][e].type = "lake"
            place[i][e].idItemResource = i * map.y + e + 1
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (0..0).random()
            resourcePlace[i][e].stone = (0..10).random()
            resourcePlace[i][e].iron = (0..5).random()
            resourcePlace[i][e].food = (5..10).random()
            resourcePlace[i][e].water = (5..10).random()
        }
        else if(stat == 4){
            place[i][e].type = "sea"
            place[i][e].idItemResource = i * map.y + e + 1
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (0..0).random()
            resourcePlace[i][e].stone = (0..10).random()
            resourcePlace[i][e].iron = (0..5).random()
            resourcePlace[i][e].food = (5..10).random()
            resourcePlace[i][e].water = (5..15).random()
        }
        else if(stat == 5){
            place[i][e].type = "desert"
            place[i][e].idItemResource = i * map.y + e + 1
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (0..5).random()
            resourcePlace[i][e].stone = (0..5).random()
            resourcePlace[i][e].iron = (0..10).random()
            resourcePlace[i][e].food = (0..5).random()
            resourcePlace[i][e].water = (0..0).random()
        }
        else{
            place[i][e].type = "mountain"
            place[i][e].idItemResource = i * map.y + e + 1
            resourcePlace[i][e].tree = (0..0).random()
            resourcePlace[i][e].stone = (5..10).random()
            resourcePlace[i][e].iron = (5..15).random()
            resourcePlace[i][e].food = (0..5).random()
            resourcePlace[i][e].water = (0..5).random()
        }
    }

    private fun initMap(){
        map.x = 5
        map.y = 5
        for(i in 0 until map.y){
            map.idPlace.add(mutableListOf())
            resourcePlace.add(mutableListOf())
            place.add(mutableListOf())
            for(e in 0 until map.x){
                map.idPlace[i].add(i * map.y + e + 1)
                var idPlace = map.idPlace[i][e]
                Log.d("FLAG_TAG", "map[$i][$e]: idPlace = $idPlace")
                resourcePlace[i].add(ItemResource())
                place[i].add(Place())
                initPlace(i, e)
                var type = place[i][e].type
                var idItemResource = place[i][e].idItemResource
                Log.d("FLAG_TAG", "place[$i][$e]: type = $type idItemResource = $idItemResource")
                var tree = resourcePlace[i][e].tree
                var stone = resourcePlace[i][e].stone
                var iron = resourcePlace[i][e].iron
                var food = resourcePlace[i][e].food
                var water = resourcePlace[i][e].water
                Log.d("FLAG_TAG", "resourcePlace[$i][$e]: tree = $tree stone = $stone iron = $iron food = $food water = $water")
            }
        }

    }

    fun init(){
        Log.d("FLAG_TAG", "INIT MANAGER")
        city.name = "Home"
        city.hp = 10
        city.type = "village"
        city.active = 1
        city.people = 1
        city.damage = 0
        city.idInventory = 1
        Log.d("FLAG_TAG", "city: name = ${city.name} hp = ${city.hp} type = ${city.type} active = ${city.active} people = ${city.people} damage = ${city.damage} idInventory = ${city.idInventory}")
        inventory.idItemProtection = 0
        inventory.idItemResource = 0
        inventory.idItemWeapon = 0
        Log.d("FLAG_TAG", "inventory: idItemProtection = ${inventory.idItemProtection} idItemResource = ${inventory.idItemResource} idItemWeapon = ${inventory.idItemWeapon}")
        protection.slot = 0
        Log.d("FLAG_TAG", "protection: slot = ${protection.slot}")
        resource.tree = 0
        resource.stone = 0
        resource.iron = 0
        resource.food = 2
        resource.water = 3
        Log.d("FLAG_TAG", "resource: tree = ${resource.tree} stone = ${resource.stone} iron = ${resource.iron} food = ${resource.food} water = ${resource.water}")
        weapon.storage = mutableListOf()
        weapon.slots = mutableListOf()
        initMap()
        Log.d("FLAG_TAG", "COMLETE INIT MANAGER")
    }

    fun download(){
        Log.d("FLAG_TAG", "DOWNLOAD DB")
        var cityList = DataBase.CityList("%")
        //Log.d("FLAG_TAG", "It's ok?")
        if(cityList.size != 0) {
            city = cityList[0]
            Log.d("FLAG_TAG", "city: name = ${city.name} hp = ${city.hp} type = ${city.type} active = ${city.active} people = ${city.people} damage = ${city.damage} idInventory = ${city.idInventory}")
        }
        else Log.d("FLAG_TAG", "ERROR: cityList.size = ${cityList.size}")
        var inventoryList = DataBase.InventoryList("%")
        if(inventoryList.size != 0) {
            inventory = inventoryList[0]
            Log.d("FLAG_TAG", "inventory: idItemProtection = ${inventory.idItemProtection} idItemResource = ${inventory.idItemResource} idItemWeapon = ${inventory.idItemWeapon}")
        }
        else Log.d("FLAG_TAG", "ERROR: invenoryList.size = ${inventoryList.size}")
        var protectionList = DataBase.ItemProtectionList("%")
        if(protectionList.size != 0) {
            protection = protectionList[0]
            Log.d("FLAG_TAG", "protection: slot = ${protection.slot}")
        }
        else Log.d("FLAG_TAG", "ERROR: protectionList.size = ${protectionList.size}")
        var resourceList = DataBase.ItemResourceList("%")
        if(resourceList.size != 0) {
            resource = resourceList[0]
            Log.d("FLAG_TAG", "resource: tree = ${resource.tree} stone = ${resource.stone} iron = ${resource.iron} food = ${resource.food} water = ${resource.water}")
        }
        else Log.d("FLAG_TAG", "ERROR: resurceList.size = ${resourceList.size}")
        var weaponList = DataBase.ItemWeaponList("%")
        if(weaponList.size != 0) {
            weapon = weaponList[0]
            Log.d("FLAG_TAG", "weapon: weapon.slots.size = ${weapon.slots.size} weapn.storage.size = ${weapon.storage.size}")
        }
        else Log.d("FLAG_TAG", "ERROR: protectionList.size = ${protectionList.size}")
        var _map = DataBase.MapInfo("%")
        if(_map.size != 0) {
            map = _map[0]
            Log.d("FLAG_TAG", "map: map.x = ${map.x} map.y = ${map.y}")
        }
        else Log.d("FLAG_TAG", "ERROR: _map.size = ${_map.size}")
        //Log.d("FLAG_TAG", "resource.size = ${tryingResource()}")
        var placeList = DataBase.PlaceList("%")
        //Log.d("FLAG_TAG", "placeList.size = ${placeList.size}")
        if(placeList.size != 0) {
            for (i in 0 until map.y) {
                place.add(mutableListOf())
                resourcePlace.add(mutableListOf())
                for (e in 0 until map.x) {
                    //Log.d("FLAG_TAG", "test 1")
                    place[i].add(placeList[i * map.y + e])
                    //Log.d("FLAG_TAG", "test 2")
                    resourcePlace[i].add(resourceList[place[i][e].idItemResource])
                    //Log.d("FLAG_TAG", "test 3")
                    var idPlace = map.idPlace[i][e]
                    //Log.d("FLAG_TAG", "map[$i][$e]: idPlace = $idPlace")
                    var type = place[i][e].type
                    var idItemResource = place[i][e].idItemResource
                    Log.d("FLAG_TAG", "place[$i][$e]: type = $type idItemResource = $idItemResource")
                    var tree = resourcePlace[i][e].tree
                    var stone = resourcePlace[i][e].stone
                    var iron = resourcePlace[i][e].iron
                    var food = resourcePlace[i][e].food
                    var water = resourcePlace[i][e].water
                    Log.d("FLAG_TAG", "resourcePlace[$i][$e]: tree = $tree stone = $stone iron = $iron food = $food water = $water")
                }
            }
        }
        else Log.d("FLAG_TAG", "ERROR: placeList.size = ${placeList.size}")
        Log.d("FLAG_TAG", "COMLETE DOWNLOAD DB")
    }

    fun unload(){
        Log.d("FLAG_TAG", "UNLOAD DB")
        unloadCity()
        Log.d("FLAG_TAG", "city unload")
        unloadInvenory()
        Log.d("FLAG_TAG", "inventory unload")
        unloadProtection()
        Log.d("FLAG_TAG", "protection unload")
        unloadResource()
        Log.d("FLAG_TAG", "resource unload")
        unloadWeapon()
        Log.d("FLAG_TAG", "weapon unload")
        unloadMap()
        Log.d("FLAG_TAG", "map unload")
        unloadPlace()
        Log.d("FLAG_TAG", "place unload")
        unloadResourcePlace()
        Log.d("FLAG_TAG", "resource place unload")
        Log.d("FLAG_TAG", "COMLETE UNLOAD DB")
    }

    private fun unloadCity(){
        var values = ContentValues()
        values.put(DBHandler.name, city.name)
        values.put(DBHandler.hp, city.hp)
        values.put(DBHandler.type, city.type)
        values.put(DBHandler.active, city.active)
        values.put(DBHandler.people, city.people)
        values.put(DBHandler.damage, city.damage)
        values.put(DBHandler.idInventory, city.idInventory)
        if(tryingCity() == 0)DataBase.addCity(values)
        else DataBase.updateCity(values, 1)
    }

    private  fun unloadInvenory(){
        var values = ContentValues()
        values.put(DBHandler.idItemResource, inventory.idItemResource)
        values.put(DBHandler.idItemProtection, inventory.idItemProtection)
        values.put(DBHandler.idItemWeapon, inventory.idItemWeapon)
        if(tryingInventory() == 0)DataBase.addInventoryData(values)
        else DataBase.updateInventoryData(values, 1)
    }

    private fun unloadProtection(){
        var values = ContentValues()
        values.put(DBHandler.slot, protection.slot)
        if(tryingProtection() == 0)DataBase.addItemProtection(values)
        else DataBase.updateItemProtection(values, 1)
    }

    private fun unloadResource(){
        var values = ContentValues()
        values.put(DBHandler.tree, resource.tree)
        values.put(DBHandler.stone, resource.stone)
        values.put(DBHandler.iron, resource.iron)
        values.put(DBHandler.food, resource.food)
        values.put(DBHandler.water, resource.water)
        if(tryingResource() == 0)DataBase.addItemResource(values)
        else DataBase.updateItemResource(values, 1)
    }

    private fun unloadWeapon(){
        //Log.d("FLAG_TAG", "weapon test 1")
        var values = ContentValues()
        var _slots : String = ""
        //Log.d("FLAG_TAG", "weapon test 2")
        //Log.d("FLAG_TAG", "weapon.slots.size = ${weapon.slots.size}")
        for(i in 0 until weapon.slots.size)_slots += weapon.slots[i].toString() + " "
        values.put(DBHandler.slots, _slots)
        //Log.d("FLAG_TAG", "weapon test 3")
        //Log.d("FLAG_TAG", "weapon.storage.size = ${weapon.storage.size}")
        var _storage : String = ""
        for (i in 0 until weapon.storage.size)_storage += weapon.storage[i].toString() + " "
        values.put(DBHandler.storage, _storage)
        //Log.d("FLAG_TAG", "weapon test 4")
        if(tryingWeapon() == 0)DataBase.addItemWeapon(values)
        else DataBase.updateItemWeapon(values, 1)
        //Log.d("FLAG_TAG", "weapon test 5")
    }

    private fun unloadMap(){
        //Log.d("FLAG_TAG", "map test 1")
        var values = ContentValues()
        values.put(DBHandler.x, map.x)
        values.put(DBHandler.y, map.y)
        //Log.d("FLAG_TAG", "map test 2")
        var Map = ""
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                Map += map.idPlace[i][e].toString() + " "
            }
        }
        values.put(DBHandler.idPlace, Map)
        //Log.d("FLAG_TAG", "map test 3")
        if(tryingMap() == 0)DataBase.addMap(values)
        else DataBase.updateMap(values, 1)
        //Log.d("FLAG_TAG", "map test 4")
    }

    private fun unloadPlace(){
        var values = ContentValues()
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                values = ContentValues()
                values.put(DBHandler.type, place[i][e].type)
                values.put(DBHandler.idItemResource, place[i][e].idItemResource)
                if(tryingPlace() < map.y * map.x)DataBase.addPlace(values)
                else DataBase.updatePlace(values, i * map.y + e + 1)
            }
        }
    }

    private fun unloadResourcePlace(){
        var values = ContentValues()
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                values = ContentValues()
                values.put(DBHandler.tree, resourcePlace[i][e].tree)
                values.put(DBHandler.stone, resourcePlace[i][e].stone)
                values.put(DBHandler.iron, resourcePlace[i][e].iron)
                values.put(DBHandler.food, resourcePlace[i][e].food)
                values.put(DBHandler.water, resourcePlace[i][e].water)
                if(tryingResource() < map.y * map.x + 1)DataBase.addItemResource(values)
                else DataBase.updateItemResource(values, i * map.y + e + 2)
            }
        }
    }

    fun unloadSettings(){
        var values = ContentValues()
        values.put(DBHandler.backDialog, settings.backDialog)
        values.put(DBHandler.nextTurnDialog, settings.nextTurnDialog)
        if(tryingSettings() == 0)DataBase.addSettings(values)
        else DataBase.updateSettings(values, 1)
    }

    fun downloadSettings(){
        var settingsList = DataBase.SettingsList("%")
        settings  = settingsList[0]
    }

    fun trying(): Int{
        var status : Int
        var cityList = DataBase.CityList("%")
        status = cityList.size
        return status
    }
    private fun tryingCity(): Int{
        var status : Int
        var cityList = DataBase.CityList("%")
        status = cityList.size
        return status
    }
    fun tryingSettings(): Int{
        var status : Int
        var settingsList = DataBase.SettingsList("%")
        status = settingsList.size
        return status
    }
    private fun tryingInventory(): Int{
        var status : Int
        var invetoryList = DataBase.InventoryList("%")
        status = invetoryList.size
        return status
    }
    private fun tryingProtection(): Int{
        var status : Int
        var protectionList = DataBase.ItemProtectionList("%")
        status = protectionList.size
        return status
    }
    private fun tryingResource(): Int{
        var status : Int
        var resourceList = DataBase.ItemResourceList("%")
        status = resourceList.size
        return status
    }
    private fun tryingWeapon(): Int{
        var status : Int
        var weaponList = DataBase.ItemWeaponList("%")
        status = weaponList.size
        //Log.d("FLAG_TAG", "weaponList.size = ${weaponList.size}")
        return status
    }
    private fun tryingMap(): Int{
        var status : Int
        var mapList = DataBase.MapInfo("%")
        status = mapList.size
        return status
    }
    private fun tryingPlace(): Int{
        var status : Int
        var placeList = DataBase.PlaceList("%")
        status = placeList.size
        return status
    }
    fun city(): CityData{
        return city
    }
    fun inventory(): InventoryData{
        return inventory
    }
    fun protection(): ItemProtection{
        return protection
    }
    fun resource(): ItemResource{
        return resource
    }
    fun weapon(): ItemWeapon{
        return weapon
    }
    fun settings(): Settings{
        return settings
    }
}