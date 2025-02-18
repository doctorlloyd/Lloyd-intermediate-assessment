package za.co.lloyd.lloyd_intermediate_assessment.models.remote.weekforecasts

import android.os.Parcelable
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.forecast.Weather
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import za.co.lloyd.lloyd_intermediate_assessment.models.remote.weekforecasts.City

@Parcelize
data class Forecast(
    @Json(name = "city")val city: City? = City(),
    @Json(name = "cnt")val cnt: Int? = 0,
    @Json(name = "cod")val cod: String? = null,
    @Json(name = "list")val list: List<Weather>? = ArrayList(),
    @Json(name = "message")val message: Int? = 0
) : Parcelable