package com.yusuftalhaklc.todoapplication.data

import androidx.lifecycle.LiveData

class ToDoRepo(private val todoDao: ToDoDao) {

    fun getAllData():LiveData<List<TodoItem>> = todoDao.getAllData()

    suspend fun addTodo(todo:TodoItem){
        todoDao.addTodo(todo)
    }
    suspend fun deleteTodo(todo:TodoItem){
        todoDao.deleteData(todo)
    }

    suspend fun updateTodo(todo:TodoItem){
        todoDao.updateToDo(todo)
    }

    fun getById(id:Int) : LiveData<TodoItem>{
       return todoDao.getContentById(id)
    }
    fun getCategoryByCategoryName(id:String): LiveData<List<TodoItem>>{
        return todoDao.getCategoryByCategoryName(id)
    }

    fun getCategoryForToDoListScreen() : LiveData<List<String>> {
        return todoDao.getCategoryForToDoListScreen()
    }

     fun getCategoryTaskForToDoListScreen() : LiveData<List<Int>> {
        return todoDao.getCategoryTaskForToDoListScreen()
    }


}