package com.yusuftalhaklc.todoapplication.view

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusuftalhaklc.todoapplication.data.ToDoViewModel
import com.yusuftalhaklc.todoapplication.ui.theme.*
import java.util.*

@Composable
fun TodoListScreen(navController: NavController,viewModel: ToDoViewModel) {

    ChangeBarColors(bgColor, bgColor)

    val gridC = remember {
        mutableStateOf(2)
    }
    val configuration = LocalConfiguration.current
    // When orientation is Landscape
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            gridC.value = 3
        }
        // Other wise
        else -> {
            gridC.value = 2
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val initialValue = 0
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        "create_newtask_screen/${initialValue}"
                    )
                },
                backgroundColor = CategoryAll,
                contentColor = Color.White
                ,
                modifier = Modifier.size(60.dp)
                    ){
            Text(text = "+", fontSize = 24.sp, textAlign = TextAlign.Center)
        } },
        content = { it
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = bgColor)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, end = 12.dp)
                ) {
                    TodoLists(gridC.value,navController,viewModel)
                }
            }

        }
    )

}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoLists(
    gridC:Int,
    navController: NavController,
    viewModel: ToDoViewModel
) {

    val categoriesDesc = viewModel.categories.observeAsState().value
    val categoryTasksDesc = viewModel.categoryTasks.observeAsState().value

    val taskCount = remember {
        mutableStateOf(0)
    }
    if (categoryTasksDesc != null) {
        taskCount.value = if (categoryTasksDesc.isNotEmpty()){
            categoryTasksDesc.sum()
        } else{
            0
        }
    }

    var catMap : kotlin.collections.Map<String, Int> = emptyMap()
    try {
        if (categoriesDesc!!.isNotEmpty()  && categoryTasksDesc!!.isNotEmpty()){
            catMap  = categoriesDesc.zip(categoryTasksDesc).toMap()
        }

    } catch (e:java.lang.Exception){
        println(e.message)
    }

    val categories = listOf(
        "work",
        "music",
        "study",
        "travel",
        "home",
        "art",
        "entertainment",
        "shop",
        "other"
    )

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(gridC),
        content = {
            item (span ={ GridItemSpan(gridC) } ){
                Text(
                    text = "Lists",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, bottom = 12.dp),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                ) }
                item {
                    CategoryBox("all", taskCount.value,navController)
                }
            items(categories){ it ->
                     if (catMap.containsKey(it.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    })){
                    catMap[it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }]?.let{
                            it1 -> CategoryBox(it, it1,navController)
                         }
                }

                else{
                    CategoryBox(it, 0,navController)
                }
            }
    })
}

@Composable
fun CategoryBox(
    id:String,
    taskCounter: Int,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(size = 180.dp)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = Color.White)
            .clickable {
                navController.navigate(
                    "todo_category_screen/${id}"
                )
            }
    ){

        Column(modifier = Modifier.padding(start = 24.dp),
            verticalArrangement = Arrangement.SpaceAround) {

            Spacer(modifier = Modifier.padding(top = 36.dp))

            val colorAndIconPicker = ColorAndIconPicker()
            val colorSource= colorAndIconPicker.getColor(id)
            val drawSource= colorAndIconPicker.getIcon(id)

            Icon(
                modifier = Modifier
                .size(36.dp),
                painter = painterResource(
                    drawSource
                ),
                tint = colorSource,
                contentDescription = "$id task" // decorative element
            )

            Spacer(modifier = Modifier.padding(top = 28.dp))

            //task name
            Text(
                text = id.replaceFirstChar {
                    if (it.isLowerCase())
                        it.titlecase(Locale.getDefault())
                    else
                        it.toString()
                },
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 1.dp),
                fontSize = 20.sp,
            )
            val taskString = if(taskCounter == 1 || taskCounter == 0){
                "$taskCounter Task"
            }
            else{
                "$taskCounter Tasks"
            }
            // task counter text
            Text(text = taskString,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Light)
        }
    }
}