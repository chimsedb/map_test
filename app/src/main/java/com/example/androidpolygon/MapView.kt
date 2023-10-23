package com.example.androidpolygon

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets


class MapView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mScaleDetector: ScaleGestureDetector? = null
    private val mScaleFactor = 1f

    private fun readMapFie(): MapModel {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.assets.open("test.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
        }.toString()

        return Gson().fromJson(json, MapModel::class.java)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

//    private class ScaleListener() : SimpleOnScaleGestureListener() {
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            mScaleFactor *= detector.scaleFactor
//
//            // Don't let the object get too small or too large.
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f))
//            return true
//        }
//    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val mapModel = readMapFie()
        canvas?.save();

        val paint = Paint()
        paint.color = Color.RED
        canvas?.drawCircle(200F, 200F, 15F, paint)
//        canvas?.drawLine(
//            105.05491863700007F * 12,
//            8.853223018000051F * 12,
//            104.82151012700007F * 12,
//            8.73616835200005F * 12,
//            paint
//        )

        var count = 0
        val test = mapModel.points.map {
            it.map { t ->
                count += 10
                t + count
            }
        }
        for (i in 0..test.size - 2) {
            if (i == mapModel.points.size) {
                canvas?.drawLine(
                    test[i][0],
                    test[i][1],
                    test[0][0],
                    test[0][1],
                    paint
                )
            } else {
                canvas?.drawLine(
                    test[i][0],
                    test[i][1],
                    test[i + 1][0],
                    test[i + 1][1],
                    paint
                )
            }
        }
        canvas?.restore();
    }
}