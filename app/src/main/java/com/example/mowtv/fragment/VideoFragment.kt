package com.example.mowtv.fragment

import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mowtv.R
import com.example.mowtv.convert.DateConvert
import com.example.mowtv.data.MowTVDB
import com.example.mowtv.databinding.FragmentVideoBinding
import com.example.mowtv.model.Report
import com.example.mowtv.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class VideoFragment : Fragment(), SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    companion object {
        val TAG = VideoFragment::class.java.canonicalName
        fun newInstance(): VideoFragment {
            return VideoFragment()
        }
    }

    lateinit var binding: FragmentVideoBinding
    var mediaPlayer: MediaPlayer? = null
    var surfaceView: SurfaceView? = null
    var surfaceHolder: SurfaceHolder? = null
    var pausing = false
    var currentPos = 0
    var playableVideo = 0
    var clicked = false
    lateinit var afd: AssetFileDescriptor
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = activity?.run {
            ViewModelProvider(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, null, false)
        binding.viewData = mainViewModel
        binding.executePendingBindings()

        surfaceView = binding.surface

        surfaceHolder = surfaceView!!.holder
        surfaceHolder!!.addCallback(this)

        surfaceView!!.setOnClickListener {
            if (!clicked) {
                mediaPlayer!!.reset()
                clicked = true
                play()
            } else clicked = false
        }
        surfaceView!!.setOnLongClickListener {
            currentPos = mediaPlayer!!.currentPosition;
            mediaPlayer!!.pause()
            pausing = true
            return@setOnLongClickListener true
        }
        surfaceView!!.setOnTouchListener(speakTouchListener)

        return binding.root
    }

    override fun onPrepared(player: MediaPlayer) {
        GlobalScope.launch {insertData()}
        mediaPlayer!!.start()
    }

    suspend fun insertData() {
        val start_time = Date()
        val id_video = playableVideo
        val video_name = mainViewModel.cats[playableVideo]
        val report = Report(id = null, id_video, video_name, start_time) //id генерируется автоматически поэтому null
        mainViewModel.insertReportInfo(report)
    }

    val speakTouchListener: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(pView: View, pEvent: MotionEvent): Boolean {
            pView.onTouchEvent(pEvent)
            if (pEvent.action === MotionEvent.ACTION_UP) {
                if (pausing) {
                    mediaPlayer!!.start()
                    pausing = false
                }
            }
            return false
        }
    }

    override fun onPause() {
        super.onPause()
        reliaseMP()
    }

    override fun onDestroy() {
        super.onDestroy()
        reliaseMP()
    }

    override fun onStart() {
        super.onStart()
        reliaseMP()
    }

    private fun reliaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer!!.seekTo(currentPos)
                mediaPlayer!!.release()
            } catch (ex: Exception) {
                mediaPlayer = MediaPlayer()
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        play()
    }

    fun play() {
        try {
            if (mediaPlayer == null) mediaPlayer = MediaPlayer()

            afd = requireContext().assets.openFd("Videos/${mainViewModel.cats[playableVideo]}") //название подгружаю из списка полученного из main
            mediaPlayer!!.setOnCompletionListener {
                mediaPlayer!!.reset()
                play()
            }
            mediaPlayer!!.setDisplay(surfaceHolder)
            mediaPlayer!!.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mediaPlayer!!.prepare()
            mediaPlayer!!.setOnPreparedListener(this)
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            playableVideo++
            if (playableVideo == mainViewModel.cats.size)
                playableVideo = 0

        } catch (e: IllegalArgumentException) {
            e.printStackTrace();
        } catch (e: IllegalStateException) {
            e.printStackTrace();
        } catch (e: IOException) {
            e.printStackTrace();
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
}
