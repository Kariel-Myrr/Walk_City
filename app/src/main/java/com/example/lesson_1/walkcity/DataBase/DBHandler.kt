package com.example.lesson_1.walkcity.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import com.example.lesson_1.walkcity.DataBase.DBHandler.Companion.SettingsName
import com.example.lesson_1.walkcity.DataBase.DBHandler.Companion.backDialog
import com.example.lesson_1.walkcity.DataBase.DBHandler.Companion.id
import com.example.lesson_1.walkcity.DataBase.DBHandler.Companion.nextTurnDialog

class DBHandler(context: Context) : SQLiteOpenHelper(context, DBName, null, DBVersion) {

    companion object {
        val DBName = "UsersDB" // Название БД
        val DBVersion = 1 // Версия БД

        var CityDataName = "CityData"
        var InventoryDataName = "InventoryData"
        var ItemResourceName = "ItemResource"
        var ItemWeaponName = "ItemWeapon"
        var ItemProtectionName = "ItemProtection"
        var MapName = "Map"
        var PlaceName = "Place"
        var SettingsName = "Settings"


        var id = "id"
        var name = "name"
        var hp = "hp"
        var type = "type"
        var active = "active"
        var people = "people"
        var damage = "damage"
        var idInventory = "idInventory"

        var idItemResource = "idItemResource"
        var idItemWeapon = "idItemWeapon"
        var idItemProtection = "idItemProtection"

        var tree = "tree"
        var stone = "stone"
        var iron = "iron"
        var food = "food"
        var water = "water"

        var slots = "slots"
        var storage = "storage"

        var slot = "slot"

        var x = "x"
        var y = "y"
        var idPlace = "idPlace"

        var backDialog = "backDialog"
        var nextTurnDialog = "nextTurnDialog"
    }

    var sqlObj: SQLiteDatabase = this.writableDatabase // Сущность SQLiteDatabase

    override fun onCreate(p0: SQLiteDatabase?) { // Вызывается при генерации БД
        var sql1 = "CREATE TABLE IF NOT EXISTS $CityDataName ( $id  INTEGER PRIMARY KEY, $name TEXT, $hp TEXT, $type TEXT, $active TEXT, $people TEXT, $damage TEXT, $idInventory TEXT);"
        p0!!.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $InventoryDataName ( $id  INTEGER PRIMARY KEY, $idItemResource TEXT, $idItemWeapon TEXT, $idItemProtection TEXT);"
        p0!!.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $ItemResourceName ( $id  INTEGER PRIMARY KEY, $tree TEXT, $stone TEXT, $iron TEXT, $food TEXT, $water TEXT);"
        p0!!.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $ItemWeaponName ( $id  INTEGER PRIMARY KEY, $slots TEXT, $storage TEXT);"
        p0!!.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $ItemProtectionName ( $id  INTEGER PRIMARY KEY, $slot TEXT);"
        p0!!.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $MapName ( $id  INTEGER PRIMARY KEY, $x TEXT, $y TEXT, $idPlace TEXT);"
        p0!!.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $PlaceName ( $id  INTEGER PRIMARY KEY, $type TEXT, $idItemResource TEXT);"
        p0!!.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $SettingsName ( $id  INTEGER PRIMARY KEY, $backDialog TEXT, $nextTurnDialog TEXT);"
        p0!!.execSQL(sql1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { // Вызывается при обновлении версии БД
        p0!!.execSQL("Drop table IF EXISTS $CityDataName")
        p0!!.execSQL("Drop table IF EXISTS $InventoryDataName")
        p0!!.execSQL("Drop table IF EXISTS $ItemResourceName")
        p0!!.execSQL("Drop table IF EXISTS $ItemWeaponName")
        p0!!.execSQL("Drop table IF EXISTS $ItemProtectionName")
        p0!!.execSQL("Drop table IF EXISTS $MapName")
        p0!!.execSQL("Drop table IF EXISTS $PlaceName")
        p0!!.execSQL("Drop table IF EXISTS $SettingsName")
        onCreate(p0)
    }

    fun addCity(values: ContentValues) = sqlObj.insert(CityDataName, "", values)
    fun addInventoryData(values: ContentValues) = sqlObj.insert(InventoryDataName, "", values)
    fun addItemResource(values: ContentValues) = sqlObj.insert(ItemResourceName, "", values)
    fun addItemWeapon(values: ContentValues) = sqlObj.insert(ItemWeaponName, "", values)
    fun addItemProtection(values: ContentValues) = sqlObj.insert(ItemProtectionName, "", values)
    fun addMap(values: ContentValues) = sqlObj.insert(MapName, "", values)
    fun addPlace(values: ContentValues) = sqlObj.insert(PlaceName, "", values)
    fun addSettings(values: ContentValues) = sqlObj.insert(SettingsName, "", values)

    fun removeCity(id: Int) = sqlObj.delete(CityDataName, "id=?", arrayOf(id.toString()))
    fun removeInventoryData(id: Int) = sqlObj.delete(InventoryDataName, "id=?", arrayOf(id.toString()))
    fun removeItemResource(id: Int) = sqlObj.delete(ItemResourceName, "id=?", arrayOf(id.toString()))
    fun removeItemWeapon(id: Int) = sqlObj.delete(ItemWeaponName, "id=?", arrayOf(id.toString()))
    fun removeItemProtection(id: Int) = sqlObj.delete(ItemProtectionName, "id=?", arrayOf(id.toString()))
    fun removeMap(id: Int) = sqlObj.delete(MapName, "id=?", arrayOf(id.toString()))
    fun removePlace(id: Int) = sqlObj.delete(PlaceName, "id=?", arrayOf(id.toString()))
    fun removeSettings(id: Int) = sqlObj.delete(SettingsName, "id=?", arrayOf(id.toString()))

    fun updateCity(values: ContentValues, id: Int) = sqlObj.update(CityDataName, values, "id=?", arrayOf(id.toString()))
    fun updateInventoryData(values: ContentValues, id: Int) = sqlObj.update(InventoryDataName, values, "id=?", arrayOf(id.toString()))
    fun updateItemResource(values: ContentValues, id: Int) = sqlObj.update(ItemResourceName, values, "id=?", arrayOf(id.toString()))
    fun updateItemWeapon(values: ContentValues, id: Int) = sqlObj.update(ItemWeaponName, values, "id=?", arrayOf(id.toString()))
    fun updateItemProtection(values: ContentValues, id: Int) = sqlObj.update(ItemProtectionName, values, "id=?", arrayOf(id.toString()))
    fun updateMap(values: ContentValues, id: Int) = sqlObj.update(MapName, values, "id=?", arrayOf(id.toString()))
    fun updatePlace(values: ContentValues, id: Int) = sqlObj.update(PlaceName, values, "id=?", arrayOf(id.toString()))
    fun updateSettings(values: ContentValues, id: Int) = sqlObj.update(SettingsName, values, "id=?", arrayOf(id.toString()))

    fun CityList(key: String): ArrayList<CityData> {
        var arraylist = ArrayList<CityData>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = CityDataName
        var cols = arrayOf(id, name, hp, type, active, people, damage, idInventory)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val name = cursor.getString(cursor.getColumnIndex(name))
                val hp = cursor.getInt(cursor.getColumnIndex(hp))
                val type = cursor.getString(cursor.getColumnIndex(type))
                val active = cursor.getInt(cursor.getColumnIndex(active))
                val people = cursor.getInt(cursor.getColumnIndex(people))
                val damage = cursor.getInt(cursor.getColumnIndex(damage))
                val idInventory = cursor.getInt(cursor.getColumnIndex(idInventory))

                arraylist.add(CityData(id, name, hp, type, active, people, damage, idInventory))

            } while (cursor.moveToNext())
        }
        return arraylist
    }

    fun InventoryList(key: String): ArrayList<InventoryData> {
        var arraylist = ArrayList<InventoryData>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = InventoryDataName
        var cols = arrayOf(id, idItemResource, idItemWeapon, idItemProtection)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val idItemResource = cursor.getInt(cursor.getColumnIndex(idItemResource))
                val idItemWeapon = cursor.getInt(cursor.getColumnIndex(idItemWeapon))
                val idItemProtection = cursor.getInt(cursor.getColumnIndex(idItemProtection))

                arraylist.add(InventoryData(id, idItemResource, idItemWeapon, idItemProtection))

            } while (cursor.moveToNext())
        }
        return arraylist
    }

    fun ItemWeaponList(key: String): ArrayList<ItemWeapon> {
        var arraylist = ArrayList<ItemWeapon>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = ItemWeaponName
        var cols = arrayOf(id, slots, storage)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val slots = cursor.getString(cursor.getColumnIndex(slots))
                val storage = cursor.getString(cursor.getColumnIndex(storage))
                //Log.d("FLAG_TAG", "string slots = $slots")
                //Log.d("FLAG_TAG", "string storage = $storage")
                //val _slots: MutableList<Int> = mutableListOf(slots.split(" ") as Int)
                //val _storage: MutableList<Int> = mutableListOf(storage.split(" ") as Int)
                var slotsList: MutableList<String> = mutableListOf()
                var storageList: MutableList<String> = mutableListOf()
                for(str in slots.split(" "))slotsList.add(str)
                for(str in storage.split(" "))storageList.add(str)
                //Log.d("FLAG_TAG", "slotsList.size = ${slotsList.size} storageList.size = ${storageList.size}")
                if(slotsList.size == 1)if(slotsList[0] == "")slotsList.clear()
                if(storageList.size == 1)if(storageList[0] == "")storageList.clear()
                //Log.d("FLAG_TAG", "slotsList.size = ${slotsList.size} storageList.size = ${storageList.size}")
                var _slots : MutableList<Int> = mutableListOf()
                val _storage: MutableList<Int> = mutableListOf()
                for(i in 0 until slotsList.size)_slots.add(slotsList[i].toInt())
                for(i in 0 until storageList.size)_storage.add(storageList[i].toInt())
                //Log.d("FLAG_TAG", "_slots.size = ${_slots.size} _storage.size = ${_storage.size}")
                arraylist.add(ItemWeapon(id, _slots, _storage))
                //Log.d("FLAG_TAG", "Ok")
            } while (cursor.moveToNext())
        }
        //Log.d("FLAG_TAG", "exit ItemWeaponList arraylist.size = ${arraylist.size}")
        return arraylist
    }

    fun ItemResourceList(key: String): ArrayList<ItemResource> {
        var arraylist = ArrayList<ItemResource>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = ItemResourceName
        var cols = arrayOf(id, tree, stone, iron, food, water)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val tree = cursor.getInt(cursor.getColumnIndex(tree))
                val stone = cursor.getInt(cursor.getColumnIndex(stone))
                val iron = cursor.getInt(cursor.getColumnIndex(iron))
                val food = cursor.getInt(cursor.getColumnIndex(food))
                val water = cursor.getInt(cursor.getColumnIndex(water))

                arraylist.add(ItemResource(id, tree, stone, iron, food, water))

            } while (cursor.moveToNext())
        }
        return arraylist
    }


    fun ItemProtectionList(key: String): ArrayList<ItemProtection> {
        var arraylist = ArrayList<ItemProtection>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = ItemProtectionName
        var cols = arrayOf(id, slot)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val slot = cursor.getInt(cursor.getColumnIndex(slot))

                arraylist.add(ItemProtection(id, slot))

            } while (cursor.moveToNext())
        }
        return arraylist
    }


    fun SettingsList(key: String): ArrayList<Settings> {
        var arraylist = ArrayList<Settings>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = SettingsName
        var cols = arrayOf(id, backDialog, nextTurnDialog)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val backDialog = cursor.getInt(cursor.getColumnIndex(backDialog))
                val nextTurnDialog = cursor.getInt(cursor.getColumnIndex(nextTurnDialog))

                arraylist.add(Settings(id, backDialog, nextTurnDialog))

            } while (cursor.moveToNext())
        }
        return arraylist
    }

    fun MapInfo(key: String): ArrayList<Map> {
        //Log.d("FLAG_TAG", "MapInfo")
        var arraylist = ArrayList<Map>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = MapName
        var cols = arrayOf(x, y, idPlace)
        var selectArgs = arrayOf(key)

        var cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        //Log.d("FLAG_TAG", "mapinfo test 1")
        if (cursor.moveToFirst()) {
            do {
                //Log.d("FLAG_TAG", "mapinfo test 2")
                val x = cursor.getInt(cursor.getColumnIndex(x))
                val y = cursor.getInt(cursor.getColumnIndex(y))
                //Log.d("FLAG_TAG", "x = $x y = $y")
                val str = cursor.getString(cursor.getColumnIndex(idPlace))
                //Log.d("FLAG_TAG", "mapinfo test 2.5")
                val list : MutableList<String> = mutableListOf()
                //Log.d("FLAG_TAG", "mapinfo test 3 str = '$str'")
                for(a in str.split(" "))list.add(a)
                list.removeAt(25)
                //Log.d("FLAG_TAG", "mapinfo test 3.5 list.size = ${list.size}")
                var idPlace : MutableList<MutableList<Int>> = mutableListOf()
                //Log.d("FLAG_TAG", "mapinfo test 4")
                for(i in 0 until y){
                    idPlace.add(mutableListOf())
                    for(e in 0 until x){
                        idPlace[i].add(list[i * y + e].toInt())
                    }
                }
                //Log.d("FLAG_TAG", "mapinfo test 5")
                arraylist.add(Map(x, y, idPlace))

            } while (cursor.moveToNext())
        }
        //Log.d("FLAG_TAG", "mapinfo test 6 arraylist.size = ${arraylist.size}")
        return arraylist
    }

    fun PlaceList(key: String): ArrayList<Place> {
        //Log.d("FLAG_TAG", "PlaceList test 1")
        var arraylist = ArrayList<Place>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = PlaceName
        //Log.d("FLAG_TAG", "PlaceList test 2")
        var cols = arrayOf(type, idItemResource)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        //Log.d("FLAG_TAG", "PlaceList test 3")
        if (cursor.moveToFirst()) {
            do {
                val type = cursor.getString(cursor.getColumnIndex(type))
                val idItemResource = cursor.getInt(cursor.getColumnIndex(idItemResource))
                arraylist.add(Place(type, idItemResource))

            } while (cursor.moveToNext())
        }
        //Log.d("FLAG_TAG", "PlaceList test 4 arraylist.size = ${arraylist.size}")
        return arraylist
    }
}