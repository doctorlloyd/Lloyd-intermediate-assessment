package za.co.lloyd.lloyd_intermediate_assessment.data.local

import androidx.room.Database
import androidx.room.TypeConverters
import za.co.lloyd.lloyd_intermediate_assessment.models.local.Task
import za.co.lloyd.lloyd_intermediate_assessment.utils.databaseconverter.UUIDConverter
import za.co.lloyd.lloyd_intermediate_assessment.utils.databaseconverter.DateConverter

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class  TaskDatabaseRoomDatabase() {
    abstract fun weatherDatabase(): TaskDao
}