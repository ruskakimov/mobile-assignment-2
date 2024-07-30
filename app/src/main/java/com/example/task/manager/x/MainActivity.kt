package com.example.task.manager.x

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class TodoItem(val text: String, val priority: Priority, var isDone: Boolean = false)

enum class Priority {
    LOW, MEDIUM, HIGH
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val todoItems = mutableListOf(
            TodoItem("Task 1", Priority.HIGH),
            TodoItem("Task 2", Priority.MEDIUM),
            TodoItem("Task 3", Priority.LOW)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.todo_recycler_view)
        val todoAdapter = TodoAdapter(todoItems)
        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                // TODO: Handle adding a new TODO item
                true
            }
            R.id.action_hide_completed -> {
                // TODO: Handle hiding completed tasks
                true
            }
            R.id.action_show_completed -> {
                // TODO: Handle showing completed tasks
                true
            }
            R.id.action_delete_completed -> {
                // TODO: Handle deleting completed tasks
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class TodoAdapter(private val todoList: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val priorityLine: View = view.findViewById(R.id.priority_line)
        val checkmarkButton: ImageButton = view.findViewById(R.id.checkmark_button)
        val todoText: TextView = view.findViewById(R.id.todo_text)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoList[position]

        // Set text
        holder.todoText.text = item.text

        // Set priority
        val priorityColor = when (item.priority) {
            Priority.HIGH -> R.color.red
            Priority.MEDIUM -> R.color.orange
            Priority.LOW -> R.color.gray
        }
        holder.priorityLine.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, priorityColor))

        // Set style based on completion status
        if (item.isDone) {
            holder.todoText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_gray))
            holder.todoText.paintFlags = holder.todoText.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.todoText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.todoText.paintFlags = holder.todoText.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.checkmarkButton.setOnClickListener {
            item.isDone = !item.isDone
            notifyItemChanged(position)
        }

        holder.deleteButton.setOnClickListener {
            todoList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = todoList.size
}

