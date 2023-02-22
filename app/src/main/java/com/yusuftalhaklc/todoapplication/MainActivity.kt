package com.yusuftalhaklc.todoapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.yusuftalhaklc.todoapplication.data.ToDoViewModel
import com.yusuftalhaklc.todoapplication.ui.theme.ChangeBarColors
import com.yusuftalhaklc.todoapplication.ui.theme.ToDoApplicationTheme
import com.yusuftalhaklc.todoapplication.ui.theme.bgColor
import com.yusuftalhaklc.todoapplication.view.CreateNewTaskScreen
import com.yusuftalhaklc.todoapplication.view.TodoCategoryScreen
import com.yusuftalhaklc.todoapplication.view.TodoListScreen
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ToDoViewModel(application = application)

        setContent {
            ToDoApplicationTheme {
                ChangeBarColors(bgColor, bgColor)
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "todo_lists_screen"){

                    composable(route = "todo_lists_screen") {
                        TodoListScreen(navController = navController,viewModel)
                    }

                    composable(route = "create_newtask_screen/{categoryID}",
                        arguments = listOf(
                            navArgument("categoryID"){
                                type = NavType.IntType
                            })) {

                        val modelId = remember {
                            it.arguments?.getInt("categoryID")
                        }
                        if (modelId != null) {
                            CreateNewTaskScreen(
                                navController = navController,
                                modelId = modelId
                            )
                        }
                    }

                    composable(route = "todo_category_screen/{categoryID}",
                         arguments = listOf(
                             navArgument("categoryID"){
                                type = NavType.StringType
                             })) {

                        val categoryID = remember {
                            it.arguments?.getString("categoryID")
                        }

                            TodoCategoryScreen(
                                navController = navController,
                                id = categoryID ?:"all",
                                viewModel
                        )
                    }
                }
            }
        }
    }

}

