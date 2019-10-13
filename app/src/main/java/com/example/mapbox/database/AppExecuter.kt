package com.example.mapbox.database

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecuter private constructor(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {


    fun diskIO(): Executor {
        return diskIO
    }

    fun newtworkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    class MainThreadExecutor : Executor {

        private val handler = Handler(Looper.getMainLooper())
        override fun execute(runnable: Runnable) {
            handler.post(runnable)
        }
    }

    companion object {

        val LOCK = Any()
        private var instance: AppExecuter? = null

        fun getInstance(): AppExecuter {

            if (instance == null) {
                synchronized(LOCK) {
                    instance = AppExecuter(
                        Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3), MainThreadExecutor()
                    )
                }
            }
            return instance!!
        }
    }
}
