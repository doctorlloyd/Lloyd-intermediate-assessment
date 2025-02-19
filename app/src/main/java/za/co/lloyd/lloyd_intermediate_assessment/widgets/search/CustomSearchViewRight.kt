package za.co.lloyd.lloyd_intermediate_assessment.widgets.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import za.co.lloyd.lloyd_intermediate_assessment.widgets.Dialog
import za.co.lloyd.lloyd_intermediate_assessment.widgets.UserAlertDialog
import kotlinx.coroutines.*
import za.co.lloyd.lloyd_intermediate_assessment.models.local.Task
import za.co.lloyd.lloyd_intermediate_assessment.screens.completed.CompletedScreenViewModel

@Composable
fun CustomSearchViewRight(placeholder: String, search: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit, task: (ArrayList<Task>?) -> Unit) {
    val taskNotFound = remember { mutableStateOf(false) }

    // Used for side effects on submit button
    val viewModelJob = Job()
    val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Completed Task view model
    val completedScreenViewModel = hiltViewModel<CompletedScreenViewModel>()

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically){
        OutlinedTextField(shape = MaterialTheme.shapes.large, singleLine = true, modifier = Modifier.height(50.dp).align(Alignment.CenterVertically).fillMaxWidth(),
            value = search, onValueChange = { newValue ->
                onValueChange(newValue)
                scope.launch {
                    if (newValue.length >= 3) {
                        val taskList = completedScreenViewModel.searchTasks(query = newValue) as ArrayList<Task>?
                        task(taskList)
                        taskNotFound.value = taskList.isNullOrEmpty()
                    }
                }
            }, colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = MaterialTheme.colors.secondary, unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.secondary, backgroundColor = Color.Transparent), placeholder = { Text(text = placeholder, style = TextStyle(color = MaterialTheme.colors.secondary, textAlign = TextAlign.Center, fontSize = 18.sp)) })
    }

    // User was not found
    if (taskNotFound.value) Dialog(openDialog = taskNotFound, dialogTitle = "Task information", headColor = Color.Red) {
        UserAlertDialog(message = "The Task you're trying to search is not found.", openDialog = taskNotFound)
    }
}