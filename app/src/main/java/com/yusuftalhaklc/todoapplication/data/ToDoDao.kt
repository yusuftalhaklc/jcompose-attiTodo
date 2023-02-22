package com.yusuftalhaklc.todoapplication.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todoItem: TodoItem)

    @Update
    suspend fun updateToDo(todoItem: TodoItem)

    @Delete
    suspend fun deleteData(todoItem: TodoItem)

    @Query("SELECT * FROM todolist_table ")
    fun getAllData(): LiveData<List<TodoItem>>

    @Query("SELECT * from todolist_table where id = :id")
    fun getContentById(id: Int) : LiveData<TodoItem>

    @Query("SELECT * from todolist_table where todo_category = :categoryName")
    fun getCategoryByCategoryName(categoryName: String) : LiveData<List<TodoItem>>

    @Query("SELECT DISTINCT todo_category FROM todolist_table GROUP BY todo_category ORDER BY count(todo_category) DESC")
    fun getCategoryForToDoListScreen(): LiveData<List<String>>

    @Query("SELECT count(todo_category) FROM todolist_table GROUP BY todo_category ORDER BY count(todo_category) DESC")
    fun getCategoryTaskForToDoListScreen(): LiveData<List<Int>>

}