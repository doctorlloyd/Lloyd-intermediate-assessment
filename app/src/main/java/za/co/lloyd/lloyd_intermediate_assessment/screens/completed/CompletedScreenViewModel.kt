package za.co.lloyd.lloyd_intermediate_assessment.screens.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import za.co.lloyd.lloyd_intermediate_assessment.models.local.Task
import za.co.lloyd.lloyd_intermediate_assessment.repository.local.TaskRepository
import javax.inject.Inject

@HiltViewModel
class CompletedScreenViewModel@Inject constructor(private val repository: TaskRepository): ViewModel() {

    suspend fun getListOfTasks(status: Int): List<Task>? {
        val deferred: Deferred<List<Task>?> = viewModelScope.async {
            repository.getListOfTasks(status = status)
        }
        return deferred.await()
    }

    suspend fun deleteTask(id: Int): Int {
        val deferred: Deferred<Int> = viewModelScope.async {
            repository.deleteTask(id = id)
        }
        return deferred.await()
    }

    suspend fun insertTask(task: Task): Long {
        val deferred: Deferred<Long> = viewModelScope.async {
            repository.insertTask(task = task)
        }
        return deferred.await()
    }

    suspend fun updateTask(updatedTask: Task): Int {
        val deferred: Deferred<Int> = viewModelScope.async {
            repository.updateTask(id = updatedTask.recordId, taskStatus = updatedTask.taskStatus!!, title = updatedTask.title, description = updatedTask.description)
        }
        return deferred.await()
    }
}