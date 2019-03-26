package cool_guys.walkcity.DataBase

class ItemWeapon(var id : Int = 0, var slots : MutableList<Int> = mutableListOf(), var storage : MutableList<Int> = mutableListOf())


/*fun demo(){
    val demo = ItemWeapon(0, mutableListOf(1,2,3), MutableList(2, {x -> -1}))

    val immutableArray = Array(10, {x -> 0})
    val mutableList = MutableList(10, {x -> x*x})

    for (i in immutableArray){}
    for(i in mutableList){}
    val x = 5
    x.toString()
    5.toString()
    mutableList.add(x)
    immutableArray.plus(4)

}*/
