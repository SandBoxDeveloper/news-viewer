package andre.hitchman.com.news.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import javax.inject.Inject

class NetworkChecker @Inject constructor(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var onNetworkChangedEvent: NetworkChangedEvent? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            onNetworkChangedEvent?.onNetworkLost()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNetworkCallback() {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun unregisterNetworkCallback() {
        try {
            onNetworkChangedEvent = null
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (exception: Exception) {
            Log.e("NetworkChecker", "un registering network callback failed")
        }
    }

    fun setupEvent(changedEvent: NetworkChangedEvent?) {
        onNetworkChangedEvent = changedEvent
    }

    fun isNetworkAvailable(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}