package com.example.lesson_1.walkcity.Map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
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
    private  var tie = Tile(2)
    private lateinit var Map : Array<Array<Tile>>

    //for scroll
    private val scaleGestureDetector = ScaleGestureDetector(context, MyScaleGestureListener())
    private var viewSize: Int = 10000
    private var mScaleFactor: Float = 1f
    private var detector = GestureDetector(context, MyGestureListener())

    init {

        Map = Array(10, {Array(10, {Tile(2)})})


        canvasSize = dip(1500f).toFloat()


        mBitmap = Bitmap.createBitmap(canvasSize.toInt(), canvasSize.toInt(), Bitmap.Config.ARGB_8888)
        mBitmapPaint = Paint(Paint.DITHER_FLAG)

        picTile = BitmapFactory.decodeResource(resources, R.drawable.sonic)

        mCanvas = Canvas(mBitmap)

        mCanvas.drawBitmap(picTile, 50f, 50f, mBitmapPaint)

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



        DrawTile(100f, 100f, Map[0][0], paint)
        DrawTile(300f, 100f, Map[0][0], paint)
        DrawTile(200f, 50f, Map[0][0], paint)
        DrawTile(200f, 150f, Map[0][0], paint)


    }

    fun DrawTile(X : Float, Y : Float, T : Tile, paint : Paint){


        mCanvas.drawLine(X, Y - 50f, 100f + X, Y, paint)
        mCanvas.drawLine(X - 100f, Y, X, Y - 50f, paint)
        mCanvas.drawLine(X - 100f, Y, X, 50f + Y, paint)
        mCanvas.drawLine(X, 50f + Y, 100f + X, Y, paint)


    }

    override fun onDraw(canvas: Canvas) {

        canvas.save()
        canvas.scale(mScaleFactor, mScaleFactor)
        canvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas.restore()

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

            println("tap X = $cellX, Y = $cellY")
//
//            if (cellX/2 - 100 <= cellY && cellX/2 + 100 >= cellY && -cellX/2 + 100 <= cellY && -cellX/2 + 300 >= cellY) {
//                println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjj")
//                picTile = BitmapFactory.decodeResource(resources, R.drawable.sonic2)
//                mCanvas.drawBitmap(picTile, 50f, 50f, mBitmapPaint)
//                invalidate()
//            }




            if (cellX/2 + 100 <= cellY && cellX/2 + 300 >= cellY && -cellX/2 + 700 <= cellY && -cellX/2 + 900 >= cellY) {
                println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjj")
                picTile = BitmapFactory.decodeResource(resources, R.drawable.sonic2)
                mCanvas.drawBitmap(picTile, 50f, 50f, mBitmapPaint)
                invalidate()
            }


            return true
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

}




