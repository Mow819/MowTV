package com.example.mowtv.model

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.example.mowtv.activity.MainActivity
import com.example.mowtv.data.UtilsVideo
import com.example.mowtv.interfaceData.OnDataReadyCallback
import com.example.mowtv.interfaceData.OnMediaReadyCallback

class StartModel(context: Context) {
    var context=context
    fun loadVideos(OnMediaReadyCallback: OnMediaReadyCallback): Videos {
        return UtilsVideo(context).parseJsonDataFromAsset()
    }
}