package com.wizardrrr.todoapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.wizardrrr.todoapp.adapters.TaskAdapter
import com.wizardrrr.todoapp.databinding.ActivityMainBinding
import com.wizardrrr.todoapp.room.database.TodoAppDatabase
import com.wizardrrr.todoapp.room.entities.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var base:TodoAppDatabase
    private  lateinit var binding: ActivityMainBinding
    lateinit var tasksArray:List<Task>
    var idTask:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        base = TodoAppDatabase.getDataBase(applicationContext)

        showTasksU()

        binding.btnCancel.visibility = View.GONE
        binding.btnUpdate.visibility = View.GONE
        binding.btnDelete.visibility = View.GONE

        if(intent.extras?.isEmpty == false){
            idTask = intent.extras!!.getLong("id",0)
            binding.txtTitle.setText(intent.extras?.getString("title",""))
            binding.txtDescription.setText(intent.extras?.getString("description",""))

            binding.btnSave.visibility = View.GONE
            binding.btnCancel.visibility = View.VISIBLE
            binding.btnUpdate.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.VISIBLE
        }
    }

    fun cancel(v:View) {
        startActivity(Intent(applicationContext,MainActivity::class.java))
        finish()
    }
    fun save(v: View) {
        val title = binding.txtTitle
        val description = binding.txtDescription

        if(title.text.toString().length < 2){
            title.error = "Debe ingresar un nombre válido"
        } else if (description.text.toString().isEmpty()){
            description.error = "Debe ingresar la descripción"
        } else {
            GlobalScope.launch {
                base.taskDao().insert(Task(title = title.text.toString(), description = description.text.toString()))
                runOnUiThread{
                    clearFields()
                    binding.txtMessage.text =  "Guardado con exito!"
                    binding.txtMessage.setTextColor(Color.GREEN)
                }
            }
        }
    }

    fun delete(v: View) {

            GlobalScope.launch {
                base.taskDao().delete(Task(id = idTask,title = binding.txtTitle.text.toString(), description = binding.txtDescription.text.toString()))
                runOnUiThread{
                    clearFields()
                    binding.txtMessage.text =  "Eliminado con exito!"
                    binding.txtMessage.setTextColor(Color.RED)

                    binding.btnSave.visibility = View.VISIBLE
                    binding.btnCancel.visibility = View.GONE
                    binding.btnUpdate.visibility = View.GONE
                    binding.btnDelete.visibility = View.GONE
                }
            }
    }
    fun update(v: View) {
        val title = binding.txtTitle
        val description = binding.txtDescription

        if(title.text.toString().length < 2){
            title.error = "Debe ingresar un nombre válido"
        } else if (description.text.toString().isEmpty()){
            description.error = "Debe ingresar la descripción"
        } else {
            GlobalScope.launch {
                base.taskDao().update(Task(id = idTask,title = title.text.toString(), description = description.text.toString()))
                runOnUiThread{
                    clearFields()
                    binding.txtMessage.text =  "Tarea actualizada con exito!"
                    binding.txtMessage.setTextColor(Color.GREEN)

                    binding.btnSave.visibility = View.VISIBLE
                    binding.btnCancel.visibility = View.GONE
                    binding.btnUpdate.visibility = View.GONE
                    binding.btnDelete.visibility = View.GONE
                }
            }
        }
    }

    private fun clearFields(){
        binding.txtTitle.text?.clear()
        binding.txtDescription.text?.clear()
        idTask = 0
    }

    private fun showTasksU(){
        GlobalScope.launch {
            val tasksData = base.taskDao().getAll()

            runOnUiThread{
                tasksData.observe(this@MainActivity,{ tasks ->
                    tasksArray = arrayListOf()
                    tasksArray = tasks

                    val adapter = TaskAdapter(tasksArray)
                    binding.rvTasks.layoutManager = LinearLayoutManager(applicationContext)
                    binding.rvTasks.adapter = adapter
                })
            }
        }
    }
}