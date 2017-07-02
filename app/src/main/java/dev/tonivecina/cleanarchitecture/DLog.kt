package dev.tonivecina.cleanarchitecture

import android.util.Log

/**
 * @author Toni Vecina on 6/27/17.
 */
object DLog {

    private val SIGNATURE: String = "CleanArchitecture"
    private val SEPARATOR: String = "--------------------"

    private enum class Type {
        ERROR,
        INFO,
        SUCCESS,
        WARNING
    }

    private fun set(message: String, type: Type) {

        if (!BuildConfig.DEBUG) {
            return
        }

        when (type) {
            Type.ERROR -> {
                Log.e(SIGNATURE, message)
                Log.e(SIGNATURE, SEPARATOR)
            }

            Type.INFO -> {
                Log.i(SIGNATURE, message)
                Log.i(SIGNATURE, SEPARATOR)
            }

            Type.SUCCESS -> {
                Log.d(SIGNATURE, message)
                Log.d(SIGNATURE, SEPARATOR)
            }

            Type.WARNING -> {
                Log.w(SIGNATURE, message)
                Log.w(SIGNATURE, SEPARATOR)
            }
        }
    }

    fun error(message: String) = set(message, Type.ERROR)

    fun info(message: String) = set(message, Type.INFO)

    fun success(message: String) = set(message, Type.SUCCESS)

    fun warning(message: String) = set(message, Type.WARNING)

}