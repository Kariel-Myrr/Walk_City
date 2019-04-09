package cool_guys.walkcity.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log

class DBHandler(context: Context) : SQLiteOpenHelper(context, DBName, null, DBVersion) {

    companion object {
        const val DBName = "UsersDB" // Название БД
        const val DBVersion = 1 // Версия БД

        var CityDataName = "CityData"
        var InventoryDataName = "InventoryData"
        var ItemResourceName = "ItemResource"
        var ItemWeaponName = "ItemWeapon"
        var ItemProtectionName = "ItemProtection"
        var MapName = "Map"
        var TileName = "Tile"
        var SettingsName = "Settings"


        var id = "id"
        var name = "name"
        var hp = "hp"
        var type = "type"
        var active = "active"
        var people = "people"
        var damage = "damage"
        var protection = "protection"
        var idInventory = "idInventory"

        var idItemResource = "idItemResource"
        var idItemWeapon = "idItemWeapon"
        var idItemProtection = "idItemProtection"

        var wood = "wood"
        var stone = "stone"
        var iron = "iron"
        var food = "food"
        var fuel = "fuel"

        var slots = "slots"
        var storage = "storage"

        var slot = "slot"

        var x = "x"
        var y = "y"
        var idTile = "idTile"

        var backDialog = "backDialog"
        var nextTurnDialog = "nextTurnDialog"
    }

    private var sqlObj: SQLiteDatabase = this.writableDatabase // Сущность SQLiteDatabase

    override fun onCreate(p0: SQLiteDatabase?) { // Вызывается при генерации БД
        var sql1 = "CREATE TABLE IF NOT EXISTS $CityDataName ( $id  INTEGER PRIMARY KEY, $name TEXT, $hp TEXT, $type TEXT, $active TEXT, $people TEXT, $damage TEXT, $protection Text, $idInventory TEXT, $x Text, $y Text);"
        p0!!.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $InventoryDataName ( $id  INTEGER PRIMARY KEY, $idItemResource TEXT, $idItemWeapon TEXT, $idItemProtection TEXT);"
        p0.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $ItemResourceName ( $id  INTEGER PRIMARY KEY, $wood TEXT, $stone TEXT, $iron TEXT, $food TEXT, $fuel TEXT, $people TEXT);"
        p0.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $ItemWeaponName ( $id  INTEGER PRIMARY KEY, $slots TEXT, $storage TEXT);"
        p0.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $ItemProtectionName ( $id  INTEGER PRIMARY KEY, $slot TEXT);"
        p0.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $MapName ( $id  INTEGER PRIMARY KEY, $x TEXT, $y TEXT, $idTile TEXT);"
        p0.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $TileName ( $id  INTEGER PRIMARY KEY, $type TEXT, $idItemResource TEXT);"
        p0.execSQL(sql1)
        sql1 = "CREATE TABLE IF NOT EXISTS $SettingsName ( $id  INTEGER PRIMARY KEY, $backDialog TEXT, $nextTurnDialog TEXT);"
        p0.execSQL(sql1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { // Вызывается при обновлении версии БД
        p0!!.execSQL("Drop table IF EXISTS $CityDataName")
        p0.execSQL("Drop table IF EXISTS $InventoryDataName")
        p0.execSQL("Drop table IF EXISTS $ItemResourceName")
        p0.execSQL("Drop table IF EXISTS $ItemWeaponName")
        p0.execSQL("Drop table IF EXISTS $ItemProtectionName")
        p0.execSQL("Drop table IF EXISTS $MapName")
        p0.execSQL("Drop table IF EXISTS $TileName")
        p0.execSQL("Drop table IF EXISTS $SettingsName")
        onCreate(p0)
    }

    fun addCity(values: ContentValues) = sqlObj.insert(CityDataName, "", values)
    fun addInventoryData(values: ContentValues) = sqlObj.insert(InventoryDataName, "", values)
    fun addItemResource(values: ContentValues) = sqlObj.insert(ItemResourceName, "", values)
    fun addItemWeapon(values: ContentValues) = sqlObj.insert(ItemWeaponName, "", values)
    fun addItemProtection(values: ContentValues) = sqlObj.insert(ItemProtectionName, "", values)
    fun addMap(values: ContentValues) = sqlObj.insert(MapName, "", values)
    fun addTile(values: ContentValues) = sqlObj.insert(TileName, "", values)
    fun addSettings(values: ContentValues) = sqlObj.insert(SettingsName, "", values)

    //fun removeCity(id: Int) = sqlObj.delete(CityDataName, "id=?", arrayOf(id.toString()))
    //fun removeInventoryData(id: Int) = sqlObj.delete(InventoryDataName, "id=?", arrayOf(id.toString()))
    //fun removeItemResource(id: Int) = sqlObj.delete(ItemResourceName, "id=?", arrayOf(id.toString()))
    //fun removeItemWeapon(id: Int) = sqlObj.delete(ItemWeaponName, "id=?", arrayOf(id.toString()))
    //fun removeItemProtection(id: Int) = sqlObj.delete(ItemProtectionName, "id=?", arrayOf(id.toString()))
    //fun removeMap(id: Int) = sqlObj.delete(MapName, "id=?", arrayOf(id.toString()))
    //fun removeTile(id: Int) = sqlObj.delete(TileName, "id=?", arrayOf(id.toString()))
    //fun removeSettings(id: Int) = sqlObj.delete(SettingsName, "id=?", arrayOf(id.toString()))

    fun updateCity(values: ContentValues, id: Int) = sqlObj.update(CityDataName, values, "id=?", arrayOf(id.toString()))
    fun updateInventoryData(values: ContentValues, id: Int) = sqlObj.update(InventoryDataName, values, "id=?", arrayOf(id.toString()))
    fun updateItemResource(values: ContentValues, id: Int) = sqlObj.update(ItemResourceName, values, "id=?", arrayOf(id.toString()))
    fun updateItemWeapon(values: ContentValues, id: Int) = sqlObj.update(ItemWeaponName, values, "id=?", arrayOf(id.toString()))
    fun updateItemProtection(values: ContentValues, id: Int) = sqlObj.update(ItemProtectionName, values, "id=?", arrayOf(id.toString()))
    fun updateMap(values: ContentValues, id: Int) = sqlObj.update(MapName, values, "id=?", arrayOf(id.toString()))
    fun updateTile(values: ContentValues, id: Int) = sqlObj.update(TileName, values, "id=?", arrayOf(id.toString()))
    fun updateSettings(values: ContentValues, id: Int) = sqlObj.update(SettingsName, values, "id=?", arrayOf(id.toString()))

    fun cityList(key: String): ArrayList<CityData> {
        //Log.d("FLAG_TAG", "DB test 1")
        val arraylist = ArrayList<CityData>()
        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = CityDataName
        val cols = arrayOf(id, name, hp, type, active, people, damage, protection, idInventory, x, y)
        val selectArgs = arrayOf(key)
        val cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        //Log.d("FLAG_TAG", "DB test 2")
        if (cursor.moveToFirst()) {
            //Log.d("FLAG_TAG", "DB test 3")
            do {
                //Log.d("FLAG_TAG", "DB test 4")
                val id = cursor.getInt(cursor.getColumnIndex(id))
                //Log.d("FLAG_TAG", "DB test 4.1")
                val name = cursor.getString(cursor.getColumnIndex(name))
                //Log.d("FLAG_TAG", "DB test 4.2")
                val hp = cursor.getInt(cursor.getColumnIndex(hp))
                //Log.d("FLAG_TAG", "DB test 4.3")
                val type = cursor.getString(cursor.getColumnIndex(type))
                //Log.d("FLAG_TAG", "DB test 4.4")
                val active = cursor.getInt(cursor.getColumnIndex(active))
                //Log.d("FLAG_TAG", "DB test 4.5")
                val people = cursor.getInt(cursor.getColumnIndex(people))
                //Log.d("FLAG_TAG", "DB test 4.6")
                val damage = cursor.getInt(cursor.getColumnIndex(damage))
                //Log.d("FLAG_TAG", "DB test 4.7")
                val protection = cursor.getInt(cursor.getColumnIndex(protection))
                //Log.d("FLAG_TAG", "DB test 4.8")
                val idInventory = cursor.getInt(cursor.getColumnIndex(idInventory))
                //Log.d("FLAG_TAG", "DB test 4.9")
                val x = cursor.getInt(cursor.getColumnIndex(x))
                //Log.d("FLAG_TAG", "DB test 4.10")
                val y = cursor.getInt(cursor.getColumnIndex(y))
                //Log.d("FLAG_TAG", "DB test 5")
                arraylist.add(CityData(id, name, hp, type, active, people, damage, protection, idInventory, x, y))
                //Log.d("FLAG_TAG", "DB test 6")
            } while (cursor.moveToNext())
            //Log.d("FLAG_TAG", "DB test 7")
        }
        //Log.d("FLAG_TAG", "DB test 8")
        return arraylist
    }

    fun inventoryList(key: String): ArrayList<InventoryData> {
        val arraylist = ArrayList<InventoryData>()
        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = InventoryDataName
        val cols = arrayOf(id, idItemResource, idItemWeapon, idItemProtection)
        val selectArgs = arrayOf(key)
        val cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
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

    fun itemWeaponList(key: String): ArrayList<ItemWeapon> {
        val arraylist = ArrayList<ItemWeapon>()
        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = ItemWeaponName
        val cols = arrayOf(id, slots, storage)
        val selectArgs = arrayOf(key)
        val cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val slots = cursor.getString(cursor.getColumnIndex(slots))
                val storage = cursor.getString(cursor.getColumnIndex(storage))
                val slotsList: MutableList<String> = mutableListOf()
                val storageList: MutableList<String> = mutableListOf()
                for(str in slots.split(" "))slotsList.add(str)
                for(str in storage.split(" "))storageList.add(str)
                slotsList.removeAt(slotsList.size - 1)
                storageList.removeAt(storageList.size - 1)
                val tmpSlots : MutableList<Int> = mutableListOf()
                val tmpStorage: MutableList<Int> = mutableListOf()
                for(i in 0 until slotsList.size)tmpSlots.add(slotsList[i].toInt())
                for(i in 0 until storageList.size)tmpStorage.add(storageList[i].toInt())
                arraylist.add(ItemWeapon(id, tmpSlots, tmpStorage))
            } while (cursor.moveToNext())
        }
        return arraylist
    }

    fun itemResourceList(key: String): ArrayList<ItemResource> {
        val arraylist = ArrayList<ItemResource>()
        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = ItemResourceName
        val cols = arrayOf(id, wood, stone, iron, food, fuel, people)
        val selectArgs = arrayOf(key)
        val cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val wood = cursor.getInt(cursor.getColumnIndex(wood))
                val stone = cursor.getInt(cursor.getColumnIndex(stone))
                val iron = cursor.getInt(cursor.getColumnIndex(iron))
                val food = cursor.getInt(cursor.getColumnIndex(food))
                val fuel = cursor.getInt(cursor.getColumnIndex(fuel))
                val people = cursor.getInt(cursor.getColumnIndex(people))

                arraylist.add(ItemResource(id, wood, stone, iron, food, fuel, people))

            } while (cursor.moveToNext())
        }
        return arraylist
    }


    fun itemProtectionList(key: String): ArrayList<ItemProtection> {
        val arraylist = ArrayList<ItemProtection>()
        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = ItemProtectionName
        val cols = arrayOf(id, slot)
        val selectArgs = arrayOf(key)
        val cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(id))
                val slot = cursor.getInt(cursor.getColumnIndex(slot))

                arraylist.add(ItemProtection(id, slot))

            } while (cursor.moveToNext())
        }
        return arraylist
    }


    fun settingsList(key: String): ArrayList<Settings> {
        val arraylist = ArrayList<Settings>()
        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = SettingsName
        val cols = arrayOf(id, backDialog, nextTurnDialog)
        val selectArgs = arrayOf(key)
        val cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
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

    fun mapInfo(key: String): ArrayList<Map> {
        //Log.d("FLAG_TAG", "MapInfo")
        val arraylist = ArrayList<Map>()
        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = MapName
        val cols = arrayOf(x, y, idTile)
        val selectArgs = arrayOf(key)

        val cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        //Log.d("FLAG_TAG", "mapinfo test 1")
        if (cursor.moveToFirst()) {
            do {
                //Log.d("FLAG_TAG", "mapinfo test 2")
                val x = cursor.getInt(cursor.getColumnIndex(x))
                val y = cursor.getInt(cursor.getColumnIndex(y))
                //Log.d("FLAG_TAG", "x = $x y = $y")
                val str = cursor.getString(cursor.getColumnIndex(idTile))
                //Log.d("FLAG_TAG", "mapinfo test 2.5")
                val list : MutableList<String> = mutableListOf()
                //Log.d("FLAG_TAG", "mapinfo test 3 str = '$str'")
                for(a in str.split(" "))list.add(a)
                list.removeAt(36)
                //Log.d("FLAG_TAG", "mapinfo test 3.5 list.size = ${list.size}")
                val idTile : MutableList<MutableList<Int>> = mutableListOf()
                //Log.d("FLAG_TAG", "mapinfo test 4")
                for(i in 0 until y){
                    idTile.add(mutableListOf())
                    for(e in 0 until x){
                        idTile[i].add(list[i * y + e].toInt())
                    }
                }
                //Log.d("FLAG_TAG", "mapinfo test 5")
                arraylist.add(Map(x, y, idTile))

            } while (cursor.moveToNext())
        }
        //Log.d("FLAG_TAG", "mapinfo test 6 arraylist.size = ${arraylist.size}")
        return arraylist
    }

    fun tileList(key: String): ArrayList<Tile> {
        //Log.d("FLAG_TAG", "TileList test 1")
        val arraylist = ArrayList<Tile>()
        val sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = TileName
        //Log.d("FLAG_TAG", "TileList test 2")
        val cols = arrayOf(type, idItemResource)
        val selectArgs = arrayOf(key)
        val cursor = sqlQB.query(sqlObj, cols, "$id like ?", selectArgs, null, null, id)
        //Log.d("FLAG_TAG", "TileList test 3")
        if (cursor.moveToFirst()) {
            do {
                val type = cursor.getString(cursor.getColumnIndex(type))
                val idItemResource = cursor.getInt(cursor.getColumnIndex(idItemResource))
                arraylist.add(Tile(type, idItemResource))

            } while (cursor.moveToNext())
        }
        //Log.d("FLAG_TAG", "TileList test 4 arraylist.size = ${arraylist.size}")
        return arraylist
    }
}