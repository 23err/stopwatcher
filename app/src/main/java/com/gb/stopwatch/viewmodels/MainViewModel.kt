package com.gb.stopwatch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.stopwatch.domain.StopwatchListOrchestrator
import com.gb.stopwatch.domain.StopwatchStateHolder
import com.gb.stopwatch.domain.TimestampMillisecondsFormatter
import com.gb.stopwatch.extensions.TimestampProviderImpl
import com.gb.stopwatch.domain.ElapsedTimeCalculator
import com.gb.stopwatch.domain.StopwatchStateCalculator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainViewModel : ViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(
        Dispatchers.Main
                + job
    )

    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                TimestampProviderImpl,
                ElapsedTimeCalculator(TimestampProviderImpl)
            ),
            ElapsedTimeCalculator(TimestampProviderImpl),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(Dispatchers.Default + SupervisorJob())
    )

    private val timerLiveData = MutableLiveData<String>()
    fun getTimerLiveData():LiveData<String> = timerLiveData

    init {
        scope.launch {
            stopwatchListOrchestrator.ticker.collect{
                timerLiveData.postValue(it)
            }
        }
    }

    fun startPressed(){
        stopwatchListOrchestrator.start()
    }

    fun pausePressed(){
        stopwatchListOrchestrator.pause()
    }

    fun stopPressed(){
        stopwatchListOrchestrator.stop()
    }

    override fun onCleared() {
        job.cancelChildren()
        super.onCleared()
    }
}