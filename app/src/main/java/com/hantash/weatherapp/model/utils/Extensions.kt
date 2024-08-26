package com.hantash.weatherapp.model.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.toDateTime(): Date {
    val df = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    df.timeZone = TimeZone.getDefault()

    val date: Date? = df.parse(this)
    return date ?: Date()
}

fun Date.beautifyToString(): String {
    val df = SimpleDateFormat("MMM d, yyyy hh:mm a", Locale.ENGLISH)
    df.timeZone = TimeZone.getDefault()

    return df.format(this)
}