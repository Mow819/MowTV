package com.example.mowtv.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mowtv.convert.DateConvert
import com.example.mowtv.interfaceData.ReportsDao
import com.example.mowtv.model.Report

@Database(entities = [Report::class], version = 5)
@TypeConverters(DateConvert::class)
abstract class MowTVDB : RoomDatabase()  {
    abstract fun reportsDao(): ReportsDao

    companion object {

        @VisibleForTesting
        private const val databaseName = "minitv.db"
        private var instance: MowTVDB? = null

        fun getDatabase(context: Context): MowTVDB? {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(context, MowTVDB::class.java, databaseName)
                    .createFromAsset("DataBase/$databaseName")
                    .build()
            }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
    }
}