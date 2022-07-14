package cool_guys.walkcity.gamemap

class MapLogic(){

}


class Cord(var X : Int,
           var Y : Int,
           var I : Int,
           var J : Int,
           var N : Int){
    init{
        J = -(this.X - this.Y - N)/2
        I = (this.X + this.Y - N)/2
    }
    fun upDate(){
        J = -(this.X - this.Y - N)/2
        I = (this.X + this.Y - N)/2
    }

}