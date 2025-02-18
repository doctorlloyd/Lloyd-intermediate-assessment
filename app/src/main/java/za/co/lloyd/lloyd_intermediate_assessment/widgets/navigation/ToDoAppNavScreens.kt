package za.co.lloyd.lloyd_intermediate_assessment.widgets.navigation

sealed class ToDoAppNavScreens(val name: String) {
    object ToDoScreen: ToDoAppNavScreens("to_do_screen")
    object CompletedScreen: ToDoAppNavScreens("completed_screen")
}