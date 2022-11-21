package com.example.mowtv.activity

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.mowtv.R
import com.example.mowtv.data.Action
import com.example.mowtv.databinding.ActivityStartBinding
import com.example.mowtv.viewModel.StartViewModel


class StartActivity : AppCompatActivity() {
    private var startViewModel: StartViewModel? = null
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        startViewModel = ViewModelProvider(this)[StartViewModel::class.java]
        binding.viewData = startViewModel
        binding.executePendingBindings()

        startViewModel!!.getAction()!!.observe(this
        ) { action -> action?.let { handleAction(it) } }

        supportActionBar?.hide()
    }
    private fun handleAction(action: Action) {
        when (action.getValue()) {
            Action.DATA_IS_LOADING -> {}
            Action.DATA_LOADED -> { //если загрузка прошла успешно - загруженные данные передаются в main
                var intent= Intent(this,MainActivity::class.java)
                intent.putExtra("VIDEOS", startViewModel!!.videos)
                startActivity(intent)
                finish()
            }
        }
    }

}
