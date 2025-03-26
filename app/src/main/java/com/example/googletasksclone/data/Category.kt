package com.example.googletasksclone.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "category")
data class Category(
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
    @PrimaryKey
    @ColumnInfo(name = "category_id") var id: String = UUID.randomUUID().toString(),
)