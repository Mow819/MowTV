package com.example.mowtv.interfaceData

import com.example.mowtv.model.Video
import com.example.mowtv.model.Videos

interface OnMediaReadyCallback {
    fun onDataReady(data : Videos)
}