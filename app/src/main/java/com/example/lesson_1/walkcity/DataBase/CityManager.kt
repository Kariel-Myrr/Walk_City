package com.example.lesson_1.walkcity.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteQueryBuilder

class CityManager(context: Context, stat: Int = 0){
    var city = CityData()
    var inventory = InventoryData()
    var protection = ItemProtection()
    var resource = ItemResource()
    var weapon = ItemWeapon()
    var DataBase = DBHandler(context)
    init{
        if(stat != 0){
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
        }
        else{
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
        }
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
    }
    fun unload(){
        DataBase.removeCity(0)
        DataBase.removeInventoryData(0)
        DataBase.removeItemProtection(0)
        DataBase.removeItemResource(0)
        DataBase.removeItemWeapon(0)

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
    }

    fun trying(): Int{
        var status : Int
        var cityList = DataBase.CityList("%")
        status = cityList.size
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
}