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
            place[i][e].idItemResource = i * map.y + e;
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (0..5).random()
            resourcePlace[i][e].stone = (0..5).random()
            resourcePlace[i][e].iron = (0..0).random()
            resourcePlace[i][e].food = (5..15).random()
            resourcePlace[i][e].water = (0..10).random()
        }
        else if(stat == 2){
            place[i][e].type = "forest"
            place[i][e].idItemResource = i * map.y + e;
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (5..15).random()
            resourcePlace[i][e].stone = (0..0).random()
            resourcePlace[i][e].iron = (0..0).random()
            resourcePlace[i][e].food = (0..10).random()
            resourcePlace[i][e].water = (0..5).random()
        }
        else if(stat == 3){
            place[i][e].type = "lake"
            place[i][e].idItemResource = i * map.y + e;
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (0..0).random()
            resourcePlace[i][e].stone = (0..10).random()
            resourcePlace[i][e].iron = (0..5).random()
            resourcePlace[i][e].food = (5..10).random()
            resourcePlace[i][e].water = (5..10).random()
        }
        else if(stat == 4){
            place[i][e].type = "sea"
            place[i][e].idItemResource = i * map.y + e;
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (0..0).random()
            resourcePlace[i][e].stone = (0..10).random()
            resourcePlace[i][e].iron = (0..5).random()
            resourcePlace[i][e].food = (5..10).random()
            resourcePlace[i][e].water = (5..15).random()
        }
        else if(stat == 5){
            place[i][e].type = "desert"
            place[i][e].idItemResource = i * map.y + e;
            resourcePlace[i][e].id = i * map.y + e
            resourcePlace[i][e].tree = (0..5).random()
            resourcePlace[i][e].stone = (0..5).random()
            resourcePlace[i][e].iron = (0..10).random()
            resourcePlace[i][e].food = (0..5).random()
            resourcePlace[i][e].water = (0..0).random()
        }
        else{
            place[i][e].type = "mountain"
            place[i][e].idItemResource = i * map.y + e;
            resourcePlace[i][e].id = i * map.y + e
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
                map.idPlace[i].add(i * map.y + e)
                resourcePlace[i].add(ItemResource())
                place[i].add(Place())
                initPlace(i, e)
            }
        }
    }

    fun init(){
        city.id = 0
        city.name = "Home"
        city.hp = 10
        city.type = "village"
        city.active = 1
        city.people = 1
        city.damage = 0
        city.idInventory = 0

        inventory.id = 0
        inventory.idItemProtection = 0
        inventory.idItemResource = 0
        inventory.idItemWeapon = 0

        protection.id = 0
        protection.slot = 0

        resource.id = 0
        resource.tree = 0
        resource.stone = 0
        resource.iron = 0
        resource.food = 2
        resource.water = 3

        weapon.id = 0
        weapon.storage = mutableListOf()
        weapon.slots = mutableListOf()

        settings.id = 0
        settings.backDialog = 0
        settings.nextTurnDialog = 0

        initMap()
    }

    fun download(){
        var cityList = DataBase.CityList("%")
        city = cityList[0]
        var inventoryList = DataBase.InventoryList("%")
        inventory = inventoryList[0]
        var protectionList = DataBase.ItemProtectionList("%")
        protection = protectionList[0]
        var resourceList = DataBase.ItemResourceList("%")
        resource = resourceList[0]
        var weaponList = DataBase.ItemWeaponList("%")
        weapon = weaponList[0]
        var settingsList = DataBase.SettingsList("%")
        settings  = settingsList[0]
        var _map = DataBase.MapInfo("%")
        map = _map
        var placeList = DataBase.PlaceList("%")
        for(i in 0 until map.y){
            for(e in 0 until map.x){
                place[i][e] = placeList[i * map.y + e]
                resourcePlace[i][e] = resourceList[place[i][e].idItemResource]
            }
        }
    }
    fun unload(){
        DataBase.removeCity(0)
        DataBase.removeInventoryData(0)
        DataBase.removeItemProtection(0)
        DataBase.removeItemResource(0)
        DataBase.removeItemWeapon(0)
        DataBase.removeSettings(0)

        var values = ContentValues()
        values.put(DBHandler.name, city.name)
        values.put(DBHandler.hp, city.hp)
        values.put(DBHandler.type, city.type)
        values.put(DBHandler.active, city.active)
        values.put(DBHandler.people, city.people)
        values.put(DBHandler.damage, city.damage)
        values.put(DBHandler.idInventory, city.idInventory)
        DataBase.addCity(values)

        values = ContentValues()
        values.put(DBHandler.idItemResource, inventory.idItemResource)
        values.put(DBHandler.idItemProtection, inventory.idItemProtection)
        values.put(DBHandler.idItemWeapon, inventory.idItemWeapon)
        DataBase.addInventoryData(values)

        values = ContentValues()
        values.put(DBHandler.slot, protection.slot)
        DataBase.addItemProtection(values)

        values = ContentValues()
        values.put(DBHandler.tree, resource.tree)
        values.put(DBHandler.stone, resource.stone)
        values.put(DBHandler.iron, resource.iron)
        values.put(DBHandler.food, resource.food)
        values.put(DBHandler.water, resource.water)
        DataBase.addItemResource(values)

        values = ContentValues()
        var _slots : String = ""
        for(i in 0 until weapon.slots.size)_slots += weapon.slots[0].toString() + " "
        values.put(DBHandler.slots, _slots)
        var _storage : String = ""
        for (i in 0 until weapon.storage.size)_storage += weapon.storage[0].toString() + " "
        values.put(DBHandler.storage, _storage)
        DataBase.addItemWeapon(values)

        values = ContentValues()
        values.put(DBHandler.backDialog, settings.backDialog)
        values.put(DBHandler.nextTurnDialog, settings.nextTurnDialog)
        DataBase.addSettings(values)


    }

    fun unloadSettings(){
        var values = ContentValues()
        values.put(DBHandler.backDialog, settings.backDialog)
        values.put(DBHandler.nextTurnDialog, settings.nextTurnDialog)
        if(tryingSettings() == 0)DataBase.addSettings(values)
        else DataBase.updateSettings(values, 0)
    }

    fun downloadSettings(){
        var settingsList = DataBase.SettingsList("%")
        settings  = settingsList[0]
        Log.d("FLAG_TAG", "${settings.backDialog} ${settings.nextTurnDialog}")
    }

    fun trying(): Int{
        var status : Int
        var cityList = DataBase.CityList("%")
        status = cityList.size
        return status
    }
    fun tryingSettings(): Int{
        var status : Int
        var settingsList = DataBase.SettingsList("%")
        status = settingsList.size
        Log.d("FLAG_TAG", "size = $status")
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