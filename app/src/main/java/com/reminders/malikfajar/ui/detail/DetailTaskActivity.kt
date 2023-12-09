package com.reminders.malikfajar.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.reminders.malikfajar.R
import com.reminders.malikfajar.ui.ViewModelFactory
import com.reminders.malikfajar.utils.DateConverter
import com.reminders.malikfajar.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)


        val viewModelFactory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, viewModelFactory)[DetailTaskViewModel::class.java]

        val id = intent.getIntExtra(TASK_ID, 0)
        detailTaskViewModel.setTaskId(id)

        detailTaskViewModel.task.observe(this){
            it?.let {
                val title = findViewById<TextInputEditText>(R.id.detail_ed_title)
                val descrition = findViewById<TextInputEditText>(R.id.detail_ed_description)
                val dueDate = findViewById<TextInputEditText>(R.id.detail_ed_due_date)

                title.setText(it.title)
                descrition.setText(it.description)
                dueDate.setText(DateConverter.convertMillisToString(it.dueDateMillis))
            }
        }

        val delete = findViewById<Button>(R.id.btn_delete_task)
        delete.setOnClickListener {
            detailTaskViewModel.deleteTask()
            finish()
        }
    }
}