package com.psycodeinteractive.pixabay.domain

import android.util.Log

class Logger {
    fun v(message: String) {
        Log.v(getTag(), message)
    }

    fun v(throwable: Throwable) {
        Log.v(getTag(), "", throwable)
    }

    fun v(message: String, throwable: Throwable) {
        Log.v(getTag(), message, throwable)
    }

    fun e(message: String) {
        Log.e(getTag(), message)
    }

    fun e(throwable: Throwable) {
        Log.e(getTag(), "", throwable)
    }

    fun e(message: String, throwable: Throwable) {
        Log.e(getTag(), message, throwable)
    }

    fun i(message: String) {
        Log.i(getTag(), message)
    }

    fun i(throwable: Throwable) {
        Log.i(getTag(), "", throwable)
    }

    fun i(message: String, throwable: Throwable) {
        Log.i(getTag(), message, throwable)
    }

    fun d(message: String) {
        Log.d(getTag(), message)
    }

    fun d(throwable: Throwable) {
        Log.d(getTag(), "", throwable)
    }

    fun d(message: String, throwable: Throwable) {
        Log.d(getTag(), message, throwable)
    }

    fun w(message: String) {
        Log.w(getTag(), message)
    }

    fun w(throwable: Throwable) {
        Log.w(getTag(), "", throwable)
    }

    fun w(message: String, throwable: Throwable) {
        Log.w(getTag(), throwable)
    }

    private fun getTag(): String {
        val stackTrace = Thread.currentThread().stackTrace
        val stackTraceElement = stackTrace[5]
        return stackTraceElement.run { "${className.substringAfterLast('.')}.$methodName()/line:$lineNumber" }
    }
}
