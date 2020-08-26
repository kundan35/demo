package com.kotlin.demo.data.source.local.table


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey var id: Long,
    var title: String,
    val url: String?,
    var bookMark: Boolean = false
)