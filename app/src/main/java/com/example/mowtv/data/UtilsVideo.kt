package com.example.mowtv.data

import android.content.Context
import android.os.Handler
import android.util.Log
import com.example.mowtv.interfaceData.OnMediaReadyCallback
import com.example.mowtv.model.Video
import com.example.mowtv.model.Videos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.text.Charsets.UTF_8

class UtilsVideo(private var context: Context) {
    fun parseJsonDataFromAsset():Videos {

        val jsonFileString = getJsonDataFromAsset()
        val gson = Gson()
        val listVideo = object : TypeToken<Videos>() {}.type
        var videos: Videos = gson.fromJson(jsonFileString, listVideo)
        return videos
    }

    fun getJsonDataFromAsset(): String? {
        val jsonString: String
        try {
            val file: InputStream = context.assets.open("Media/medialist.json")

            val size: Int = file.available()
            val buffer = ByteArray(size)
            file.read(buffer)
            file.close()

            jsonString = String(buffer, Charsets.UTF_8)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}
