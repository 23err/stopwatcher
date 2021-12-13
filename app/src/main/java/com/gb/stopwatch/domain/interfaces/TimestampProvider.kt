package com.gb.stopwatch.domain.interfaces

interface TimestampProvider {
    fun getMilliseconds(): Long
}