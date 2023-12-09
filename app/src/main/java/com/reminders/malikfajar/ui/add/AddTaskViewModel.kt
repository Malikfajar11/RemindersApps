package com.reminders.malikfajar.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reminders.malikfajar.data.Task
import com.reminders.malikfajar.data.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }
}