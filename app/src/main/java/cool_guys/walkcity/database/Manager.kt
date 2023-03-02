package cool_guys.walkcity.database

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.util.Log
import java.util.*

class Manager(val context: Context) {

    var city : MutableList<CityData> = mutableListOf()
    private var inventory : MutableList<InventoryData> = mutableListOf()
    private var protection : MutableList<ItemProtection> = mutableListOf()
    var resource : MutableList<ItemResource> = mutableListOf()
    private var weapon : MutableList<ItemWeapon> = mutableListOf()
    private var dataBase = DBHandler(context)
    private var map = Map()
    var tile : MutableList<MutableList<Tile>> = mutableListOf()
    private var resourceTile: MutableList<MutableList<ItemResource>> = mutableListOf()
    private val countCity = 10
    var settings = Settings()

    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

    private fun initTile(i: Int, e: Int) {
        //var numNull = (0..0).random()
        //var numLow = (0..5).random()
        //var numSmall = (0..10).random()
        //var numMedium = (5..10).random()
        //var numHigh = (5..15).random()
        when ((1..6).random()) {
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
                resourceTile[i][e].wood = (5..15).random()
                resourceTile[i][e].stone = (5..10).random()
                resourceTile[i][e].iron = (5..15).random()
                resourceTile[i][e].food = (0..5).random()
                resourceTile[i][e].fuel = (0..5).random()
                resourceTile[i][e].people = (0..5).random()
            }
        }
        tile[i][e].resource = resourceTile[i][e]
    }

    fun initTile(i: Int, e: Int, type: String, tree: Int, stone: Int, iron: Int, food: Int, fuel: Int) {
        tile[i][e].type = type
        resourceTile[i][e].wood = tree
        resourceTile[i][e].stone = stone
        resourceTile[i][e].iron = iron
        resourceTile[i][e].food = food
        resourceTile[i][e].fuel = fuel
    }

    private fun initMap() {
        map.x = 6
        map.y = 6
        for (i in 0 until map.y) {
            map.idTile.add(mutableListOf())
            resourceTile.add(mutableListOf())
            tile.add(mutableListOf())
            for (e in 0 until map.x) {
                map.idTile[i].add(i * map.y + e)
                val idTile = map.idTile[i][e]
                Log.d(TAG, "map[$i][$e]: idTile = $idTile")
                resourceTile[i].add(ItemResource())
                tile[i].add(Tile())
                initTile(i, e)
                val type = tile[i][e].type
                val idItemResource = tile[i][e].idItemResource
                Log.d(TAG, "tile[$i][$e]: type = $type idItemResource = $idItemResource")
                val tree = resourceTile[i][e].wood
                val stone = resourceTile[i][e].stone
                val iron = resourceTile[i][e].iron
                val food = resourceTile[i][e].food
                val fuel = resourceTile[i][e].fuel
                val people = resourceTile[i][e].people
                Log.d(TAG, "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food fuel = $fuel people = $people")
            }
        }
    }

    private fun initCity(id: Int) {
        val tmpCity = CityData()
        when (id) {
            0 -> {
                tmpCity.name = "my"
                tmpCity.hp = 10
                tmpCity.type = "town"
                tmpCity.active = 1
                tmpCity.people = 3
                tmpCity.damage = 0
                tmpCity.protection = 0
                tmpCity.idInventory = id
                tmpCity.x = 0
                tmpCity.y = 0
            }
            1 -> {
                tmpCity.name = "upfort"
                tmpCity.hp = 30
                tmpCity.type = "city"
                tmpCity.active = 1
                tmpCity.people = 25
                tmpCity.damage = 3
                tmpCity.protection = 5
                tmpCity.idInventory = id
                tmpCity.x = 1
                tmpCity.y = 1
            }
            2 -> {
                tmpCity.name = "middlefort"
                tmpCity.hp = 20
                tmpCity.type = "village"
                tmpCity.active = 1
                tmpCity.people = 15
                tmpCity.damage = 2
                tmpCity.protection = 4
                tmpCity.idInventory = id
                tmpCity.x = 3
                tmpCity.y = 0
            }
            3 -> {
                tmpCity.name = "downfort"
                tmpCity.hp = 20
                tmpCity.type = "village"
                tmpCity.active = 1
                tmpCity.people = 15
                tmpCity.damage = 2
                tmpCity.protection = 4
                tmpCity.idInventory = id
                tmpCity.x = 0
                tmpCity.y = 3
            }
            4 -> {
                tmpCity.name = "hillexp"
                tmpCity.hp = 15
                tmpCity.type = "town"
                tmpCity.active = 1
                tmpCity.people = 5
                tmpCity.damage = 2
                tmpCity.protection = 2
                tmpCity.idInventory = id
                tmpCity.x = 4
                tmpCity.y = 2
            }
            5 -> {
                tmpCity.name = "forestexp"
                tmpCity.hp = 20
                tmpCity.type = "village"
                tmpCity.active = 1
                tmpCity.people = 10
                tmpCity.damage = 4
                tmpCity.protection = 3
                tmpCity.idInventory = id
                tmpCity.x = 3
                tmpCity.y = 3
            }
            6 -> {
                tmpCity.name = "desertexp"
                tmpCity.hp = 15
                tmpCity.type = "town"
                tmpCity.active = 1
                tmpCity.people = 5
                tmpCity.damage = 2
                tmpCity.protection = 2
                tmpCity.idInventory = id
                tmpCity.x = 2
                tmpCity.y = 4
            }
            7 -> {
                tmpCity.name = "southwall"
                tmpCity.hp = 40
                tmpCity.type = "city"
                tmpCity.active = 1
                tmpCity.people = 20
                tmpCity.damage = 10
                tmpCity.protection = 8
                tmpCity.idInventory = id
                tmpCity.x = 5
                tmpCity.y = 4
            }
            8 -> {
                tmpCity.name = "westwall"
                tmpCity.hp = 40
                tmpCity.type = "city"
                tmpCity.active = 1
                tmpCity.people = 20
                tmpCity.damage = 10
                tmpCity.protection = 8
                tmpCity.idInventory = id
                tmpCity.x = 4
                tmpCity.y = 5
            }
            else -> {
                tmpCity.name = "gamma"
                tmpCity.hp = 50
                tmpCity.type = "metropolis"
                tmpCity.active = 1
                tmpCity.people = 25
                tmpCity.damage = 15
                tmpCity.protection = 10
                tmpCity.idInventory = id
                tmpCity.x = 5
                tmpCity.y = 5
            }
        }

        Log.d(TAG, "city: name = ${tmpCity.name} hp = ${tmpCity.hp} type = ${tmpCity.type} active = ${tmpCity.active} people = ${tmpCity.people} damage = ${tmpCity.damage} protection = ${tmpCity.protection} idInventory = ${tmpCity.idInventory} x = ${tmpCity.x} y = ${tmpCity.y}")
        city.add(tmpCity)
    }

    fun initCity(id: Int, name: String, hp: Int, type: String, active: Int, people: Int, damage: Int, protection: Int, x: Int, y: Int) {
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

    private fun initInventory(id: Int) {
        val tmpInventory = InventoryData()
        tmpInventory.idItemProtection = id
        tmpInventory.idItemResource = id
        tmpInventory.idItemWeapon = id
        Log.d(TAG, "inventory: idItemProtection = ${tmpInventory.idItemProtection} idItemResource = ${tmpInventory.idItemResource} idItemWeapon = ${tmpInventory.idItemWeapon}")
        inventory.add(tmpInventory)
    }

    private fun initProtection() {
        val tmpProtection = ItemProtection()
        tmpProtection.slot = 0
        Log.d(TAG, "protection: slot = ${tmpProtection.slot}")
        protection.add(tmpProtection)
    }

    fun initProtection(id: Int, slot: Int) {
        protection[id].slot = slot
    }

    private fun initResource(cityId: Int) {
        val tmpResource = ItemResource()
        when(cityId){
            0 -> {
                tmpResource.people = 3
            }
            1 -> {
                tmpResource.people = 25
            }
            2 -> {
                tmpResource.people = 15
            }
            3 -> {
                tmpResource.people = 15
            }
            4 -> {
                tmpResource.people = 5
            }
            5 -> {
                tmpResource.people = 10
            }
            6 -> {
                tmpResource.people = 5
            }
            7 -> {
                tmpResource.people = 40
            }
            8 -> {
                tmpResource.people = 40
            }
            9 -> {
                tmpResource.people = 60
            }
        }
        tmpResource.food = tmpResource.people * 30
        tmpResource.wood = 0
        tmpResource.stone = 0
        tmpResource.iron = 0

        tmpResource.fuel = 5 * 20

        Log.d(TAG, "resource: wood = ${tmpResource.wood} stone = ${tmpResource.stone} iron = ${tmpResource.iron} food = ${tmpResource.food} fuel = ${tmpResource.fuel}")
        resource.add(tmpResource)
    }

    fun initResource(id: Int, tree: Int, stone: Int, iron: Int, food: Int, fuel: Int) {
        resource[id].wood = 0
        resource[id].stone = 0
        resource[id].iron = 0
        resource[id].food = 2
        resource[id].fuel = 10
    }

    private fun initWeapon() {
        val tmpWeapon = ItemWeapon()
        tmpWeapon.storage = mutableListOf()
        tmpWeapon.slots = mutableListOf()
        weapon.add(tmpWeapon)
    }

    fun initWeapon(id: Int, storage: MutableList<Int>, slots: MutableList<Int>) {
        weapon[id].storage = storage
        weapon[id].slots = slots
    }

    private fun allocation() {
        if (city[0].x + 1 < map.x) {
            if (!tile[city[0].y][city[0].x + 1].busy) tile[city[0].y][city[0].x + 1].allocation = 1
            else tile[city[0].y][city[0].x + 1].allocation = 2
        }
        if (city[0].x - 1 >= 0) {
            if (!tile[city[0].y][city[0].x - 1].busy) tile[city[0].y][city[0].x - 1].allocation = 1
            else tile[city[0].y][city[0].x - 1].allocation = 2
        }
        if (city[0].y + 1 < map.y) {
            if (!tile[city[0].y + 1][city[0].x].busy) tile[city[0].y + 1][city[0].x].allocation = 1
            else tile[city[0].y + 1][city[0].x].allocation = 2
        }
        if (city[0].y - 1 >= 0) {
            if (!tile[city[0].y - 1][city[0].x].busy) tile[city[0].y - 1][city[0].x].allocation = 1
            else tile[city[0].y - 1][city[0].x].allocation = 2
        }
    }

    fun init() {
        Log.d(TAG, "INIT MANAGER")
        for (i in 0 until countCity) initCity(i)
        for (i in 0 until countCity) initInventory(i)
        for (i in 0 until countCity) initProtection()
        for (i in 0 until countCity) initResource(i)
        for (i in 0 until countCity) initWeapon()
        initMap()
        for (i in 0 until countCity) {
            tile[city[i].y][city[i].x].city = city[i]
            tile[city[i].y][city[i].x].busy = true
            Log.d(TAG, "tile[city[$i].x][city[$i].y].city.id = ${tile[city[i].y][city[i].x].city.id}")
        }
        allocation()
        Log.d(TAG, "COMPLETE INIT MANAGER")
    }

    private fun downloadCity() {
        val cityList = dataBase.cityList("%")
        if (cityList.size != 0) {
            city = cityList
            for (i in 0 until countCity)
                Log.d(TAG, "city: name = ${city[i].name} hp = ${city[i].hp} type = ${city[i].type} active = ${city[i].active} people = ${city[i].people} damage = ${city[i].damage} protection = ${city[i].protection} idInventory = ${city[i].idInventory} x = ${city[i].x} y = ${city[i].y}")
        } else Log.e(TAG, "ERROR: cityList.size = ${cityList.size}")
    }

    private fun downloadCity(id: Int) {
        val cityList = dataBase.cityList("%")
        if (cityList.size != 0) {
            city[id] = cityList[id]
            Log.d(TAG, "city: name = ${city[id].name} hp = ${city[id].hp} type = ${city[id].type} active = ${city[id].active} people = ${city[id].people} damage = ${city[id].damage} protection = ${city[id].protection} idInventory = ${city[id].idInventory} x = ${city[id].x} y = ${city[id].y}")
        } else Log.e(TAG, "ERROR: cityList.size = ${cityList.size}")
    }

    private fun downloadInventory() {
        val inventoryList = dataBase.inventoryList("%")
        if(inventoryList.size != 0) {
            inventory = inventoryList
            for (i in 0 until countCity)
                Log.d(TAG, "inventory: idItemProtection = ${inventory[i].idItemProtection} idItemResource = ${inventory[i].idItemResource} idItemWeapon = ${inventory[i].idItemWeapon}")
        } else Log.e(TAG, "ERROR: inventoryList.size = ${inventoryList.size}")
    }

    private fun downloadInventory(id: Int) {
        val inventoryList = dataBase.inventoryList("%")
        if (inventoryList.size != 0) {
            inventory[id] = inventoryList[id]
            Log.d(TAG, "inventory: idItemProtection = ${inventory[id].idItemProtection} idItemResource = ${inventory[id].idItemResource} idItemWeapon = ${inventory[id].idItemWeapon}")
        } else Log.e(TAG, "ERROR: invenoryList.size = ${inventoryList.size}")
    }

    private fun downloadProtection() {
        val protectionList = dataBase.itemProtectionList("%")
        if (protectionList.size != 0) {
            protection = protectionList
            for (i in 0 until countCity) Log.d(TAG, "protection: slot = ${protection[i].slot}")
        } else Log.e(TAG, "ERROR: protectionList.size = ${protectionList.size}")
    }

    private fun downloadProtection(id: Int) {
        val protectionList = dataBase.itemProtectionList("%")
        if (protectionList.size != 0) {
            protection[id] = protectionList[id]
            Log.d(TAG, "protection: slot = ${protection[id].slot}")
        } else Log.e(TAG, "ERROR: protectionList.size = ${protectionList.size}")
    }

    private fun downloadResource() {
        val resourceList = dataBase.itemResourceList("%")
        if (resourceList.size != 0) {
            for (i in 0 until countCity) {
                resource.add(resourceList[i])
                Log.d(TAG, "resource: wood = ${resource[i].wood} stone = ${resource[i].stone} iron = ${resource[i].iron} food = ${resource[i].food} fuel = ${resource[i].fuel} people = ${resource[i].people}")
            }
        } else Log.d(TAG, "ERROR: resourceList.size = ${resourceList.size}")
    }

    private fun downloadResource(id: Int) {
        val resourceList = dataBase.itemResourceList("%")
        if (resourceList.size != 0) {
            resource[id] = resourceList[id]
            Log.d(TAG, "resource: wood = ${resource[id].wood} stone = ${resource[id].stone} iron = ${resource[id].iron} food = ${resource[id].food} fuel = ${resource[id].fuel}")
        } else Log.e(TAG, "ERROR: resurceList.size = ${resourceList.size}")
    }

    private fun downloadWeapon() {
        //Log.d(_root_ide_package_.cool_guys.walkcity.database.Manager.Companion.TAG, "downloadWeapon() test 1")
        val weaponList = dataBase.itemWeaponList("%")
        if (weaponList.size != 0) {
            // Log.d(TAG, "downloadWeapon() test 2")
            weapon = weaponList
            // Log.d(TAG, "downloadWeapon() test 3")
            for (i in 0 until countCity)
                Log.d(TAG, "weapon: weapon.slots.size = ${weapon[i].slots.size} weapn.storage.size = ${weapon[i].storage.size}")
            // Log.d(TAG, "downloadWeapon() test 4")
        } else Log.e(TAG, "ERROR: weaponList.size = ${weaponList.size}")
        // Log.d(TAG, "downloadWeapon() test 5")
    }

    private fun downloadWeapon(id: Int) {
        val weaponList = dataBase.itemWeaponList("%")
        if (weaponList.size != 0) {
            weapon[id] = weaponList[id]
            Log.d(TAG, "weapon: weapon.slots.size = ${weapon[id].slots.size} weapn.storage.size = ${weapon[id].storage.size}")
        } else Log.e(TAG, "ERROR: weaponList.size = ${weaponList.size}")
    }

    private fun downloadMap() {
        val tmpMap = dataBase.mapInfo("%")
        if (tmpMap.size != 0) {
            map = tmpMap[0]
            Log.d(TAG, "map: map.x = ${map.x} map.y = ${map.y}")
        } else Log.d(TAG, "ERROR: _map.size = ${tmpMap.size}")
    }

    private fun downloadTile() {
        val tileList = dataBase.tileList("%")
        if(tileList.size != 0) {
            for (i in 0 until map.y) {
                tile.add(mutableListOf())

                for (e in 0 until map.x) {
                    tile[i].add(tileList[i * map.y + e])

                    val type = tile[i][e].type
                    val idItemResource = tile[i][e].idItemResource
                    Log.d(TAG, "tile[$i][$e]: type = $type idItemResource = $idItemResource")
                }
            }
        } else Log.e(TAG, "ERROR: tileList.size = ${tileList.size}")
    }

    fun downloadTile(i: Int, e: Int) {
        val tileList = dataBase.tileList("%")
        if (tileList.size != 0) {
            tile[i][e] = tileList[i * map.y + e]
            val type = tile[i][e].type
            val idItemResource = tile[i][e].idItemResource
            Log.d(TAG, "tile[$i][$e]: type = $type idItemResource = $idItemResource")
        } else Log.e(TAG, "ERROR: tileList.size = ${tileList.size}")
    }

    private fun downloadResourceTile() {
        val resourceTileList = dataBase.itemResourceList("%")
        if (resourceTileList.size != 0) {
            for (i in 0 until map.y) {
                resourceTile.add(mutableListOf())
                for (e in 0 until map.x) {
                    resourceTile[i].add(resourceTileList[tile[i][e].idItemResource])
                    tile[i][e].resource = resourceTile[i][e]
                    val tree = resourceTile[i][e].wood
                    val stone = resourceTile[i][e].stone
                    val iron = resourceTile[i][e].iron
                    val food = resourceTile[i][e].food
                    val fuel = resourceTile[i][e].fuel
                    val people = resourceTile[i][e].people
                    Log.d(TAG, "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food fuel = $fuel people = $people")
                }
            }
        } else Log.e(TAG, "ERROR: resourceTileList.size = ${resourceTileList.size}")
    }

    fun downloadResourceTile(i: Int, e: Int) {
        val resourceTileList = dataBase.itemResourceList("%")
        if (resourceTileList.size != 0) {
            resourceTile[i][e] = resourceTileList[tile[i][e].idItemResource]
            val tree = resourceTile[i][e].wood
            val stone = resourceTile[i][e].stone
            val iron = resourceTile[i][e].iron
            val food = resourceTile[i][e].food
            val fuel = resourceTile[i][e].fuel
            Log.d(TAG, "resourceTile[$i][$e]: wood = $tree stone = $stone iron = $iron food = $food fuel = $fuel")
        } else Log.e(TAG, "ERROR: resourceTileList.size = ${resourceTileList.size}")
    }

    fun download() {
        Log.d(TAG, "DOWNLOAD DB")
        downloadCity()
        downloadInventory()
        downloadProtection()
        downloadResource()
        downloadWeapon()
        downloadMap()
        downloadTile()
        downloadResourceTile()
        for (i in 0 until countCity) {
            if (city[i].active == 0) continue
            tile[city[i].y][city[i].x].city = city[i]
            tile[city[i].y][city[i].x].city.id -= 1
            tile[city[i].y][city[i].x].busy = true
            Log.d(TAG, "tile[city[$i].x][city[$i].y].city.id = ${tile[city[i].y][city[i].x].city.id}")

        }
        for(i in 0..5)
            for (e in 0..5)
                Log.d(TAG, "tile[$i][$e].busy = ${tile[i][e].busy}")

        allocation()
        Log.d(TAG, "COMPLETE DOWNLOAD DB")
    }

    fun unload() {
        Log.d(TAG, "UNLOAD DB")
        for (i in 0 until countCity) unloadCity(i)
        Log.d(TAG, "city unload")
        for (i in 0 until countCity) unloadInventory(i)
        Log.d(TAG, "inventory unload")
        for (i in 0 until countCity) unloadProtection(i)
        Log.d(TAG, "protection unload")
        for (i in 0 until countCity) unloadResource(i)
        Log.d(TAG, "resource unload")
        for (i in 0 until countCity) unloadWeapon(i)
        Log.d(TAG, "weapon unload")
        unloadMap()
        Log.d(TAG, "map unload")
        unloadTile()
        Log.d(TAG, "tile unload")
        unloadResourceTile()
        Log.d(TAG, "resource tile unload")
        Log.d(TAG, "COMPLETE UNLOAD DB")
    }

    private fun unloadCity(id: Int) {
        // Log.d(TAG, "unload city $id")
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
        // Log.d(TAG, "test unload city 1")
        if (tryingCity() < countCity) dataBase.addCity(values)
        else dataBase.updateCity(values, id + 1)
        // Log.d(TAG, "test unload city 2")
    }

    private fun unloadInventory(id: Int) {
        val values = ContentValues()
        values.put(DBHandler.idItemResource, inventory[id].idItemResource)
        values.put(DBHandler.idItemProtection, inventory[id].idItemProtection)
        values.put(DBHandler.idItemWeapon, inventory[id].idItemWeapon)
        if (tryingInventory() < countCity) dataBase.addInventoryData(values)
        else dataBase.updateInventoryData(values, id + 1)
    }

    private fun unloadProtection(id: Int) {
        val values = ContentValues()
        values.put(DBHandler.slot, protection[id].slot)
        if (tryingProtection() < countCity) dataBase.addItemProtection(values)
        else dataBase.updateItemProtection(values, id + 1)
    }

    private fun unloadResource(id: Int) {
        val values = ContentValues()
        values.put(DBHandler.wood, resource[id].wood)
        values.put(DBHandler.stone, resource[id].stone)
        values.put(DBHandler.iron, resource[id].iron)
        values.put(DBHandler.food, resource[id].food)
        values.put(DBHandler.fuel, resource[id].fuel)
        values.put(DBHandler.people, resource[id].people)
        if (tryingResource() < countCity) dataBase.addItemResource(values)
        else dataBase.updateItemResource(values, id + 1)
    }

    private fun unloadWeapon(id: Int) {
        // Log.d(TAG, "unloadWeapon() test 1")
        val values = ContentValues()
        var slotsString = ""
        for (i in 0 until weapon[id].slots.size) slotsString += weapon[id].slots[i].toString() + " "
        values.put(DBHandler.slots, slotsString)
        var storageString = ""
        for (i in 0 until weapon[id].storage.size) storageString += weapon[id].storage[i].toString() + " "
        values.put(DBHandler.storage, storageString)
        // Log.d(TAG, "unloadWeapon() test 2")
        if (tryingWeapon() < countCity) dataBase.addItemWeapon(values)
        else dataBase.updateItemWeapon(values, id + 1)
        // Log.d(TAG, "unloadWeapon() test 3")
    }

    private fun unloadMap() {
        // Log.d(TAG, "x = ${map.x} y = ${map.y}")
        val values = ContentValues()
        values.put(DBHandler.x, map.x)
        values.put(DBHandler.y, map.y)
        var mapString = ""
        // Log.d(TAG, "Test map 1")
        for (i in 0 until map.y) {
            for (e in 0 until map.x) {
                // Log.d(TAG, "i = $i e = $e")
                mapString += map.idTile[i][e].toString() + " "
                // Log.d(TAG, "Ok")
            }
        }
        // Log.d(TAG, "Test map 2")
        values.put(DBHandler.idTile, mapString)
        if (tryingMap() == 0) dataBase.addMap(values)
        else dataBase.updateMap(values, 1)
        // Log.d(TAG, "Test map 3")
    }

    private fun unloadTile() {
        var values : ContentValues
        for (i in 0 until map.y) {
            for (e in 0 until map.x) {
                values = ContentValues()
                values.put(DBHandler.type, tile[i][e].type)
                values.put(DBHandler.idItemResource, tile[i][e].idItemResource)
                if (tryingTile() < map.y * map.x) dataBase.addTile(values)
                else dataBase.updateTile(values, i * map.y + e + 1)
            }
        }
    }

    private fun unloadResourceTile(){
        var values : ContentValues
        for (i in 0 until map.y) {
            for (e in 0 until map.x) {
                values = ContentValues()
                values.put(DBHandler.wood, resourceTile[i][e].wood)
                values.put(DBHandler.stone, resourceTile[i][e].stone)
                values.put(DBHandler.iron, resourceTile[i][e].iron)
                values.put(DBHandler.food, resourceTile[i][e].food)
                values.put(DBHandler.fuel, resourceTile[i][e].fuel)
                values.put(DBHandler.people, resourceTile[i][e].people)
                if (tryingResource() < map.y * map.x + countCity) dataBase.addItemResource(values)
                else dataBase.updateItemResource(values, i * map.y + e + 1 + countCity)
            }
        }
    }

    fun unloadSettings() {
        val values = ContentValues()
        values.put(DBHandler.backDialog, settings.backDialog)
        values.put(DBHandler.nextTurnDialog, settings.nextTurnDialog)
        if (tryingSettings() == 0) dataBase.addSettings(values)
        else dataBase.updateSettings(values, 1)
    }

    fun downloadSettings() {
        val settingsList = dataBase.settingsList("%")
        settings  = settingsList[0]
    }

    fun tryingCity(): Int{
        // Log.d(TAG, "trying city test 1")
        val status : Int
        val cityList = dataBase.cityList("%")
        status = cityList.size
        // Log.d(TAG, "status = $status")
        return status
    }

    fun tryingSettings(): Int {
        val status : Int
        val settingsList = dataBase.settingsList("%")
        status = settingsList.size
        return status
    }

    private fun tryingInventory(): Int {
        val status : Int
        val invetoryList = dataBase.inventoryList("%")
        status = invetoryList.size
        return status
    }

    private fun tryingProtection(): Int {
        val status : Int
        val protectionList = dataBase.itemProtectionList("%")
        status = protectionList.size
        return status
    }

    private fun tryingResource(): Int {
        val status : Int
        val resourceList = dataBase.itemResourceList("%")
        status = resourceList.size
        return status
    }

    private fun tryingWeapon(): Int {
        // Log.d(TAG, "tryingWeapon() test 1")
        val status : Int
        val weaponList = dataBase.itemWeaponList("%")
        status = weaponList.size
        // Log.d(TAG, "tryingWeapon() test 2 satus = $status")
        return status
    }

    private fun tryingMap(): Int {
        val status : Int
        val mapList = dataBase.mapInfo("%")
        status = mapList.size
        return status
    }

    private fun tryingTile(): Int {
        val status : Int
        val tileList = dataBase.tileList("%")
        status = tileList.size
        // Log.d(TAG, "$status")
        return status
    }

    fun settings(): Settings {
        return settings
    }

    fun tile(): MutableList<MutableList<Tile>> {
        return tile
    }

    fun clearTile(cityId: Int) {
        Log.d(TAG, "clearTile() cityId = $cityId")
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

    fun removeCity(id: Int, newY: Int, newX: Int): Int {
        if (resource[id].fuel >= 5) {
            tile[city[id].y][city[id].x].busy = false
            resource[id].fuel -= 5
            city[id].x = newX
            city[id].y = newY
            return 1
        }
        return 0
    }

    fun craftWeapon(cityId: Int, type: Int): Int{
        Log.d(TAG, "cityId = $cityId type = $type")
        when (type) {
            1 -> {
                //Log.d(TAG, "Type 1 test1")
                //Log.d(TAG, "tryingResource = ${tryingResource()}")
                //Log.d(TAG, "wood = ${resource.size}")
                //Log.d(TAG, "wood = ${resource[cityId].wood}")
                //Log.d(TAG, "size = ${weapon[cityId].storage.size}")
                return if (resource[cityId].wood >= 2 && weapon[cityId].storage.size < 10) {
                    //Log.d(TAG, "Type 1 test2")
                    resource[cityId].wood -= 2
                    //Log.d(TAG, "Type 1 test3")
                    weapon[cityId].storage.add(type)
                    city[cityId].damage += 1
                    //Log.d(TAG, "Type 1 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 1 or not enough space in storage")
                    0
                }
            }
            2 -> {
                return if (resource[cityId].wood >= 3 && resource[cityId].stone >= 1 &&
                            weapon[cityId].storage.size < 10) {
                    resource[cityId].wood -= 3
                    resource[cityId].stone -= 1
                    weapon[cityId].storage.add(type)
                    city[cityId].damage += 2
                    Log.d(TAG, "Type 2 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 2 or not enough space in storage")
                    0
                }
            }
            3 -> {
                return if (resource[cityId].wood >= 1 && resource[cityId].stone >= 2 &&
                            weapon[cityId].storage.size < 10) {
                    resource[cityId].wood -= 1
                    resource[cityId].stone -= 2
                    weapon[cityId].storage.add(type)
                    city[cityId].damage += 3
                    Log.d(TAG, "Type 3 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 3 or not enough space in storage")
                    0
                }
            }
            4 -> {
                return if (resource[cityId].wood >= 3 && resource[cityId].stone >= 3 &&
                            weapon[cityId].storage.size < 10) {
                    resource[cityId].wood -= 3
                    resource[cityId].stone -= 3
                    weapon[cityId].storage.add(type)
                    city[cityId].damage += 4
                    Log.d(TAG, "Type 4 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 4 or not enough space in storage")
                    0
                }
            }
            5 -> {
                return if (resource[cityId].wood >= 2 && resource[cityId].stone >= 4 &&
                            weapon[cityId].storage.size < 10) {
                    resource[cityId].wood -= 2
                    resource[cityId].stone -= 4
                    weapon[cityId].storage.add(type)
                    city[cityId].damage += 3
                    Log.d(TAG, "Type 5 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 5 or not enough space in storage")
                    0
                }
            }
            6 -> {
                return if (resource[cityId].wood >= 4 && resource[cityId].stone >= 6 &&
                            resource[cityId].iron >= 2 && weapon[cityId].storage.size < 10) {
                    resource[cityId].wood -= 4
                    resource[cityId].stone -= 6
                    resource[cityId].iron -= 2
                    weapon[cityId].storage.add(type)
                    city[cityId].damage += 8
                    Log.d(TAG, "Type 6 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 6 or not enough space in storage")
                    0
                }
            }
        }
        Log.d(TAG, "Type 1 test4")
        return 0
    }

    fun clearStorage(cityId: Int, num: Int) {
        if (num < weapon[cityId].storage.size) {
            weapon[cityId].storage.removeAt(num)
            Log.d(TAG, "Weapon $num removed")
        }
        else Log.d(TAG, "Weapon $num not found")
    }

    fun changeWeapon(cityId: Int, num: Int){
        if(num < weapon[cityId].storage.size){
            when (weapon[cityId].slots.size) {
                0 -> {
                    weapon[cityId].slots.add(weapon[cityId].storage[num])
                    weapon[cityId].storage.removeAt(num)
                }
                1 -> {
                    weapon[cityId].slots.add(weapon[cityId].slots[0])
                    weapon[cityId].slots[0] = weapon[cityId].storage[num]
                    weapon[cityId].storage.removeAt(num)
                }
                else -> {
                    val type = weapon[cityId].slots[1]
                    weapon[cityId].slots[1] = weapon[cityId].slots[0]
                    weapon[cityId].slots[0] = weapon[cityId].storage[num]
                    weapon[cityId].storage[num] = type
                }
            }
        }
        else Log.d(TAG, "Weapon $num not found")
    }

    fun craftProtection(cityId: Int, type: Int): Int{
        when (type) {
            1 -> {
                return if (resource[cityId].wood >= 5) {
                    resource[cityId].wood -= 5
                    protection[cityId].slot = 1
                    city[cityId].protection += 1
                    Log.d(TAG, "Type 1 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 1")
                    0
                }
            }
            2 -> {
                return if (resource[cityId].wood >= 10 && resource[cityId].stone >= 5) {
                    resource[cityId].wood -= 10
                    resource[cityId].stone -= 5
                    protection[cityId].slot = 2
                    city[cityId].protection += 2
                    Log.d(TAG, "Type 2 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 2")
                    0
                }
            }
            3 -> {
                return if (resource[cityId].wood >= 5 && resource[cityId].stone >= 15) {
                    resource[cityId].wood -= 5
                    resource[cityId].stone -= 15
                    protection[cityId].slot = 3
                    city[cityId].protection += 3
                    Log.d(TAG, "Type 3 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 3")
                    0
                }
            }
            4 -> {
                return if (resource[cityId].wood >= 10 && resource[cityId].iron >= 10) {
                    resource[cityId].wood -= 10
                    resource[cityId].iron -= 10
                    protection[cityId].slot = 4
                    city[cityId].protection += 4
                    Log.d(TAG, "Type 4 made")
                    1
                } else {
                    Log.d(TAG, "Not enough resources for type 4")
                    0
                }
            }
        }
        return 0
    }

    fun recountDamage(cityId: Int) {
        city[cityId].damage = 0
        for(i in 0 until weapon[cityId].slots.size) {
            when(weapon[cityId].slots[i]) {
                1 -> city[cityId].damage += 1
                2 -> city[cityId].damage += 2
                3 -> city[cityId].damage += 3
                4 -> city[cityId].damage += 4
                5 -> city[cityId].damage += 5
                6 -> city[cityId].damage += 8
            }
        }
    }



    fun recountProtection(cityId: Int) {
        city[cityId].protection = 0
        when(protection[cityId].slot) {
            1 -> city[cityId].protection += 1
            2 -> city[cityId].protection += 2
            3 -> city[cityId].protection += 3
            4 -> city[cityId].protection += 4
        }
    }

    fun giveHomeCity(): CityData {
        return city[0]
    }

    fun giveHomeResource(): ItemResource {
        return resource[0]
    }

    fun giveHomeProtection(): ItemProtection {
        return protection[0]
    }

    fun giveHomeWeapon(): ItemWeapon {
        return weapon[0]
    }

    fun attackCity(cityId1: Int, cityId2: Int): Int{
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
        resource[cityId1].wood += resource[cityId2].wood / 3
        resource[cityId2].wood = 0
        resource[cityId1].stone += resource[cityId2].stone / 3
        resource[cityId2].stone = 0
        resource[cityId1].iron += resource[cityId2].iron / 3
        resource[cityId2].iron = 0
        resource[cityId1].food += resource[cityId2].food / 3
        resource[cityId2].food = 0
        resource[cityId1].fuel += resource[cityId2].fuel / 3
        resource[cityId2].fuel = 0
        resource[cityId1].people += resource[cityId2].people / 3
        resource[cityId2].people = 0
    }

    private fun attacLogic(cityId : Int): Int {
        var stat = 1
        if (city[cityId].x + 1 < map.x && stat == 1) {
            if (tile[city[cityId].y][city[cityId].x + 1].busy &&
                (tile[city[cityId].y][city[cityId].x + 1].city.name == "my" || cityId == 0)) {
                if (attackCity(cityId, tile[city[cityId].y][city[cityId].x + 1].city.id) == 1) {
                    lootingCity(cityId, tile[city[cityId].y][city[cityId].x + 1].city.id)
                    if (cityId == 0 || cityId == 4 || cityId == 5 || cityId == 6 || cityId == 9) {
                        removeCity(cityId, city[cityId].y, city[cityId].x + 1)
                    }
                }
                stat = 0
            }
        }
        if (city[cityId].y + 1 < map.y && stat == 1) {
            if (tile[city[cityId].y + 1][city[cityId].x].busy &&
                (tile[city[cityId].y + 1][city[cityId].x].city.name == "my" || cityId == 0)) {
                if (attackCity(cityId, tile[city[cityId].y + 1][city[cityId].x].city.id) == 1) {
                    lootingCity(cityId, tile[city[cityId].y + 1][city[cityId].x].city.id)
                    if (cityId == 0 || cityId == 4 || cityId == 5 || cityId == 6 || cityId == 9) {
                        removeCity(cityId, city[cityId].y + 1, city[cityId].x)
                    }
                }
                stat = 0
            }
        }
        if (city[cityId].x - 1 >= 0 && stat == 1) {
            if (tile[city[cityId].y][city[cityId].x - 1].busy &&
                (tile[city[cityId].y][city[cityId].x - 1].city.name == "my" || cityId == 0)) {
                if (attackCity(cityId, tile[city[cityId].y][city[cityId].x - 1].city.id) == 1) {
                    lootingCity(cityId, tile[city[cityId].y][city[cityId].x - 1].city.id)
                    if (cityId == 0 || cityId == 4 || cityId == 5 || cityId == 6 || cityId == 9) {
                        removeCity(cityId, city[cityId].y, city[cityId].x - 1)
                    }
                }
                stat = 0
            }
        }
        if (city[cityId].y - 1 >= 0 && stat == 1) {
            if (tile[city[cityId].y - 1][city[cityId].x].busy &&
                (tile[city[cityId].y - 1][city[cityId].x].city.name == "my" || cityId == 0)) {
                if (attackCity(cityId, tile[city[cityId].y - 1][city[cityId].x].city.id ) == 1) {
                    lootingCity(cityId, tile[city[cityId].y - 1][city[cityId].x].city.id)
                    if (cityId == 0 || cityId == 4 || cityId == 5 || cityId == 6 || cityId == 9) {
                        removeCity(cityId, city[cityId].y - 1, city[cityId].x)
                    }
                }
                stat = 0
            }
        }
        return stat
    }

    private fun moveLogic(cityId: Int){
        var stat = 1
        val test : MutableList<Int> = mutableListOf(0, 1, 2, 3)
        while(test.size != 0) {
            val flag = test.random()
            Log.d(TAG, "flag = $flag")
            if (city[cityId].x + 1 < map.x && stat == 1 && flag == 0) {
                if (!tile[city[cityId].y][city[cityId].x + 1].busy) {
                    if (removeCity(cityId, newX = city[cityId].x + 1, newY = city[cityId].y) == 1) {
                        tile[city[cityId].y][city[cityId].x].busy = true
                        stat = 0
                    }
                }
            }
            if (city[cityId].y + 1 < map.y && stat == 1 && flag == 1) {
                if (!tile[city[cityId].y + 1][city[cityId].x].busy) {
                    if (removeCity(cityId, newX = city[cityId].x, newY = city[cityId].y + 1) == 1) {
                        tile[city[cityId].y][city[cityId].x].busy = true
                        stat = 0
                    }
                }
            }
            if (city[cityId].x - 1 >= 0 && stat == 1 && flag == 2) {
                if (!tile[city[cityId].y][city[cityId].x - 1].busy) {
                    if (removeCity(cityId, newX = city[cityId].x - 1, newY = city[cityId].y) == 1) {
                        tile[city[cityId].y][city[cityId].x].busy = true
                        stat = 0
                    }
                }
            }
            if (city[cityId].y - 1 >= 0 && stat == 1 && flag == 3) {
                if (!tile[city[cityId].y - 1][city[cityId].x].busy) {
                    if (removeCity(cityId, newX = city[cityId].x, newY = city[cityId].y - 1) == 1) {
                        tile[city[cityId].y][city[cityId].x].busy = true
                    }
                }
            }
            test.remove(flag)
        }
    }

    fun craftLogic(cityId: Int) {
        var stat = craftWeapon(cityId, 6)
        if (stat == 0) stat = craftWeapon(cityId, 5)
        if (stat == 0) stat = craftWeapon(cityId, 4)
        if (stat == 0) stat = craftWeapon(cityId, 3)
        if (stat == 0) stat = craftWeapon(cityId, 2)
        if (stat == 0) stat = craftWeapon(cityId, 1)

        stat = craftProtection(cityId, 4)
        if (stat == 0) stat = craftProtection(cityId, 3)
        if (stat == 0) stat = craftProtection(cityId, 2)
        if (stat == 0) stat = craftProtection(cityId, 1)
    }

    fun changeLogic(cityId: Int) {
        var max: Int = 0
        for (a in weapon[cityId].slots) if(a > max) max = a
        for (i in 0 until weapon[cityId].storage.size)
            if (weapon[cityId].storage[i] > max || weapon[cityId].slots.size < 2)
                changeWeapon(cityId, i)
    }

    fun recountPeople(cityId: Int) {
        if (resource[cityId].food == 0) resource[cityId].people -= 1
        else {
            resource[cityId].food -= resource[cityId].people
            if (resource[cityId].food < 0) resource[cityId].food = 0
        }
        city[cityId].people = resource[cityId].people
    }

    fun renameCity(cityId: Int) {
        if (city[cityId].people < 6) {
            if (city[cityId].type == "village") city[cityId].hp -= 5
            city[cityId].type = "town"
        } else if (city[cityId].people < 21) {
            if (city[cityId].type == "town") city[cityId].hp += 5
            if (city[cityId].type == "city") city[cityId].hp -= 10
            city[cityId].type = "village"
        } else if (city[cityId].people < 51) {
            if (city[cityId].type == "village") city[cityId].hp += 10
            if (city[cityId].type == "metropolis") city[cityId].hp -= 20
            city[cityId].type = "city"
        } else{
            if (city[cityId].type == "city") city[cityId].hp += 20
            city[cityId].type = "metropolis"
        }
    }

    fun recountActive(cityId: Int) {
        if (city[cityId].hp <= 0 || resource[cityId].people <= 0) city[cityId].active = 0
    }

    fun nextTurn(){
        Log.d(TAG, "next turn test 1")
        if (city[0].active == 0) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Game info")
            builder.setMessage("You die")
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        for (i in 0 until countCity) {
            if (city[i].active == 0 || i == 0) continue
            clearTile(i)
            if (i == 0 || i == 4 || i == 5 || i == 6 || i == 9) {
                if (attacLogic(i) != 0) moveLogic(i)
            } else{
                attacLogic(i)
            }
            clearTile(i)
            //craftLogic(i)
            //changeLogic(i)
            //recountDamage(i)
            //recountProtection(i)
            recountPeople(i)
            renameCity(i)
            recountActive(i)
        }
    }

    fun check(): Int {
        if (city[0].active == 0) return 1
        if (city[9].active == 0) return 2
        return 0
    }

    fun delCity() {
        for (i in 10 downTo 1) dataBase.removeCity(i)
    }

    companion object {
        private const val TAG = "Manager"
    }
}