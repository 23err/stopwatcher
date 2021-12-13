package com.gb.stopwatch.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gb.stopwatch.databinding.ActivityMainBinding
import com.gb.stopwatch.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy{
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            viewModel.getTimerLiveData().observe(this@MainActivity){
                textTime.text = it
            }
            buttonStart.setOnClickListener {
                viewModel.startPressed()
            }
            buttonPause.setOnClickListener {
                viewModel.pausePressed()
            }
            buttonStop.setOnClickListener {
                viewModel.stopPressed()
            }
        }
    }
}

