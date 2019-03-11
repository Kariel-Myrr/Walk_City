package com.example.lesson_1.walkcity.Map

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.example.lesson_1.walkcity.R
import org.jetbrains.anko.dip


class MapView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private lateinit var mBitmap: Bitmap
    private lateinit var picTile: Bitmap
    private lateinit var mCanvas: Canvas
    private lateinit var paint: Paint
    private lateinit var mBitmapPaint: Paint
    private var canvasSize: Float = 0f

    //Задаем матрицу
    private var Map : Array<Array<Tile>>
    private val N = 4//должно быть четным
    private val matrX = 400f//координаты центра поля
    private val matrY = 200f
    private val dMatrX = matrX - N*100f//то на сколько поле отходит от края(от х и у)
    private val dMatrY = matrY - N*50f


    //for scroll
    private val scaleGestureDetector = ScaleGestureDetector(context, MyScaleGestureListener())
    private var viewSize: Int = 10000
    private var mScaleFactor: Float = 1f
    private var detector = GestureDetector(context, MyGestureListener())

    init {

        Map = Array(N, {Array(N, {Tile(2)})})


        canvasSize = dip(1500f).toFloat()


        mBitmap = Bitmap.createBitmap(canvasSize.toInt(), canvasSize.toInt(), Bitmap.Config.ARGB_8888)
        mBitmapPaint = Paint(Paint.DITHER_FLAG)

        picTile = BitmapFactory.decodeResource(resources, R.drawable.sonic)

        mCanvas = Canvas(mBitmap)

        //mCanvas.drawBitmap(picTile, 50f, 50f, mBitmapPaint)

        paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = -0xfafb
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

        drawMatr(paint)


    }





    private fun drawMatr(paint : Paint){

        for(i in 0..(N-1)){
            if(i%2 == 0) paint.color = Color.GREEN
            else paint.color = Color.BLUE
            for(j in 0..i){
                drawTile((i-j)*100 + matrX, (i+j)*50 + dMatrY, Map[j][i], paint)
            }
        }
        invalidate()
    }


    private fun drawTile(X : Float, Y : Float, T : Tile, paint : Paint){


        mCanvas.drawLine(X, Y - 50f, 100f + X, Y, paint)
        mCanvas.drawLine(X - 100f, Y, X, Y - 50f, paint)
        mCanvas.drawLine(X - 100f, Y, X, 50f + Y, paint)
        mCanvas.drawLine(X, 50f + Y, 100f + X, Y, paint)
        if(T.type == 3){
            mCanvas.drawCircle(X, Y, 50f, paint)
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
                canvasSize = viewSize * mScaleFactor//изменяем хранимое в памяти значение размера канваса
                //используется при расчетах
                //по умолчанию после зума канвас отскролит в левый верхний угол.
                //Скролим канвас так, чтобы на экране оставалась
                //область канваса, над которой был жест зума
                //Для получения данной формулы достаточно школьных знаний математики (декартовы координаты).
                var scrollX = ((scrollX + focusX) * scaleFactor - focusX).toInt()
                scrollX = Math.min(Math.max(scrollX, 0), canvasSize.toInt() - viewSize)
                var scrollY = ((scrollY + focusY) * scaleFactor - focusY).toInt()
                scrollY = Math.min(Math.max(scrollY, 0), canvasSize.toInt() - viewSize)
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
            if (scrollX + distanceX < canvasSize - viewSize && scrollX + distanceX > 0) {
                scrollBy(distanceX.toInt(), 0)
            }
            //не даем канвасу показать края по вертикали
            if (scrollY + distanceY < canvasSize - viewSize && scrollY + distanceY > 0) {
                scrollBy(0, distanceY.toInt())
            }
            return true
        }

        //обрабатываем одиночный тап
        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            //получаем координаты ячейки, по которой тапнули
            val cellX = ((event.x + scrollX) / mScaleFactor).toInt()
            val cellY = ((event.y + scrollY) / mScaleFactor).toInt()




            val cord = Cord(0, 0, 0 , 0, N)

            tileTapCord(cellX.toFloat() - dMatrX, cellY.toFloat() - dMatrY, cord)

            println("I =  ${cord.I}   J =  ${cord.J} ")

            logicTapFun(cord)

            return true
        }

        fun tileTapCord(xTap : Float, yTap : Float, cord : Cord){

            val sX = ((xTap)/100).toInt()
            val sY = ((yTap)/50).toInt()
           // val cord = Cord(0, 0)


            if((sX+sY) % 2 == 1){
                if(-(xTap % 100) / 2 + 50 < yTap % 50){
                    cord.X = sX + 1
                    cord.Y = sY + 1
                }
                else {
                    cord.X = sX
                    cord.Y = sY
                }

            }

            else{
                if((xTap - sX*100) / 2 > yTap - sY*50){
                    cord.X = sX + 1
                    cord.Y = sY
                }
                else {
                    cord.X = sX
                    cord.Y = sY + 1
                }

            }

            cord.upDate()
        }


        //обрабатываем двойной тап
        override fun onDoubleTapEvent(event: MotionEvent): Boolean {
            //зумируем канвас к первоначальному виду
            mScaleFactor = 1f
            canvasSize = viewSize.toFloat()
            scrollTo(100, 100)//скролим, чтобы не было видно краев канваса.
           // invalidate()//перерисовываем канвас
            return false
        }


    }




    fun logicTapFun(cord : Cord){
        if(cord.I > 3 || cord.J > 3 || cord.I < 0 || cord.J < 0){}
        else {
            Map[cord.I][cord.J].type = 3
            println("aaa")
            drawMatr(paint)
        }
    }

}




