package com.example.googletasksclone.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.googletasksclone.data.Task
import com.example.googletasksclone.data.source.local.TasksDao

@Database(entities = [Task::class], version = 3, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun taskDao(): TasksDao
}