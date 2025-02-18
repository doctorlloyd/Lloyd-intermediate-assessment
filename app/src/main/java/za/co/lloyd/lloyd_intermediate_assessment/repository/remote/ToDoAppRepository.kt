package za.co.lloyd.lloyd_intermediate_assessment.repository.remote


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import za.co.lloyd.lloyd_intermediate_assessment.data.remote.ToDoApiService
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.forecast.Weather
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.weekforecasts.Forecast
import za.co.lloyd.lloyd_intermediate_assessment.utils.network.DataState
import javax.inject.Inject

class ToDoAppRepository@Inject constructor(private val toDoApiService: ToDoApiService) {
    suspend fun getTodayWeather(lat: String, lon: String, key: String): Flow<DataState<Weather>> = flow {
        emit(DataState.Loading)
        try {
            val forecast = toDoApiService.getTodayWeather(lat = lat, lon = lon, key = key)
            emit(DataState.Success(data = forecast))
        } catch (e: Exception) {
            emit(DataState.Error(exception = e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTodayWeatherByCity(city: String, key: String): Flow<DataState<Weather>> = flow {
        emit(DataState.Loading)
        try {
            val forecast = toDoApiService.getTodayWeatherByCity(city = city, key = key)
            emit(DataState.Success(data = forecast))
        } catch (e: Exception) {
            emit(DataState.Error(exception = e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getWeeklyWeather(lat: String, lon: String, key: String): Flow<DataState<Forecast>> = flow {
        emit(DataState.Loading)
        try {
            val forecasts = toDoApiService.getWeeklyWeather(lat = lat, lon = lon, key = key)
            emit(DataState.Success(data = forecasts))
        } catch (e: Exception) {
            emit(DataState.Error(exception = e))
        }
    }.flowOn(Dispatchers.IO)
}