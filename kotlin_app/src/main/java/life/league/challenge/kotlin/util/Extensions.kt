package life.league.challenge.kotlin.util

import android.util.Log

fun <T : Any> T.tag() = this::class.simpleName ?: ""
fun <T : Any> T.logD(message: String) = Log.d(this::class.simpleName, message)
fun <T : Any> T.logV(message: String) = Log.v(this::class.simpleName, message)
fun <T : Any> T.logE(message: String) = Log.e(this::class.simpleName, message)
fun <T : Any> T.logE(tr: Throwable) = Log.e(this::class.simpleName, "${tr.message} \n ${tr.stackTraceToString()}")