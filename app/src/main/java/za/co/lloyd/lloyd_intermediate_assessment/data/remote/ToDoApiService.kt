package za.co.lloyd.lloyd_intermediate_assessment.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.forecast.Weather
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.weekforecasts.Forecast

interface ToDoApiService {
    @GET("/data/2.5/weather")
    suspend fun getTodayWeather(@Query("lat") lat: String,
                                @Query("lon") lon: String,
                                @Query("appid") key: String): Weather

    @GET("/data/2.5/weather")
    suspend fun getTodayWeatherByCity(@Query("q") city: String,
                                      @Query("appid") key: String): Weather

    @GET("/data/2.5/forecast")
    suspend fun getWeeklyWeather(@Query("lat") lat: String,
                                 @Query("lon") lon: String,
                                 @Query("appid") key: String): Forecast
}