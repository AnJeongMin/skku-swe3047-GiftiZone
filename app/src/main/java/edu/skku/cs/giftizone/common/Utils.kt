package edu.skku.cs.giftizone.common

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

fun toast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(context, message, duration).show()
    }
}