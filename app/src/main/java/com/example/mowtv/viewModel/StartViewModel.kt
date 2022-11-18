package com.example.mowtv.viewModel

import android.app.Application
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.createBitmap
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mowtv.data.Action
import com.example.mowtv.interfaceData.OnDataReadyCallback
import com.example.mowtv.interfaceData.OnMediaReadyCallback
import com.example.mowtv.model.StartModel
import com.example.mowtv.model.Videos

class StartViewModel(application: Application) : AndroidViewModel(application) {
    var action = MutableLiveData<Action>()

    var stModel: StartModel = StartModel(getApplication())
    val text = ObservableField("")
    val isLoading = ObservableField(false)
    val dataIsLoading = ObservableField(true)
    var logo = ObservableField(Uri.parse("android.resource://your.package.here/drawable/white_logo.png"))

    fun getAction(): LiveData<Action?>? {
        return action
    }

    var videos: Videos? = null
    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: String) {
            isLoading.set(false)
            text.set(data)
        }
    }

    fun loadVideos() {
        text.set("Loading...")
        isLoading.set(true)
        logo.set(Uri.parse("android.resource://your.package.here/drawable/white_logo2.png"))
        dataIsLoading.set(false)

        Handler().postDelayed({ getVideos() }, 3000)
    }

    private fun getVideos() {
        videos = stModel.loadVideos(object : OnMediaReadyCallback {
            override fun onDataReady(data: Videos) {
                isLoading.set(false)
                onDataReadyCallback.onDataReady("Loaded")
            }
        })
        action.value = Action(Action.DATA_LOADED)
    }
}