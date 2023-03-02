package cool_guys.walkcity.gamemap

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import cool_guys.walkcity.database.Tile
import android.graphics.BitmapFactory
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.widget.Toast
import cool_guys.walkcity.database.CityData
import cool_guys.walkcity.database.Manager


class MapView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mBitmap: Bitmap
    private var field: Bitmap
    private var hill: Bitmap
    private var desert: Bitmap
    private var sea : Bitmap
    private var smallcity : Bitmap
    private var mediumcity : Bitmap
    private var spot : Bitmap
    private var forest : Bitmap
    private var gamma : Bitmap
    private var fort : Bitmap
    private var lake : Bitmap

    //private var fielthiscity : Bitmap
    private var mCanvas: Canvas
    private var paint: Paint
    private var mBitmapPaint: Paint
    private var canvasXSize: Float = 0f
    private var canvasYSize: Float = 0f
    var manager : Manager = Manager(context)

    //Задаем матрицу
    var map : MutableList<MutableList<Tile>>
    var cityArr : MutableList<CityData>
    private val xHightTile = 150f
    private val yHightTile = 75f
    val n = 6                                   //должно быть четным
    private val matrX = 950f                    //координаты центра поля
    private val matrY = 300f
    private val dMatrX = matrX - n*xHightTile   //то на сколько поле отходит от края(от х и у)
    private val dMatrY = matrY - n*50f

    var statMyCity = 1

    //for scroll
    private val scaleGestureDetector = ScaleGestureDetector(context, MyScaleGestureListener())
    private var viewXSize: Int = 1000
    private var viewYSize: Int = 0

    private var mScaleFactor: Float = 1f
    private var detector = GestureDetector(context, MyGestureListener())


    init {
        map = MutableList(n) { MutableList(n) { Tile("field") } }
        cityArr = MutableList(6) { CityData() }

        canvasXSize = TypedValue.applyDimension(
            COMPLEX_UNIT_DIP,
            950f,
            resources.displayMetrics)
        canvasYSize = TypedValue.applyDimension(
            COMPLEX_UNIT_DIP,
            500f,
            resources.displayMetrics)

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
        forest = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.forest)
        spot = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.spot)
        fort = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.defstatcity)
        gamma = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.gamma)
        lake = BitmapFactory.decodeResource(resources, cool_guys.walkcity.R.drawable.lake)

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
        for(i in 0 until n) {
            if (i % 2 == 0) paint.color = Color.GREEN
            else paint.color = Color.BLUE
            for (j in 0..i) {
                drawTile((i - j) * xHightTile + matrX, (i + j) * yHightTile + dMatrY + yHightTile, map[i][j], paint)
                drawTile(-(i - j) * xHightTile + matrX, (i + j) * yHightTile + dMatrY + yHightTile, map[j][i], paint)
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
        when (T.type) {
            "hill" -> mCanvas.drawBitmap(hill, X - xHightTile, Y - yHightTile, paint)
            "desert" -> mCanvas.drawBitmap(desert, X - xHightTile,Y - yHightTile, paint)
            "sea" -> mCanvas.drawBitmap(sea, X - xHightTile, Y - yHightTile, paint)
            "forest" -> mCanvas.drawBitmap(forest, X - xHightTile, Y - yHightTile - 115, paint)
            "lake" -> mCanvas.drawBitmap(lake, X - xHightTile , Y - yHightTile - 115, paint)
            else -> mCanvas.drawBitmap(field, X - xHightTile, Y - yHightTile, paint)
        }

        if (T.busy) {
            if (T.city.name == "my")                mCanvas.drawBitmap(spot, X - xHightTile , Y - yHightTile - 115, paint)
            if (T.city.type == "town")              mCanvas.drawBitmap(smallcity, X - xHightTile , Y - yHightTile - 115, paint)
            else if (T.city.idInventory <=3 && T.city.idInventory != 0)
                                                    mCanvas.drawBitmap(fort, X - xHightTile, Y - yHightTile - 115, paint)
            else if (T.city.type == "metropolis")   mCanvas.drawBitmap(gamma, X - xHightTile, Y - yHightTile - 115, paint)
            else if (T.city.type != "town")         mCanvas.drawBitmap(mediumcity, X - xHightTile, Y - yHightTile - 115, paint)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.scale(mScaleFactor, mScaleFactor)
        canvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            detector.onTouchEvent(event)
        }
        if (event != null) {
            scaleGestureDetector.onTouchEvent(event)
        }
        return true
    }


    //пишем Скейл
    private inner class MyScaleGestureListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            val scaleFactor = scaleGestureDetector.scaleFactor

            val focusX = scaleGestureDetector.focusX
            val focusY = scaleGestureDetector.focusY

            if (mScaleFactor * scaleFactor > 1 && mScaleFactor * scaleFactor <= 1) {
                mScaleFactor *= scaleGestureDetector.scaleFactor
                canvasXSize = viewXSize * mScaleFactor  //изменяем хранимое в памяти значение размера канваса
                canvasYSize = viewYSize * mScaleFactor
                /** используется при расчетах
                  по умолчанию после зума канвас отскролит в левый верхний угол.
                  Скролим канвас так, чтобы на экране оставалась
                  область канваса, над которой был жест зума
                  Для получения данной формулы достаточно школьных знаний математики (декартовы координаты).
                **/
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

            val cord = Cord(0, 0, 0, 0, n)

            tileTapCord(cellX.toFloat() - dMatrX, cellY.toFloat() - dMatrY, cord)

            logicLongTapFun(cord)

            super.onLongPress(event)
        }

        //обрабатываем одиночный тап
        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            //получаем координаты ячейки, по которой тапнули
            val cellX = ((event.x + scrollX) / mScaleFactor).toInt()
            val cellY = ((event.y + scrollY) / mScaleFactor).toInt()

            val cord = Cord(0, 0, 0 , 0, n)

            tileTapCord(cellX.toFloat() - dMatrX, cellY.toFloat() - dMatrY, cord)

            logicTapFun(cord)
            return true
        }

        fun tileTapCord(xTap : Float, yTap : Float, cord : Cord){//подавать х-тар и у-тар без dMatrX/Y
            val sX = ((xTap)/xHightTile).toInt()
            val sY = ((yTap)/yHightTile).toInt()
           // val cord = Cord(0, 0)

            if ((sX+sY) % 2 == 1) {
                if (-(xTap % xHightTile) / 2 + yHightTile < yTap % yHightTile) {
                    cord.X = sX + 1
                    cord.Y = sY + 1
                } else {
                    cord.X = sX
                    cord.Y = sY
                }
            } else {
                if ((xTap - sX*xHightTile) / 2 > yTap - sY*yHightTile) {
                    cord.X = sX + 1
                    cord.Y = sY
                } else {
                    cord.X = sX
                    cord.Y = sY + 1
                }
            }
            if (kotlin.math.abs(cord.X - n) + kotlin.math.abs(cord.Y - n) > n-1) {
               // println("sX = ${cord.X}, sY = ${cord.Y}, ${sX - N} ${sY - N}")
                cord.X = -11
                cord.Y = -11
            }
            Log.d(TAG, "sX = ${cord.X}, sY = ${cord.Y}")
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
        if (cord.I > n-1 || cord.J > n-1 || cord.I < 0 || cord.J < 0) {
            // wtf TODO
        } else {
            Log.d(TAG, "I =  ${cord.I}   J =  ${cord.J} ")
            val y = cord.I
            val x = cord.J
            if (map[y][x].city.name != "") {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("City stats")
                builder.setMessage("Name: ${map[y][x].city.name} ${map[y][x].city.type}\n" +
                        "Hp: ${map[y][x].city.hp}\n" +
                        "Damage: ${map[y][x].city.damage}\n" +
                        "Protection: ${map[y][x].city.protection}")
                builder.setPositiveButton("Ok") { dialog, which ->

                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                //drawMatr()
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Tile stats")
                builder.setMessage("Wood: ${map[y][x].resource.wood}\n" +
                        "Stone: ${map[y][x].resource.stone}\n" +
                        "Iron: ${map[y][x].resource.iron}\n" +
                        "Food: ${map[y][x].resource.food}\n" +
                        "Fuel: ${map[y][x].resource.fuel}\n" +
                        "People: ${map[y][x].resource.people}\n")
                builder.setPositiveButton("Ok") { dialog, which ->

                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                //drawMatr()
            }
            //map[cord.I][cord.J].type = "hill"
        }
    }

    fun logicTapFun(cord : Cord){
        if (cord.I > n-1 || cord.J > n-1 || cord.I < 0 || cord.J < 0) {
            // once again wtf TODO
        } else if(statMyCity == 1){
            //map[cord.I][cord.J].type = "hill"
            val y = cord.I
            val x = cord.J
            if (map[y][x].allocation == 1) {
                Toast.makeText(context, "My city moved.", Toast.LENGTH_SHORT).show()
                manager.clearTile(0)
                map[manager.city[0].y][manager.city[0].x].busy = false
                map[manager.city[0].y][manager.city[0].x].city = CityData()
                map[y][x].city = manager.city[0]
                manager.removeCity(0, y, x)
                map[y][x].busy = true
                manager.clearTile(0)
                manager.recountPeople(0)
                manager.renameCity(0)
                manager.recountActive(0)
                statMyCity = 0
            } else if (map[y][x].allocation == 2) {
                Toast.makeText(context, "My city attacked.", Toast.LENGTH_SHORT).show()
                if (manager.attackCity(0, map[y][x].city.id) == 1) {
                    map[y][x].city.active = 0
                    Toast.makeText(context, "You have destroyed enemy city.", Toast.LENGTH_SHORT).show()
                    manager.clearTile(0)
                    manager.lootingCity(0, map[y][x].city.id)
                    map[manager.city[0].y][manager.city[0].x].busy = false
                    map[manager.city[0].y][manager.city[0].x].city = CityData()
                    map[y][x].city = manager.city[0]
                    manager.removeCity(0, y, x)
                    manager.clearTile(0)
                    manager.recountPeople(0)
                    manager.renameCity(0)
                    manager.recountActive(0)
                    map[y][x].busy = true
                }
                statMyCity = 0
            }
            drawMatr()
        }
    }

    companion object {
        private const val TAG = "MapView"
    }
}




