package com.wizardrrr.todoapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wizardrrr.todoapp.room.database.TodoAppDatabase
import com.wizardrrr.todoapp.room.entities.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var base:TodoAppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        base = TodoAppDatabase.getDataBase(applicationContext)

        GlobalScope.launch {
            base.taskDao().insert(Task(title = "pasear al perro", description = "Lorem Lorem Lorem Lorem"))
        }
    }
}