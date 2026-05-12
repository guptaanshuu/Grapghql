package com.example.myapplication

object AppSyncConfig {

    const val HTTP_ENDPOINT =
        "https://ifqek373sfgdjndz2qpvdo63cu.appsync-api.us-east-1.amazonaws.com/graphql"

    const val API_KEY =
        "da2-r4thp3qgzbd4dm55uwrve7lwci"

    fun host(): String {
        return HTTP_ENDPOINT
            .replace("https://", "")
            .replace("/graphql", "")
    }

    fun realtimeEndpoint(): String {
        val realtimeHost = host()
            .replace("appsync-api", "appsync-realtime-api")

        val headerJson =
            """
            {
              "host":"${host()}",
              "x-api-key":"$API_KEY"
            }
            """.trimIndent()

        val encodedHeader = android.util.Base64.encodeToString(
            headerJson.toByteArray(),
            android.util.Base64.NO_WRAP,
        )

        val encodedPayload = android.util.Base64.encodeToString(
            "{}".toByteArray(),
            android.util.Base64.NO_WRAP,
        )
        return "wss://$realtimeHost/graphql?header=$encodedHeader&payload=$encodedPayload"
    }
}
