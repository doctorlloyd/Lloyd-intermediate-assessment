package za.co.lloyd.lloyd_intermediate_assessment.utils.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}