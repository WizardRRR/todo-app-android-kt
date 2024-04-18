package com.wizardrrr.todoapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wizardrrr.todoapp.room.daos.TaskDao
import com.wizardrrr.todoapp.room.entities.Task

@Database(entities = [Task::class], version = 1)
abstract class TodoAppDatabase:RoomDatabase()   {
    abstract fun taskDao():TaskDao

    companion object{
        fun getDataBase(ctx:Context):TodoAppDatabase{
            val db = Room.databaseBuilder(ctx,TodoAppDatabase::class.java,name = "todoapp").build()
            return db
        }
    }
}