package andre.hitchman.com.news.network

import java.util.*

interface NetworkChangedEvent : EventListener {
    fun onNetworkLost()
}