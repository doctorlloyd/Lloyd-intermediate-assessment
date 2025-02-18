package za.co.lloyd.lloyd_intermediate_assessment.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.forecast.Weather
import za.co.lloyd.lloyd_intermediate_assessment.repository.remote.ToDoAppRepository
import za.co.lloyd.lloyd_intermediate_assessment.utils.network.DataState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(private val repository: ToDoAppRepository): ViewModel() {
    private val weatherDataState: MutableSharedFlow<DataState<Weather>> = MutableSharedFlow(replay = 1)
    var weatherResponse : Flow<DataState<Weather>> = weatherDataState

    suspend fun getTodayWeather(lat: String, lon: String, key: String){
        viewModelScope.launch {
            repository.getTodayWeather(lat = lat, lon = lon, key = key).collectLatest {
                weatherDataState.tryEmit(it)
            }
        }
    }
}