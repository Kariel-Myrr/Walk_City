package cool_guys.walkcity.database

class Tile(var type : String = "", var idItemResource: Int = 0, var city : CityData = CityData(), var resource : ItemResource = ItemResource(), var busy : Boolean = false, var allocation : Int = 0)