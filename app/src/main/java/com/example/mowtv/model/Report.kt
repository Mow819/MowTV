package com.example.mowtv.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val id_video: Int,
    val video_name: String,
    val start_time: Date
)
