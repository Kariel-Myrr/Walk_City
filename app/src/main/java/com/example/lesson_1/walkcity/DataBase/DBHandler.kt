package com.example.lesson_1.walkcity.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

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
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { // Вызывается при обновлении версии БД
        p0!!.execSQL("Drop table IF EXISTS $CityDataName")
        p0!!.execSQL("Drop table IF EXISTS $InventoryDataName")
        p0!!.execSQL("Drop table IF EXISTS $ItemResourceName")
        p0!!.execSQL("Drop table IF EXISTS $ItemWeaponName")
        p0!!.execSQL("Drop table IF EXISTS $ItemProtectionName")
        p0!!.execSQL("Drop table IF EXISTS $MapName")
        p0!!.execSQL("Drop table IF EXISTS $PlaceName")
        onCreate(p0)
    }

    fun addCity(values: ContentValues) = sqlObj.insert(CityDataName, "", values)
    fun addInventoryData(values: ContentValues) = sqlObj.insert(InventoryDataName, "", values)
    fun addItemResource(values: ContentValues) = sqlObj.insert(ItemResourceName, "", values)
    fun addItemWeapon(values: ContentValues) = sqlObj.insert(ItemWeaponName, "", values)
    fun addItemProtection(values: ContentValues) = sqlObj.insert(ItemProtectionName, "", values)
    fun addMap(values: ContentValues) = sqlObj.insert(MapName, "", values)
    fun addPlace(values: ContentValues) = sqlObj.insert(PlaceName, "", values)

    fun removeCity(id: Int) = sqlObj.delete(CityDataName, "id=?", arrayOf(id.toString()))
    fun removeInventoryData(id: Int) = sqlObj.delete(InventoryDataName, "id=?", arrayOf(id.toString()))
    fun removeItemResource(id: Int) = sqlObj.delete(ItemResourceName, "id=?", arrayOf(id.toString()))
    fun removeItemWeapon(id: Int) = sqlObj.delete(ItemWeaponName, "id=?", arrayOf(id.toString()))
    fun removeItemProtection(id: Int) = sqlObj.delete(ItemProtectionName, "id=?", arrayOf(id.toString()))
    fun removeMap(id: Int) = sqlObj.delete(MapName, "id=?", arrayOf(id.toString()))
    fun removePlace(id: Int) = sqlObj.delete(PlaceName, "id=?", arrayOf(id.toString()))

    fun updateCity(values: ContentValues, id: Int) = sqlObj.update(CityDataName, values, "id=?", arrayOf(id.toString()))
    fun updateInventoryData(values: ContentValues, id: Int) = sqlObj.update(InventoryDataName, values, "id=?", arrayOf(id.toString()))
    fun updateItemResource(values: ContentValues, id: Int) = sqlObj.update(ItemResourceName, values, "id=?", arrayOf(id.toString()))
    fun updateItemWeapon(values: ContentValues, id: Int) = sqlObj.update(ItemWeaponName, values, "id=?", arrayOf(id.toString()))
    fun updateItemProtection(values: ContentValues, id: Int) = sqlObj.update(ItemProtectionName, values, "id=?", arrayOf(id.toString()))
    fun updateMap(values: ContentValues, id: Int) = sqlObj.update(MapName, values, "id=?", arrayOf(id.toString()))
    fun updatePlace(values: ContentValues, id: Int) = sqlObj.update(PlaceName, values, "id=?", arrayOf(id.toString()))

}