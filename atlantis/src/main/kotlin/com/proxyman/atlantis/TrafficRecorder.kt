package com.proxyman.atlantis

import okhttp3.Request as OkHttpRequest
import okhttp3.Response as OkHttpResponse

/**
 * Destination of the traffic captured by [AtlantisInterceptor]
 */
internal interface TrafficRecorder {
    fun sendPackage(trafficPackage: TrafficPackage)
    fun onStreamOpen(id: String, request: OkHttpRequest, response: OkHttpResponse)
    fun onStreamEvent(id: String, text: String)
    fun onStreamClose(id: String, code: Int, reason: String?)
}

internal object AtlantisRecorder : TrafficRecorder {
    override fun sendPackage(trafficPackage: TrafficPackage) = Atlantis.sendPackage(trafficPackage)

    override fun onStreamOpen(id: String, request: OkHttpRequest, response: OkHttpResponse) =
        Atlantis.onWebSocketOpen(id = id, request = request, response = response)

    override fun onStreamEvent(id: String, text: String) = Atlantis.onWebSocketReceiveText(id = id, text = text)

    override fun onStreamClose(id: String, code: Int, reason: String?) =
        Atlantis.onWebSocketClosing(id = id, code = code, reason = reason)
}
