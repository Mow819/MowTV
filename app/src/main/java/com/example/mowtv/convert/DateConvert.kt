package com.example.mowtv.convert

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConvert { //конверт в дату и из даты
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val date = formatter.parse(value)
        return date
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        var string= formatter.format(date)
        return string
    }
}
