package za.co.lloyd.lloyd_intermediate_assessment.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.List
import za.co.lloyd.lloyd_intermediate_assessment.models.navigation.BottomNavigationItem
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val FAHRENHEIT: Double = 273.15
    val dtFormat  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)

    val navScreens = listOf(
        BottomNavigationItem(title = "To Do List", selectedIcon = Icons.Filled.List, unselectedIcon = Icons.Outlined.List, hasNews = false),
        BottomNavigationItem(title = "Completed", selectedIcon = Icons.Filled.Edit, unselectedIcon = Icons.Outlined.Edit, hasNews = false))
}