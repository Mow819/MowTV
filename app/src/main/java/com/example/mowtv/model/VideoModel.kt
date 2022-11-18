package com.example.mowtv.model

import android.content.Context
import android.content.res.AssetFileDescriptor

class VideoModel {
    var videoName: String? = null

    constructor(videoName: String) {
        this.videoName = videoName
    }

}