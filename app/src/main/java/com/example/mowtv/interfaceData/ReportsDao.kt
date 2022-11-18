package com.example.mowtv.interfaceData


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mowtv.model.Report


@Dao
open interface ReportsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(report: Report)

    @Query("SELECT * FROM reports")
    fun getAllReportsData(): List<Report>
}