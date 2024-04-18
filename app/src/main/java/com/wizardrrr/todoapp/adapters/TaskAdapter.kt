package com.wizardrrr.todoapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wizardrrr.todoapp.MainActivity
import com.wizardrrr.todoapp.databinding.ItemTaskBinding
import com.wizardrrr.todoapp.room.entities.Task

class TaskAdapter(private val data: List<Task>)
    :RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtTitleTask.text = data[position].title
        holder.binding.txtDescriptionTask.text = data[position].description

        holder.binding.cardTask.setOnClickListener{
            val intent = Intent(holder.binding.cardTask.context.applicationContext,MainActivity::class.java)
            intent.putExtra("id", data[position].id)
            intent.putExtra("title", data[position].title)
            intent.putExtra("description", data[position].description)

            holder.binding.cardTask.context.startActivity(intent)
        }
    }
}