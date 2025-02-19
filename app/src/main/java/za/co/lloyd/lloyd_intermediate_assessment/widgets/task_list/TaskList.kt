package za.co.lloyd.lloyd_intermediate_assessment.widgets.task_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.lloyd.lloyd_intermediate_assessment.models.local.Task

@Composable
fun TaskList(
    home: Boolean,
    taskList: List<Task>,
    onEditTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    if (taskList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskList.size) { i ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { onDeleteTask(taskList[i]) }, Modifier.weight(1f)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = if(home) Color.White else Color.Black)
                    }
                    Text(
                        text = taskList[i].title,
                        Modifier.weight(1f),
                        style = TextStyle(fontSize = 18.sp, color = if(home) Color.White else Color.Black)
                    )
                    Text(
                        text = taskList[i].description,
                        Modifier.weight(1f),
                        style = TextStyle(fontSize = 18.sp, color = if(home) Color.White else Color.Black)
                    )
                    IconButton(onClick = { onEditTask(taskList[i]) }, Modifier.weight(1f)) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = if(home) Color.White else Color.Black)
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "No tasks available",
                style = TextStyle(fontSize = 18.sp, color = if(home) Color.White else Color.Black, textAlign = TextAlign.Center)
            )
        }
    }
}