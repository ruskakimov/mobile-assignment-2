package com.example.task.manager.x

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

class AddTodoDialogFragment : DialogFragment(R.layout.dialog_add_todo) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoEditText = view.findViewById<EditText>(R.id.todo_edit_text)
        val priorityRadioGroup = view.findViewById<RadioGroup>(R.id.priority_radio_group)
        val addButton = view.findViewById<Button>(R.id.add_button)
        val cancelButton = view.findViewById<Button>(R.id.cancel_button)

        addButton.setOnClickListener {
            val todoText = todoEditText.text.toString()
            val priority = when (priorityRadioGroup.checkedRadioButtonId) {
                R.id.radio_high -> Priority.HIGH
                R.id.radio_medium -> Priority.MEDIUM
                else -> Priority.LOW
            }
            val todoItem = TodoItem(todoText, priority)
            val mainActivity = activity as MainActivity
            mainActivity.receiveTodoItem(todoItem)
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}