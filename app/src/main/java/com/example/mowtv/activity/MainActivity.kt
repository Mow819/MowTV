package com.example.mowtv.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.os.HandlerCompat.postDelayed
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.mowtv.R
import com.example.mowtv.adapter.ViewPagerAdapter
import com.example.mowtv.data.MowTVDB
import com.example.mowtv.databinding.ActivityMainBinding
import com.example.mowtv.fragment.ReportFragment
import com.example.mowtv.fragment.VideoFragment
import com.example.mowtv.model.Videos
import com.example.mowtv.viewModel.MainViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val TAG = VideoFragment::class.java.canonicalName
    lateinit var videoFragment: VideoFragment
    private lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var videos: Videos
    var list: List<String> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewData = mainViewModel
        binding.executePendingBindings()
        var viewpager = binding.viewpager
        if (savedInstanceState == null) {
            videoFragment = VideoFragment()
            GlobalScope.launch(Dispatchers.Main) {
                loadNameVideo()
                delay(100)
                setupViewPager(viewpager)
            }
        } else {
            videoFragment = supportFragmentManager.findFragmentByTag(VideoFragment.TAG) as VideoFragment
        }
        videos = (intent.getSerializableExtra("VIDEOS") as Videos?)!!


        supportActionBar?.hide()
    }

    suspend fun loadNameVideo(){
        GlobalScope.async {
            withContext(Dispatchers.Main) {
                for(i in 0 until videos.videos.size)
                    list+=videos.videos[i].videoIdentifier
                mainViewModel!!.cats=list
            }
        }.await()
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(VideoFragment(), "ВИДЕО")
        adapter.addFragment(ReportFragment(), "ИСТОРИЯ")
        viewpager.adapter = adapter
        binding.tabLayout.setupWithViewPager(viewpager)
        binding.tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_outline_play_circle_24)
        binding.tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_search_24)
    }
}