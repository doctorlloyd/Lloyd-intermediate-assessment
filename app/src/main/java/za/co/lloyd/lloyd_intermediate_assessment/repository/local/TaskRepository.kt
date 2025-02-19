package za.co.lloyd.lloyd_intermediate_assessment.repository.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import za.co.lloyd.lloyd_intermediate_assessment.data.local.TaskDao
import za.co.lloyd.lloyd_intermediate_assessment.models.local.Task
import javax.inject.Inject

class TaskRepository@Inject constructor(private val taskDao: TaskDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun insertTask(task: Task): Long = coroutineScope.async{
        return@async taskDao.insertTask(task = task)
    }.await()
    suspend fun updateTask(id: Int, taskStatus: Boolean, title: String, description: String): Int = coroutineScope.async{
        return@async taskDao.updateTask(id = id, taskStatus = taskStatus, title = title, description= description)
    }.await()

    suspend fun getListOfTasks(status: Int): List<Task>? = coroutineScope.async{
        return@async taskDao.getListOfTasks(status = status)
    }.await()
    suspend fun searchTasks(query: String): List<Task>? = coroutineScope.async{
        return@async taskDao.searchTasks(query = query)
    }.await()

    suspend fun deleteAllTasks(): Int = coroutineScope.async{
        return@async taskDao.deleteAllTasks()
    }.await()
    suspend fun deleteTask(id: Int): Int = coroutineScope.async{
        return@async taskDao.deleteTask(id = id)
    }.await()
}