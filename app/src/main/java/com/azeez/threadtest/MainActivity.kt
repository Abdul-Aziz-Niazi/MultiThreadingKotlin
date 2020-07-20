package com.azeez.threadtest

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

const val TAG = "THREAD_SAMPLE"

const val MESSAGE_TAG = "MESSAGE_KEY"


class MainActivity : AppCompatActivity() {

    private val messageHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val bundle = msg.data
            val message = bundle.getString(MESSAGE_TAG, "")
            Log.d(TAG, "FROM MESSAGE HANDLER CLASS: $message")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * Default anonymous Runnable implementation
         */
        val runnable = object : Runnable {
            override fun run() {
                Log.d(TAG, "FROM ANONYMOUS CLASS")
            }

        }
        val handler = Handler()
        handler.post(runnable)


        /**
         * Implementation of Thread using Lambda
         */
        val runnableShort = Runnable {
            Log.d(TAG, "FROM LAMDBDA CLASS")
        }
        val shorterHandler = Handler()
        shorterHandler.post(runnableShort)

        /**
         * Direct Handler initialization using lambdas
         * since #Runnable is a single function interface
         * it can be reduced completely
         */
        Handler().post {
            Log.d(TAG, "FROM REDUCED LAMDBDA CLASS")
        }

        /**
         * Background Threads using Thread class
         */
        Thread(object : Runnable {
            override fun run() {
                Log.d(TAG, "Executing threads")
            }
        }).start()

        /**
         * Reducing the #Runnable implementation
         */
        Thread { Log.d(TAG, "Executing Reduced threads") }.start()


        /**
         * Using the kotlin thread() method
         */
        thread(start = true) {
            Log.d(TAG, "Executing threads using kotlin thread() method")
        }

        /**
         * Sending a message to the UI
         */
        thread(start = true) {
            val bundle = Bundle()
            bundle.putString(MESSAGE_TAG, ">DATA FROM THREAD<")
            Message().also {
                it.data = bundle
                messageHandler.sendMessage(it)
            }
        }

    }
}