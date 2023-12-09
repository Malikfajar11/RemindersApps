package com.reminders.malikfajar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("tasks")
data class Task(

    @PrimaryKey(true)
    val id: Int = 0,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("dueDate")
    val dueDateMillis: Long,

    @ColumnInfo("completed")
    val isCompleted: Boolean = false
)
