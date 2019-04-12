package cool_guys.walkcity.Map

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import cool_guys.walkcity.DataBase.Tile
import org.jetbrains.anko.dip
import java.lang.Math.abs
import android.graphics.BitmapFactory
import android.support.v7.app.AlertDialog
import android.widget.Toast
import cool_guys.walkcity.DataBase.CityData
import cool_guys.walkcity.DataBase.Manager


class MapView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mBitmap: Bitmap
    private var field: Bitmap
    private var hill: Bitmap
    private var desert: Bitmap
    private  var sea : Bitmap
    private var smallcity : Bitmap
    private var mediumcity : Bitmap
    //private var fielthiscity : Bitmap
    private var mCanvas: Canvas
    private var paint: Paint
    private var mBitmapPaint: Paint
    private var canvasXSize: Float = 0f
    private var canvasYSize: Float = 0f
    var manager : Manager = Manager(context)
    //Задаем матрицу
    public var Map : MutableList<MutableList<Tile>>
    var CityArr : MutableList<CityData>
    private val xHightTile = 150f
    private val yHightTile = 75f
    public val N = 6//должно быть четным
    private val matrX = 850f//координаты центра поля
    private val matrY = 300f
    private val dMatrX = matrX - N*xHightTile//то на сколько поле отходит от края(от х и у)
    private val dMatrY = matrY - N*50f

    var statMyCity = 1

    //for scroll
    private val scaleGestureDetector = ScaleGestureDetector(context, MyScaleGestureListener())
    private var viewXSize: Int = 1000
    private var viewYSize: Int = 600

    private var mScaleFactor: Float = 1f
    private var detector = GestureDetector(context, MyGestureListener())


    init {

        Map = MutableList(N, { MutableList(N, {Tile("field")}) })
        CityArr = MutableList(6 , {CityData()})

        canvasXSize = dip(1000f).toFloat()
        canvasYSize = dip(600f).toFloat()


        mBitmap = Bitmap.createBitmap(canvasXSize.toInt(), canvasYSize.toInt(), Bitmap.Config.ARGB_8888)
        mBitmapPaint = Paint(Paint.DITHER_FLAG)

        val options = BitmapFactory.Options()
        options.inScaled = false

        field = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.fields)
        hill = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.hill)
        desert = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.desert)
        sea = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.sea)
        //fielthiscity = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.fieldthiscity)
        smallcity = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.smallcity)
        mediumcity = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.mediumcity)

        mCanvas = Canvas(mBitmap)
        scrollBy(matrX.toInt() - 500, matrY.toInt() - 500)

       // mCanvas.drawBitmap(picTile, 50f, 50f, mBitmapPaint)

        paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = -0x99999
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND

//
//        mCanvas.drawLine(200f, 0f, 400f, 100f, paint)
//        mCanvas.drawLine(0f, 100f, 200f, 0f, paint)
//        mCanvas.drawLine(0f, 100f, 200f, 200f, paint)
//        mCanvas.drawLine(200f, 200f, 400f, 100f, paint)



       // drawTile(100f, 100f, Map[0][0], paint)
       // drawTile(300f, 100f, Map[0][0], paint)
       // drawTile(200f, 50f, Map[0][0], paint)
       // drawTile(200f, 150f, Map[0][0], paint)

        drawMatr()


    }



    fun drawMatr(){



        for(i in 0..(N-1)) {
            if (i % 2 == 0) paint.color = Color.GREEN
            else paint.color = Color.BLUE
            for (j in 0..i) {
                drawTile((i - j) * xHightTile + matrX, (i + j) * yHightTile + dMatrY + yHightTile, Map[i][j], paint)
                drawTile(-(i - j) * xHightTile + matrX, (i + j) * yHightTile + dMatrY + yHightTile, Map[j][i], paint)
            }

        }

        invalidate()
    }

    private fun drawCity(X : Float, Y : Float, city : CityData, paint : Paint){

            mCanvas.drawBitmap(smallcity, X - xHightTile, Y - yHightTile, paint)

    }


    private fun drawTile(X : Float, Y : Float, T : Tile, paint : Paint){


       // mCanvas.drawLine(X, Y - yHightTile, xHightTile + X, Y, paint)
      //  mCanvas.drawLine(X - xHightTile, Y, X, Y - yHightTile, paint)
       // mCanvas.drawLine(X - xHightTile, Y, X, yHightTile + Y, paint)
       // mCanvas.drawLine(X, yHightTile + Y, xHightTile + X, Y, paint)
        if(T.type == "hill"){
            //mCanvas.drawCircle(X, Y, 50f, paint)
            mCanvas.drawBitmap(hill, X - xHightTile, Y - yHightTile, paint)
        }
        else if(T.type == "desert") {
            mCanvas.drawBitmap(desert, X - xHightTile,Y - yHightTile, paint)
        }
        else if(T.type == "sea") {
            mCanvas.drawBitmap(sea, X - xHightTile, Y - yHightTile, paint)
        }
        else {
            mCanvas.drawBitmap(field, X - xHightTile, Y - yHightTile, paint)
        }
        if(T.city.name == "town"){
            mCanvas.drawBitmap(smallcity,  X - xHightTile, Y - yHightTile, paint)
        }
        else if(T.city.name != "town"){
            mCanvas.drawBitmap(mediumcity,  X - xHightTile, Y - yHightTile, paint)
        }
    }

    override fun onDraw(canvas: Canvas) {

        canvas.save()
        canvas.scale(mScaleFactor, mScaleFactor)
        canvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas.restore()
        println("bb")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        detector.onTouchEvent(event)
        scaleGestureDetector.onTouchEvent(event)
        return true
    }


    //пишем Скейл
    private inner class MyScaleGestureListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            val scaleFactor = scaleGestureDetector.scaleFactor

            val focusX = scaleGestureDetector.focusX
            val focusY = scaleGestureDetector.focusY

            if (mScaleFactor * scaleFactor > 1 && mScaleFactor * scaleFactor < 2) {
                mScaleFactor *= scaleGestureDetector.scaleFactor
                canvasXSize = viewXSize * mScaleFactor//изменяем хранимое в памяти значение размера канваса
                canvasYSize = viewYSize * mScaleFactor
                //используется при расчетах
                //по умолчанию после зума канвас отскролит в левый верхний угол.
                //Скролим канвас так, чтобы на экране оставалась
                //область канваса, над которой был жест зума
                //Для получения данной формулы достаточно школьных знаний математики (декартовы координаты).
                var scrollX = ((scrollX + focusX) * scaleFactor - focusX).toInt()
                scrollX = Math.min(Math.max(scrollX, 0), canvasXSize.toInt() - viewXSize)
                var scrollY = ((scrollY + focusY) * scaleFactor - focusY).toInt()
                scrollY = Math.min(Math.max(scrollY, 0), canvasXSize.toInt() - viewYSize)
                scrollTo(scrollX, scrollY)
            }
            //вызываем перерисовку принудительно


            invalidate()
            return true
        }

    }

    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        //обрабатываем скролл (перемещение пальца по экрану)
        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            //не даем канвасу показать края по горизонтали
            if (scrollX + distanceX <= canvasXSize - viewXSize && scrollX + distanceX > 0) {
                scrollBy(distanceX.toInt(), 0)
            }
            //не даем канвасу показать края по вертикали
            if (scrollY + distanceY <= canvasYSize - viewYSize && scrollY + distanceY > 0) {
                scrollBy(0, distanceY.toInt())
            }
            return true
        }

        override fun onLongPress(event: MotionEvent) {
            val cellX = ((event.x + scrollX) / mScaleFactor).toInt()
            val cellY = ((event.y + scrollY) / mScaleFactor).toInt()

            val cord = Cord(0, 0, 0, 0, N)

            tileTapCord(cellX.toFloat() - dMatrX, cellY.toFloat() - dMatrY, cord)

            logicLongTapFun(cord)

            super.onLongPress(event)
        }

        //обрабатываем одиночный тап
        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            //получаем координаты ячейки, по которой тапнули
            val cellX = ((event.x + scrollX) / mScaleFactor).toInt()
            val cellY = ((event.y + scrollY) / mScaleFactor).toInt()




            val cord = Cord(0, 0, 0 , 0, N)

            tileTapCord(cellX.toFloat() - dMatrX, cellY.toFloat() - dMatrY, cord)


            logicTapFun(cord)

            return true
        }

        fun tileTapCord(xTap : Float, yTap : Float, cord : Cord){//подавать х-тар и у-тар без dMatrX/Y

            val sX = ((xTap)/xHightTile).toInt()
            val sY = ((yTap)/yHightTile).toInt()
           // val cord = Cord(0, 0)


            if((sX+sY) % 2 == 1){
                if(-(xTap % xHightTile) / 2 + yHightTile < yTap % yHightTile){
                    cord.X = sX + 1
                    cord.Y = sY + 1
                }
                else {
                    cord.X = sX
                    cord.Y = sY
                }

            }

            else{
                if((xTap - sX*xHightTile) / 2 > yTap - sY*yHightTile){
                    cord.X = sX + 1
                    cord.Y = sY
                }
                else {
                    cord.X = sX
                    cord.Y = sY + 1
                }

            }
            if(abs(cord.X - N) + abs(cord.Y - N) > N-1){
               // println("sX = ${cord.X}, sY = ${cord.Y}, ${sX - N} ${sY - N}")
                cord.X = -11
                cord.Y = -11
            }
            println("sX = ${cord.X}, sY = ${cord.Y}")
            cord.upDate()
        }


        //обрабатываем двойной тап
       // override fun onDoubleTapEvent(event: MotionEvent): Boolean {
       //     //зумируем канвас к первоначальному виду
       //     mScaleFactor = 1f
       //     canvasSize = viewSize.toFloat()
       //     scrollTo(100, 100)//скролим, чтобы не было видно краев канваса.
           // invalidate()//перерисовываем канвас
       //     return false
       // }


    }




    fun logicLongTapFun(cord : Cord){
        if(cord.I > N-1 || cord.J > N-1 || cord.I < 0 || cord.J < 0){}
        else {
            println("I =  ${cord.I}   J =  ${cord.J} ")
            val y = cord.I
            val x = cord.J
            if(Map[y][x].city.name != ""){
                val builder = AlertDialog.Builder(context)
                builder.setTitle("City stats")
                builder.setMessage("Name: ${Map[y][x].city.name} ${Map[y][x].city.type}\n" +
                        "Hp: ${Map[y][x].city.hp}\n" +
                        "Damage: ${Map[y][x].city.damage}\n" +
                        "Protection: ${Map[y][x].city.protection}")
                builder.setPositiveButton("Ok") { dialog, which ->

                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                //drawMatr()
            }
            else{
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Tile stats")
                builder.setMessage("Wood: ${Map[y][x].resource.wood}\n" +
                        "Stone: ${Map[y][x].resource.stone}\n" +
                        "Iron: ${Map[y][x].resource.iron}\n" +
                        "Food: ${Map[y][x].resource.food}\n" +
                        "Fuel: ${Map[y][x].resource.fuel}\n" +
                        "People: ${Map[y][x].resource.people}\n")
                builder.setPositiveButton("Ok") { dialog, which ->

                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                //drawMatr()
            }
            //Map[cord.I][cord.J].type = "hill"
            //println("aaa")

        }
    }

    fun logicTapFun(cord : Cord){
        if(cord.I > N-1 || cord.J > N-1 || cord.I < 0 || cord.J < 0){}
        else if(statMyCity == 1){
            //Map[cord.I][cord.J].type = "hill"
            val y = cord.I
            val x = cord.J
            if(Map[y][x].allocation == 1){
                Map[manager.city[0].y][manager.city[0].x].busy = false
                manager.removeCity(0, x, y)
                Map[y][x].busy = true
                statMyCity = 0
            }
            else if(Map[y][x].allocation == 2){
                if(manager.attacCity(0, Map[y][x].city.id) == 1){
                    manager.lootingCity(0, Map[y][x].city.id)
                    Map[manager.city[0].y][manager.city[0].x].busy = false
                    manager.removeCity(0, x, y)
                    Map[y][x].busy = true
                }
                statMyCity = 0
            }
            //drawMatr()
            //println("aaa")
        }
    }

}




