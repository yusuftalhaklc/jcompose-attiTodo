package com.yusuftalhaklc.todoapplication.view

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.yusuftalhaklc.todoapplication.R
import com.yusuftalhaklc.todoapplication.data.ToDoViewModel
import com.yusuftalhaklc.todoapplication.data.TodoItem
import com.yusuftalhaklc.todoapplication.ui.theme.CategoryAll
import com.yusuftalhaklc.todoapplication.ui.theme.ChangeBarColors
import com.yusuftalhaklc.todoapplication.ui.theme.ColorAndIconPicker
import com.yusuftalhaklc.todoapplication.ui.theme.bgColor
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateNewTaskScreen(
    navController: NavController,
    modelId: Int,
) {


    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    ChangeBarColors(bgColor, CategoryAll)
    val viewModel = ToDoViewModel(application = LocalContext.current.applicationContext as Application)

    viewModel.getById(modelId)
    val model = viewModel.TodoById.observeAsState().value


    val context = LocalContext.current



    var text by remember{
        mutableStateOf("")
    }
    var title by remember{
        mutableStateOf("")
    }
    var selectedItemForMainScreen by remember {
        mutableStateOf("Select Category")
    }
    var date by remember {
        mutableStateOf("")
    }

    val mDate = remember {
        mutableStateOf("Select Date")
    }


    LaunchedEffect(model){
        if (model != null) {
            text= model.content
            title = model.title
            selectedItemForMainScreen = model.category
            date= model.date
            mDate.value = model.date
        }
    }


    val isThereTitle = remember {
        mutableStateOf(Color.Gray)
    }
    val isThereAlarm = remember {
        mutableStateOf(Color.Gray)
    }
    val isThereCat = remember {
        mutableStateOf(Color.Gray)
    }
    val isThereCat2 = remember {
        mutableStateOf(Color.Gray)
    }
    Column(modifier = Modifier
        .fillMaxSize()
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Text(text = "  ", fontSize = 20.sp)
            Text(text = "New Task", fontSize = 20.sp)
            Icon(
            painter = painterResource(id =R.drawable.close ),
                tint = Color.Black  ,
                contentDescription = "Back To Main Screen",
            modifier = Modifier
                .size(20.dp)
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {

                    navController.navigate(
                        "todo_lists_screen"
                    )
                }
            )


        }
        Text(
            text = "What are you planning?",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        if(modelId == 0){
            BasicTextField(value = text , onValueChange = { text = it }
                ,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxSize()
                    .weight(2f)
                    .focusRequester(focusRequester)
                    .onFocusChanged {

                        if (it.isFocused) {
                            keyboardController?.show()
                        }
                    }
                ,
                textStyle = TextStyle(fontSize = 20.sp),
                cursorBrush = SolidColor(CategoryAll)
            )
            DisposableEffect(Unit) {
                focusRequester.requestFocus()
                onDispose { }
            }
        }
        else{
            BasicTextField(value = text , onValueChange = { text = it }
                ,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxSize()
                    .weight(2f)
                ,
                textStyle = TextStyle(fontSize = 20.sp),
                cursorBrush = SolidColor(CategoryAll)
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(28.dp)
            .background(Color.Gray)
            .height(1.dp))


        Column(modifier = Modifier
            .padding(28.dp)
            .verticalScroll(rememberScrollState())
            .weight(1f)) {
            Row() {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(
                        R.drawable.title
                    ),
                    tint = isThereTitle.value,
                    contentDescription = "Title" // decorative element
                )


                if (title.isNotEmpty()){
                    isThereTitle.value = CategoryAll
                }
                else {
                    isThereTitle.value = Color.Gray
                }
                BasicTextField(value = title , onValueChange = { title = it },
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth()

                    ,
                    textStyle = TextStyle(fontSize = 16.sp),
                    cursorBrush = SolidColor(CategoryAll),
                    decorationBox = {
                            innerTextField ->

                            if (title.isEmpty()) {
                                Text("Add Title", color = Color.Gray)
                            }
                            innerTextField()  //<-- Add this

                    }

                )

            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row() {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(
                        R.drawable.bell
                    ),
                    tint = isThereAlarm.value,
                    contentDescription = "Date" // decorative element
                )


                date = selectDateAndTime(mDate,isThereAlarm)

            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row() {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(
                        R.drawable.tag
                    ),
                    tint = isThereCat2.value,
                    contentDescription = "Category" // decorative element
                )

                val colorAndIconPicker = ColorAndIconPicker()

                var expanded by remember { mutableStateOf(false) }
                val items = colorAndIconPicker.getList()
                var selectedIndex by remember { mutableStateOf(0) }



                if (selectedItemForMainScreen != "Select Category"){
                    isThereCat.value = Color.Black
                    isThereCat2.value = CategoryAll
                }
                else {
                    isThereCat.value = Color.Gray
                    isThereCat2.value = Color.Gray
                }

                Box(modifier = Modifier
                    .padding(start = 4.dp)
                    .fillMaxSize()) {
                    Text(" $selectedItemForMainScreen",modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .padding(start = 12.dp, end = 20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(onClick = { expanded = true })
                        .background(
                            bgColor
                        ),
                        color = isThereCat.value)
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgColor)
                    ) {
                        items.forEachIndexed { index , s ->
                            DropdownMenuItem(onClick = {
                                selectedIndex = index
                                expanded = false
                                selectedItemForMainScreen = items[index].replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.getDefault()
                                    ) else it.toString()
                                }
                            }) {

                                Row() {
                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp),
                                        painter = painterResource(
                                           colorAndIconPicker.getIcon(items[index])
                                        ),
                                        tint = colorAndIconPicker.getColor(items[index]) ,
                                        contentDescription = "Icons" // decorative element
                                    )
                                    Text(
                                        text = items[index].capitalize(),
                                        modifier = Modifier.padding(start = 16.dp),

                                        )
                                }
                            }
                        }
                    }
                }
            }
        }
        Button(onClick = {

           try {
               if (
                   selectedItemForMainScreen.isNotEmpty() &&
                   title.isNotEmpty() &&
                   date.isNotEmpty() &&
                   text.isNotEmpty()
                       ) {
                   if (modelId == 0){
                       viewModel.addTodo(
                           TodoItem(
                               category = selectedItemForMainScreen,
                               title= title,
                               date = date,
                               content = text
                           )
                       )
                   }else {
                       val model2 = TodoItem(
                           id= modelId,
                           category = selectedItemForMainScreen,
                           title= title,
                           date = date,
                           content = text
                       )
                       viewModel.updateTodo(model2)
                   }


                   navController.navigate("todo_lists_screen")
                   if (modelId == 0){
                       Toast.makeText(
                           context,
                           "Created Todo!",
                           Toast.LENGTH_SHORT
                       ).show()
                   }else {
                       Toast.makeText(
                           context,
                           "Updated Todo!",
                           Toast.LENGTH_SHORT
                       ).show()
                   }

               }
               else {
                   Toast.makeText(
                       context,
                       "Please fill in all fields!",
                       Toast.LENGTH_SHORT
                   ).show()
               }

           }
           catch (e:java.lang.Exception){
               println(e.message)
           }
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = CategoryAll),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(CategoryAll)
        )
        {
            var buttonText by remember {
                mutableStateOf("")
            }
            if (modelId == 0){
                buttonText = "Create"
            }else {
                buttonText = "Update"
            }
            Text(text=buttonText,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400

            )
        }
    }

}
@SuppressLint("SuspiciousIndentation")
@Composable
fun selectDateAndTime(mDate:MutableState<String>,mycolor: MutableState<Color>):String{

    val context = LocalContext.current


    val textColor = remember {
        mutableStateOf(Color.Gray)
    }

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()



    Log.d("CREATESCREENDATEVALUE",mDate.value)
    if (mDate.value != "Select Date" ){
        mycolor.value = CategoryAll
        textColor.value = Color.Black
    }
    else {
        mycolor.value = Color.Gray
        textColor.value = Color.Gray
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(28.dp)
        .padding(start = 16.dp, end = 20.dp)
        .clip(RoundedCornerShape(4.dp))
        .clickable {
            dateDialogState.show()
        }
        ){
        Text(text = mDate.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, top = 2.dp),
            color = textColor.value
        )
    }


    MaterialDialog(
        properties = DialogProperties(),
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
               timeDialogState.show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
        ) {
            pickedDate = it

        }
    }
    MaterialDialog(

        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok") {
               mDate.value = "${pickedDate.toString()} ${pickedTime.toString()}"
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Pick a time",
            is24HourClock = true
        ) {
            pickedTime = it
        }
    }

    return mDate.value
}
