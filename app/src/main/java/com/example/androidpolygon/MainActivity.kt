package com.example.androidpolygon

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.system.Os
import android.system.OsConstants
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpolygon.model.Features
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.RandomAccessFile
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    private fun readMapFie(context: Context): Features {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.assets.open("diaphanhuyen.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
        }.toString()

        return Gson().fromJson(json, Features::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Job() + Dispatchers.IO).launch {
            val timeLoad = System.currentTimeMillis()
            val data = readMapFie(context = applicationContext)
            Log.d("123123", "time load : ${System.currentTimeMillis() - timeLoad}")
            delay(5000L)
//            for (i in 1..100) {
                val timeExecute = System.currentTimeMillis()
                data.geometryList.forEach {
                    launch {
                        val polygon: MutableList<Gfg.Point> = mutableListOf()
                        it.geometry.points.first().forEach { t ->
                            polygon.add(Gfg.Point(t[0], t[1]))
                        }

                        Gfg.invoke(
                            polygon.toTypedArray(),
                            Gfg.Point(105.06243584100007f, 8.849240095000084f)
                        )
//                    Gfg._checkIfValidMarker(Gfg.Point(1f, 1f), polygon)
                    }
//                }

                Log.d("123123", "time execute : ${System.currentTimeMillis() - timeExecute}")
            }
        }

//        Log.d("123123", "${System.currentTimeMillis() - time}")
    }

}