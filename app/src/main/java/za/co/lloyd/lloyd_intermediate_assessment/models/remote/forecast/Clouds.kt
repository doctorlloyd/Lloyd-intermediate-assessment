package za.co.lloyd.lloyd_intermediate_assessment.models.remote.forecast

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Clouds(
    @Json(name = "all")val all: Int? = 0
): Parcelable