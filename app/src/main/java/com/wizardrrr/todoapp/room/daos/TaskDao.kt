package com.wizardrrr.todoapp.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wizardrrr.todoapp.room.entities.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAll():LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id:Long):LiveData<Task>
}
