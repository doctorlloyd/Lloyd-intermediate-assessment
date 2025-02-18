package za.co.lloyd.lloyd_intermediate_assessment.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import za.co.lloyd.lloyd_intermediate_assessment.widgets.loader.Loader
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import za.co.lloyd.lloyd_intermediate_assessment.R
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.forecast.Weather
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.weekforecasts.Forecast
import za.co.lloyd.lloyd_intermediate_assessment.screens.DeviceViewModel
import za.co.lloyd.lloyd_intermediate_assessment.utils.Constants
import za.co.lloyd.lloyd_intermediate_assessment.utils.backgroundColorChanger
import za.co.lloyd.lloyd_intermediate_assessment.utils.convertTemperature
import za.co.lloyd.lloyd_intermediate_assessment.utils.getWeatherIcon
import za.co.lloyd.lloyd_intermediate_assessment.utils.getWeekDays
import za.co.lloyd.lloyd_intermediate_assessment.utils.isBetween11AM2PM
import za.co.lloyd.lloyd_intermediate_assessment.utils.network.DataState
import za.co.lloyd.lloyd_intermediate_assessment.utils.theme.Cloudy
import za.co.lloyd.lloyd_intermediate_assessment.utils.theme.Rainy
import za.co.lloyd.lloyd_intermediate_assessment.utils.theme.Sunny
import za.co.lloyd.lloyd_intermediate_assessment.widgets.ConnectivityViewModel
import za.co.lloyd.lloyd_intermediate_assessment.widgets.navigation.ToDoAppNavScreens

@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    // Used for side effects on submit button
    val viewModelJob = Job()
    val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Weather view model
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val connectivityViewModel = hiltViewModel<ConnectivityViewModel>()
    val deviceViewModel = hiltViewModel<DeviceViewModel>()
    deviceViewModel.getCurrentLocation()

    val progressBar = remember { mutableStateOf(false) }
    val weather = remember { mutableStateOf(Weather())}

//    val weeklyWeather = remember { mutableStateOf(Forecast())}

    if(!weather.value.name.isNullOrEmpty()) WeatherWidget(navController = navController, weather = weather)
    else scope.launch {
        if(connectivityViewModel.appConnectivityStatus() && deviceViewModel.currentLocation?.latitude != null){
            withContext(Dispatchers.IO) {
                homeViewModel.getTodayWeather(lat = deviceViewModel.currentLocation?.latitude.toString(), lon = deviceViewModel.currentLocation?.longitude.toString(), key = context.resources.getString(R.string.api_key))
                withContext(Dispatchers.Main) {
                    homeViewModel.weatherResponse.collectLatest {
                        when(it){
                            is DataState.Success->{
                                weather.value = it.data
                                progressBar.value = false
//                                homeViewModel.forecastsResponse.collectLatest { week ->
//                                    when(week) {
//                                        is DataState.Success -> { progressBar.value = false
//                                            weeklyWeather.value = week.data }
//                                        is DataState.Loading -> {}
//                                        is DataState.Error -> progressBar.value = false
//                                    }
//                                }
                            }
                            is DataState.Loading -> progressBar.value = true
                            is DataState.Error -> progressBar.value = false
                        }
                    }
                }
            }
        }
    }
    if(progressBar.value) Loader()
}

@Composable
fun WeatherWidget(navController: NavController, weather: MutableState<Weather>){
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
        Scaffold(
            bottomBar = {
                NavigationBar(modifier = Modifier.height(65.dp), containerColor = if(backgroundColorChanger(weather.value.weather?.get(0)?.icon!!).contains(stringResource(id = R.string.clear), ignoreCase = true)) Sunny
                else if(backgroundColorChanger(weather.value.weather?.get(0)?.icon!!).contains(stringResource(id = R.string.rainy), ignoreCase = true)) Rainy
                else Cloudy, contentColor = MaterialTheme.colors.secondary, tonalElevation = 2.dp) {
                    Constants.navScreens.forEachIndexed { index, item ->
                        NavigationBarItem(selected = selectedItemIndex == index,
                            onClick = { selectedItemIndex = index
                                when (index) {
                                    0 -> {}
                                    1 -> navController.navigate(ToDoAppNavScreens.ToDoScreen.name){ launchSingleTop = true }
                                }
                            },
                            label = { androidx.compose.material.Text(text = item.title, style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.secondary, fontWeight = if (index == selectedItemIndex) FontWeight.ExtraBold else FontWeight.Bold)) },
                            alwaysShowLabel = true, icon = { Icon(tint = if (index == selectedItemIndex) Color.White else MaterialTheme.colors.secondary, imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon, contentDescription = item.title) },
                            colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colors.secondary)
                        )
                    }
                }
            }){ padding ->
            Box(modifier = Modifier.fillMaxSize().padding(0.dp)){
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = with (Modifier){ fillMaxSize().weight(0.65f).paint(painterResource(id =
                    if(backgroundColorChanger(weather.value.weather?.get(0)?.icon!!).contains(stringResource(id = R.string.clear), ignoreCase = true)) R.drawable.forest_sunny
                    else if(backgroundColorChanger(weather.value.weather?.get(0)?.icon!!).contains(stringResource(id = R.string.rainy), ignoreCase = true)) R.drawable.forest_rainy
                    else R.drawable.forest_cloudy
                    ), contentScale = ContentScale.FillBounds) }){
                        Column(modifier = Modifier.align(Alignment.Center),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "${"%.0f".format(convertTemperature(weather.value.main?.temp!!))} \u00B0C", style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White))
                            Image(painter = painterResource(id = getWeatherIcon(weather.value.weather?.get(0)?.icon!!)), contentDescription = null)
                            Text(text = weather.value.weather?.get(0)?.description!!, style = TextStyle(fontSize = 22.sp, color = Color.White))
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth().weight(0.2f).background(color =
                    if(backgroundColorChanger(weather.value.weather?.get(0)?.icon!!).contains(stringResource(id = R.string.clear), ignoreCase = true)) Sunny
                    else if(backgroundColorChanger(weather.value.weather?.get(0)?.icon!!).contains(stringResource(id = R.string.rainy), ignoreCase = true)) Rainy
                    else Cloudy), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "${"%.0f".format(convertTemperature(weather.value.main!!.temp_min!!))} \u00B0C", color = Color.White)
                            Text(text = "min", color = Color.White)
                        }
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "${"%.0f".format(convertTemperature(weather.value.main?.temp!!))} \u00B0C", color = Color.White)
                            Text(text = "Current", color = Color.White)
                        }
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "${"%.0f".format(convertTemperature(weather.value.main?.temp_max!!))} \u00B0C", color = Color.White)
                            Text(text = "max", color = Color.White)
                        }
                    }
                    Divider(color = Color.White, thickness = 2.dp)
                    Column(modifier = Modifier.fillMaxWidth().weight(1.35f).background(color =
                    if(backgroundColorChanger(weather.value.weather?.get(0)?.icon!!).contains(stringResource(id = R.string.clear), ignoreCase = true)) Sunny
                    else if(backgroundColorChanger(weather.value.weather?.get(0)?.icon!!).contains(stringResource(id = R.string.rainy), ignoreCase = true)) Rainy
                    else Cloudy).padding(top = 14.dp, bottom = 60.dp)){

                    }
                }
            }
        }
}