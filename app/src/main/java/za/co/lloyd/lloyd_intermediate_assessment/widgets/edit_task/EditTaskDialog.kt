package za.co.lloyd.lloyd_intermediate_assessment.widgets.edit_task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.lloyd.lloyd_intermediate_assessment.models.local.Task
import za.co.lloyd.lloyd_intermediate_assessment.utils.Constants.dtFormat
import java.util.Date

@Composable
fun EditTaskDialog(
    task: Task, // ✅ Existing task to edit
    onDismiss: () -> Unit,
    onTaskUpdated: (Task) -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    var isCompleted by remember { mutableStateOf(task.taskStatus ?: false) } // ✅ Preserve completion status

    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        title = {
            Text(
                text = "Edit Task",
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSurface
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title", color = MaterialTheme.colors.onSurface) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onSurface,
                        cursorColor = MaterialTheme.colors.onSurface
                    )
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description", color = MaterialTheme.colors.onSurface) },
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onSurface,
                        cursorColor = MaterialTheme.colors.onSurface
                    )
                )

                // ✅ Checkbox for task completion
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isCompleted,
                        onCheckedChange = { isCompleted = it },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Mark as Complete", color = MaterialTheme.colors.onSurface)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank()) {
                        val updatedTask = task.copy( // ✅ Keep task ID & update fields
                            title = title,
                            description = description,
                            taskStatus = isCompleted,
                            dt_txt = dtFormat.format(Date()) // Optional: Update timestamp
                        )
                        onTaskUpdated(updatedTask)
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text("Save Changes", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary)
            ) {
                Text("Cancel", color = MaterialTheme.colors.primary)
            }
        }
    )
}