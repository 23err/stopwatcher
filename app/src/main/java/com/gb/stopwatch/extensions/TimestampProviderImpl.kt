package com.gb.stopwatch.extensions

import com.gb.stopwatch.domain.interfaces.TimestampProvider

object TimestampProviderImpl: TimestampProvider {
    override fun getMilliseconds(): Long {
        return System.currentTimeMillis()
    }
}