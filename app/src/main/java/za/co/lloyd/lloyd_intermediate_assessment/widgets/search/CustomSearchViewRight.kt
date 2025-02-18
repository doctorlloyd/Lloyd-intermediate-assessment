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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import za.co.lloyd.lloyd_intermediate_assessment.widgets.Dialog
import za.co.lloyd.lloyd_intermediate_assessment.widgets.UserAlertDialog
import kotlinx.coroutines.*
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.forecast.Weather
import za.co.lloyd.lloyd_intermediate_assessment.screens.home.HomeViewModel

@Composable
fun CustomSearchViewRight(placeholder: String, search: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit, weather: (Weather) -> Unit) {
    val context = LocalContext.current
    val cityNotFound = remember { mutableStateOf(false) }

    // Used for side effects on submit button
    val viewModelJob = Job()
    val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Weather view model
    val homeViewModel = hiltViewModel<HomeViewModel>()
//    val searchScreenViewModel = hiltViewModel<SearchScreenViewModel>()

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically){
        OutlinedTextField(shape = MaterialTheme.shapes.large, singleLine = true, modifier = Modifier.height(50.dp).align(Alignment.CenterVertically).fillMaxWidth(),
            value = search, onValueChange = onValueChange, colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = MaterialTheme.colors.secondary, unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.secondary, backgroundColor = Color.Transparent), placeholder = { Text(text = placeholder, style = TextStyle(color = MaterialTheme.colors.secondary, textAlign = TextAlign.Center, fontSize = 18.sp)) }, trailingIcon = {
                IconButton(onClick = {
                    scope.launch {

                    }
                }){ Icon(imageVector = Icons.Default.Search, contentDescription = "", tint = MaterialTheme.colors.secondary) }
            })
    }

    // User was not found
    if (cityNotFound.value) Dialog(openDialog = cityNotFound, dialogTitle = "City information", headColor = Color.Red) {
        UserAlertDialog(message = "The City you're trying to search is not found.", openDialog = cityNotFound)
    }
}