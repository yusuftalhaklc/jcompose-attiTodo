package com.yusuftalhaklc.todoapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todolist_table")
data class TodoItem (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo(name = "todo_category")
    val category:String,
    @ColumnInfo(name = "todo_title")
    val title:String,
    @ColumnInfo(name = "todo_date")
    val date:String,
    @ColumnInfo(name = "todo_content")
    val content:String,
    )