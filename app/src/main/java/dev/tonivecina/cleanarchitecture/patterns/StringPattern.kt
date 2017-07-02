package dev.tonivecina.cleanarchitecture.patterns

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Toni Vecina on 6/29/17.
 */
object StringPattern {

    fun fromDate(date: Date, format: String): String {
        val simpleFormatter = SimpleDateFormat(format, Locale.getDefault())
        return simpleFormatter.format(date)
    }
}