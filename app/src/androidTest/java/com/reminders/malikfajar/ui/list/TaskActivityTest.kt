package com.reminders.malikfajar.ui.list

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.reminders.malikfajar.ui.add.AddTaskActivity
import com.reminders.malikfajar.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class TaskActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(TaskActivity::class.java)
        Intents.init()
    }
    @After
    fun delete() {
        Intents.release()
    }
    @Test
    fun shouldDisplayAddTaskActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(AddTaskActivity::class.java.name))
    }

}