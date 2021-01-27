package com.example.todo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.todo.database.TaskDatabase
import java.util.*

private const val DATABASE_NAME = "task-database-ver-1.01"

class TaskRepository private constructor(context: Context) {

    private val database: TaskDatabase = Room.databaseBuilder(
        context.applicationContext,
        TaskDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val taskDao = database.taskDao()

    fun getTasks(): LiveData<List<Task>> = taskDao.getTasks()

    fun getTask(id: UUID): LiveData<Task?> = taskDao.getTask(id)
    fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    companion object {
        private var INSTANCE: TaskRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) INSTANCE = TaskRepository(context)
        }

        fun get(): TaskRepository {
            return INSTANCE ?: throw IllegalStateException("TaskRepository must be initialized")
        }

    }

}