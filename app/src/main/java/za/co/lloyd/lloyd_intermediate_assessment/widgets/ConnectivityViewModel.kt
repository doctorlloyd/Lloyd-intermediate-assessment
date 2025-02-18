package za.co.lloyd.lloyd_intermediate_assessment.widgets

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.lloyd.lloyd_intermediate_assessment.utils.networkConnection.ConnectivityManager
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel@Inject constructor(private val connectivityManager: ConnectivityManager) : ViewModel() {
    fun appConnectivityStatus(): Boolean { return connectivityManager.isNetworkAvailable.value }
}