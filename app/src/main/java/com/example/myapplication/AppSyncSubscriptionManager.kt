package com.example.myapplication

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber
import javax.inject.Inject

class AppSyncSubscriptionManager @Inject constructor() {
    private val client = OkHttpClient()

    private var webSocket: WebSocket? = null

    fun connect() {

        val request = Request.Builder()
            .url(AppSyncConfig.realtimeEndpoint())
            .addHeader("Sec-WebSocket-Protocol", "graphql-ws")
            .build()

        webSocket = client.newWebSocket(
            request,
            object : WebSocketListener() {

                override fun onOpen(
                    webSocket: WebSocket,
                    response: Response,
                ) {
                    Timber.tag("AppSync")
                        .d("WebSocket connected")

                    sendConnectionInit(webSocket)
                }

                override fun onMessage(
                    webSocket: WebSocket,
                    text: String,
                ) {
                    Timber.tag("AppSync")
                        .d("Message: $text")

                    handleMessage(webSocket, text)
                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: Response?,
                ) {
                    Timber.tag("AppSync")
                        .e(t, "WebSocket failure")
                }

                override fun onClosed(
                    webSocket: WebSocket,
                    code: Int,
                    reason: String,
                ) {
                    Timber.tag("AppSync")
                        .d("WebSocket closed")
                }
            },
        )
    }

    private fun sendConnectionInit(webSocket: WebSocket) {
    }
    private fun startSubscription(webSocket: WebSocket) {

        val subscriptionQuery =
            "subscription { onFuelPriceUpdate { timestamp } }"

        val payload =
            """
            {
              "id":"1",
              "type":"start",
              "payload": {
                "data":"{\"query\":\"$subscriptionQuery\"}",
                "extensions": {
                  "authorization": {
                    "host":"${AppSyncConfig.host()}",
                    "x-api-key":"${AppSyncConfig.API_KEY}"
                  }
                }
              }
            }
            """.trimIndent()

        webSocket.send(payload)

        Timber.tag("AppSync")
            .d("Subscription started")
    }

    private fun handleMessage(
        webSocket: WebSocket,
        text: String,
    ) {

        when {

            text.contains("connection_ack") -> {
                Timber.tag("AppSync")
                    .d("Connection ACK")

                startSubscription(webSocket)
            }

            text.contains("start_ack") -> {
                Timber.tag("AppSync")
                    .d("Subscription acknowledged")
            }

            text.contains("data") -> {
                Timber.tag("AppSync")
                    .d("Realtime data received")
            }

            text.contains("ka") -> {
                Timber.tag("AppSync")
                    .d("Keep alive")
            }

            text.contains("error") -> {
                Timber.tag("AppSync")
                    .e("Subscription error: $text")
            }
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "Closed by user")
    }
}