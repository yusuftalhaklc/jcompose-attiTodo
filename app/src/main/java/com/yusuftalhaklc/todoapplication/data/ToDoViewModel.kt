package com.yusuftalhaklc.todoapplication.data

import android.app.Application
import android.util.Log
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): ViewModel() {

    var categories : LiveData<List<String>> = MutableLiveData(emptyList())
    var categoryTasks : LiveData<List<Int>> = MutableLiveData(emptyList())

    var categoryByCategoryName : LiveData<List<TodoItem>> =  MutableLiveData(emptyList())

    var TodoById: LiveData<TodoItem> =  MutableLiveData()
    var readAllData: LiveData<List<TodoItem>> = MutableLiveData()

    private val repository: ToDoRepo

    init {
        val toDoDao = TodoDatabase.getInstance(application).todoDao()
        repository = ToDoRepo(toDoDao)
        readAllData = repository.getAllData()

        getCategoryForToDoListScreen()
        getCategoryTaskForToDoListScreen()
    }

    fun addTodo(todo: TodoItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }

    fun deleteTodo(todo:TodoItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun updateTodo(todo:TodoItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }
    fun getCategoryByCategoryName(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            if (id == "all"){
                categoryByCategoryName =  repository.getAllData()
            }
            else {
                categoryByCategoryName =  repository.getCategoryByCategoryName(id.capitalize())
            }


        }
    }

    fun getById(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            TodoById =  repository.getById(id)
        }
    }

     private fun getCategoryForToDoListScreen()  {
         viewModelScope.launch (Dispatchers.IO){
             categories = repository.getCategoryForToDoListScreen()
         }

    }
    private fun getCategoryTaskForToDoListScreen()  {
        viewModelScope.launch (Dispatchers.IO){
            categoryTasks = repository.getCategoryTaskForToDoListScreen()
        }
    }



}