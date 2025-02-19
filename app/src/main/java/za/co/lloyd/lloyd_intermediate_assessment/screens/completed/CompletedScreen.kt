package za.co.lloyd.lloyd_intermediate_assessment.screens.completed

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.*
import za.co.lloyd.lloyd_intermediate_assessment.models.local.Task
import za.co.lloyd.lloyd_intermediate_assessment.utils.Constants.navScreens
import za.co.lloyd.lloyd_intermediate_assessment.widgets.edit_task.EditTaskDialog
import za.co.lloyd.lloyd_intermediate_assessment.widgets.navigation.ToDoAppNavScreens
import za.co.lloyd.lloyd_intermediate_assessment.widgets.search.CustomSearchViewRight
import za.co.lloyd.lloyd_intermediate_assessment.widgets.task_list.TaskList

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CompletedScreen(navController: NavController) {
    // Used for side effects on submit button
    val viewModelJob = Job()
    val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(1) }
    var searchText by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    val completedScreenViewModel = hiltViewModel<CompletedScreenViewModel>()

    var taskList by remember{ mutableStateOf(ArrayList<Task>()) }
    scope.launch { taskList = completedScreenViewModel.getListOfTasks(status = 1) as ArrayList }

    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.height(65.dp), containerColor = MaterialTheme.colors.surface, contentColor = MaterialTheme.colors.primaryVariant, tonalElevation = 2.dp) {
                navScreens.forEachIndexed { index, item ->
                    NavigationBarItem(selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index
                            when (index) {
                                0 -> navController.navigate(ToDoAppNavScreens.ToDoScreen.name){ launchSingleTop = true }
                                1 -> {}
                            }
                        },
                        label = { Text(text = item.title, style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.secondary, fontWeight = if (index == selectedItemIndex) FontWeight.ExtraBold else FontWeight.Bold)) },
                        alwaysShowLabel = true, icon = { Icon(tint = if (index == selectedItemIndex) Color.White else MaterialTheme.colors.secondary, imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon, contentDescription = item.title) },
                        colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colors.secondary)
                    )
                }
            }
        }){ padding ->
        Box(modifier = Modifier.fillMaxSize().padding(0.dp).background(color = MaterialTheme.colors.background)){
            Column(modifier = Modifier.fillMaxSize()){
                CustomSearchViewRight(placeholder = "Search for your task", search = searchText, modifier = Modifier.fillMaxWidth().background(color = Color.Transparent).padding(start = 16.dp, end = 16.dp, top = 8.dp), onValueChange = { text -> searchText = text }, weather = {
                    // Update local weather list
                    scope.launch { if (it.main?.temp!! > 0) taskList = completedScreenViewModel.getListOfTasks(status = 1) as ArrayList }
                })
                Spacer(modifier = Modifier.height(26.dp))
                TaskList(
                    home = false,
                    taskList,
                    onEditTask = { task ->
                        selectedTask = task
                        showEditDialog = true
                    },
                    onDeleteTask = { task ->
                        selectedTask = task
                        showDeleteDialog = true
                    }
                )
            }
        }

        // Edit Task Dialog
        if (showEditDialog && selectedTask != null) {
            EditTaskDialog(
                task = selectedTask!!,
                onDismiss = { showEditDialog = false },
                onTaskUpdated = { updatedTask ->
                    scope.launch {
                        completedScreenViewModel.updateTask(updatedTask)
                        taskList = completedScreenViewModel.getListOfTasks(status = 1) as ArrayList
                    }
                }
            )
        }

        // Delete Confirmation Dialog
        if (showDeleteDialog && selectedTask != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { androidx.compose.material3.Text("Delete Task") },
                text = { androidx.compose.material3.Text("Are you sure you want to delete this task?") },
                confirmButton = {
                    Button(
                        onClick = {
                            scope.launch {
                                completedScreenViewModel.deleteTask(selectedTask!!.recordId)
                                taskList = completedScreenViewModel.getListOfTasks(status = 1) as ArrayList
                            }
                            showDeleteDialog = false
                        }
                    ) {
                        androidx.compose.material3.Text("Delete")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showDeleteDialog = false }) {
                        androidx.compose.material3.Text("Cancel")
                    }
                }
            )
        }
    }
}