package com.azeez.threadtest

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val LOG_SOME_DATA = "com.azeez.threadtest.action.LOG_SOME_DATA"
private const val ACTION_BAZ = "com.azeez.threadtest.action.BAZ"

private const val EXTRA_PARAM1 = "com.azeez.threadtest.extra.PARAM1"
private const val EXTRA_PARAM2 = "com.azeez.threadtest.extra.PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
class MyIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork: ${intent.action}")
        when (intent.action) {
            LOG_SOME_DATA -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                logSomeDataForUser(param1 ?: "null", param2 ?: "null2")
            }
            ACTION_BAZ -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionBaz(param1, param2)
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun logSomeDataForUser(param1: String, param2: String) {
        Log.d(TAG, "DATA > $param1 $param2")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String, param2: String) {
    }

    companion object {
        val TAG = MyIntentService::class.java.simpleName

        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        @JvmStatic
        fun startActionLogging(context: Context, param1: String, param2: String) {
            Log.d(TAG, "startActionLogging: ")
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = LOG_SOME_DATA
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }

        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        @JvmStatic
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }
    }
}
