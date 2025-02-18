package za.co.lloyd.lloyd_intermediate_assessment.models.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_tbl")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recordId") val recordId: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "dt_txt") val dt_txt: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "taskStatus") val taskStatus: Boolean? = false,
    @ColumnInfo(name = "deliveryStatus") val deliveryStatus: Boolean? = false,
): Parcelable