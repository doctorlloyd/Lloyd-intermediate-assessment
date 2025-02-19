package za.co.lloyd.lloyd_intermediate_assessment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import za.co.lloyd.lloyd_intermediate_assessment.models.local.Task

@Dao
interface  TaskDao{
    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertTask(task: Task): Long

    @Query("SELECT * FROM task_tbl WHERE taskStatus = :status ORDER BY dt_txt DESC")
    fun getListOfTasks(status: Int): List<Task>? // 0 = pending, 1 = completed

    @Query("SELECT * FROM task_tbl WHERE title LIKE '%' || :query || '%' ORDER BY dt_txt DESC")
    fun searchTasks(query: String): List<Task>?

    @Query("DELETE FROM task_tbl")
    fun deleteAllTasks(): Int

    @Query("UPDATE task_tbl SET taskStatus = :taskStatus, title = :title, description = :description WHERE recordId = :id")
    fun updateTask(id: Int, taskStatus: Boolean, title: String, description: String): Int

    @Query("DELETE FROM task_tbl WHERE recordId = :id")
    fun deleteTask(id: Int): Int
}