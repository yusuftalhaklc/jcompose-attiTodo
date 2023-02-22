package com.yusuftalhaklc.todoapplication.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.GestureDetector
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusuftalhaklc.todoapplication.DateEdıtor
import com.yusuftalhaklc.todoapplication.R
import com.yusuftalhaklc.todoapplication.data.ToDoViewModel
import com.yusuftalhaklc.todoapplication.data.TodoItem
import com.yusuftalhaklc.todoapplication.ui.theme.ChangeBarColors
import com.yusuftalhaklc.todoapplication.ui.theme.ColorAndIconPicker
import com.yusuftalhaklc.todoapplication.ui.theme.bgColor
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun TodoCategoryScreen(
    navController: NavController,
    id:String,
    viewModel: ToDoViewModel
) {


    val categoriesDesc = viewModel.categories.observeAsState().value
    val categoryTasksDesc = viewModel.categoryTasks.observeAsState().value

    val taskCountAll = remember {
        mutableStateOf(0)
    }
    if (categoryTasksDesc != null) {
        taskCountAll.value = if (categoryTasksDesc.isNotEmpty()){
            categoryTasksDesc.sum()
        } else{
            0
        }
    }

    var catMap : Map<String, Int> = emptyMap()
    try {
        if (categoriesDesc!!.isNotEmpty()  && categoryTasksDesc!!.isNotEmpty()){
            catMap  = categoriesDesc.zip(categoryTasksDesc).toMap()
        }

    } catch (e:java.lang.Exception){
        println(e.message)
    }

    val taskCount = if(id == "all"){
        taskCountAll.value
    }
    else if (catMap.containsKey(id.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })){
        catMap[id.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()}]
    }
    else {
        0
    }
    /*
    viewModel.getCategoryByCategoryName(id.lowercase())
    val currentCategoryTodos = viewModel.categoryByCategoryName.observeAsState().value
    Log.d("categoryscreen", currentCategoryTodos?.size.toString())*/

    viewModel.getCategoryByCategoryName(id.lowercase())
    val currentCategoryTodos by viewModel.categoryByCategoryName.observeAsState(emptyList())

    val todoItems= viewModel.categoryByCategoryName.value

    val colorAndIconPicker: ColorAndIconPicker = ColorAndIconPicker()
    val colorSource= colorAndIconPicker.getColor(id)
    val drawSource= colorAndIconPicker.getIcon(id)

    ChangeBarColors(colorSource, bgColor)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorSource)
    ){
        Icon(
            painter = painterResource(id = R.drawable.back ),
            tint = Color.White  ,
            contentDescription = "Back To Main Screen",
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp)
                .size(24.dp)
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {

                    navController.navigate(
                        "todo_lists_screen"
                    )
                }
        )

       Column(modifier = Modifier
           .fillMaxWidth()
           .height(290.dp)
           .weight(2f)
           .background(colorSource),
               verticalArrangement = Arrangement.Center
       ) {

           Column(modifier = Modifier.padding(start = 36.dp)) {
               Box(modifier =
               Modifier
                   .size(52.dp)
                   .clip(CircleShape)
                   .background(bgColor)

                   ) {
                   Icon(
                       modifier = Modifier
                           .align(Alignment.Center)
                           .size(24.dp),
                       painter = painterResource(
                           drawSource
                       ),
                       tint = colorSource,
                       contentDescription = "$id task" // decorative element
                   )
               }

               Spacer(modifier = Modifier.padding(top = 8.dp))

               Text(
                   text = id.replaceFirstChar {
                       if (it.isLowerCase())
                           it.titlecase(Locale.getDefault())
                       else
                           it.toString()
                   },
                   color = Color.White,
                   fontWeight = FontWeight.Bold,
                   fontSize = 40.sp,
               )

               val taskString = if (taskCount == 0 || taskCount == 1){
                   "$taskCount Task"
               }else {
                   "$taskCount Tasks"
               }

               // task counter text
               Text(text = taskString,
                   fontSize = 16.sp,
                   color = Color.White,
                   fontWeight = FontWeight.Light)
               }
           }

        Column (modifier = Modifier
            .fillMaxSize()
            .weight(4f)
            .clip(RoundedCornerShape(36.dp, 36.dp, 0.dp, 0.dp))
            .background(bgColor)){

            if(currentCategoryTodos.isNotEmpty()){
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),content = {

                    items(currentCategoryTodos.sortedByDescending{it.id}){


                        CategoryItems(viewModel,navController,it)
                    }
                })
            }
            else {
                    Text(text = "Nothing to show here",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.fillMaxSize()
                    )
            }

       }
    }
}

@Composable
fun Header(categoryName:String, date: String) {


}

@Composable
fun CategoryItems(viewModel: ToDoViewModel,navController: NavController, todoItem: TodoItem) {


    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 28.dp, end = 36.dp, top = 4.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable {

        }
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    navController.navigate(
                        route = "create_newtask_screen/${todoItem.id}"
                    )
                },
                onLongPress = {
                    showDialog = true // dialog açılacak
                }
            )
        }
        /*.clickable {
            navController.navigate(
                route = "create_newtask_screen/${it.id}"
            ) }*/

    )


    {


        Text(text = todoItem.title,
            maxLines = 1,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            color = Color.Black, fontSize = 16.sp)

        Text(text = DateEdıtor(todoItem.date).dayMonthText,
            modifier = Modifier.padding(start = 8.dp, top = 32.dp, bottom = 8.dp),
            color = Color.Gray, fontSize = 12.sp)
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false // dialog kapatılacak
            },
            title = {
                Text("Delete")
            },
            text = {
                Text("Are you sure you want to delete '${todoItem.title}' ")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteTodo(todoItem)

                        showDialog = false // dialog kapatılacak
                    }
                ) {
                    Text(
                        "Delete",
                    )
                }
            }, dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false // dialog kapatılacak
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


}
