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

    suspend fun getListOfTasks(status: Int): List<Task>? = coroutineScope.async{
        return@async taskDao.getListOfTasks(status = status)
    }.await()

    suspend fun deleteAllTasks(): Int = coroutineScope.async{
        return@async taskDao.deleteAllTasks()
    }.await()
}