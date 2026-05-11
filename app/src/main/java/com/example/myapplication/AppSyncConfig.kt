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
        return "wss://ifqek373sfgdjndz2qpvdo63cu.appsync-realtime-api.us-east-1.amazonaws.com/graphql?header=$encodedHeader&payload=$encodedPayload"

       // return "wss://$realtimeHost/graphql?header=$encodedHeader&payload=$encodedPayload"
    }
}
/*object AppSyncConfig {
    const val HTTP_ENDPOINT =
        "https://eredu7mfdbfivlxi6uqjqyiui4.appsync-api.us-east-1.amazonaws.com/graphql"

    const val API_KEY = "YOUR_API_KEY"

    val HOST: String
        get() = HTTP_ENDPOINT.toHttpUrl().host
}
suspend fun executeGraphQLQuery() {
    val client = OkHttpClient()

    val jsonBody = """
        {
          "query": "query ListFuels { getFuels { fuels { id name } } }"
        }
    """.trimIndent()

    val request = Request.Builder()
        .url(AppSyncConfig.HTTP_ENDPOINT)
        .addHeader("Content-Type", "application/json")
        .addHeader("x-api-key", AppSyncConfig.API_KEY)
        .post(jsonBody.toRequestBody("application/json".toMediaType()))
        .build()

    val response = client.newCall(request).execute()

    println("HTTP Status: ${response.code}")
    println("Response: ${response.body?.string()}")
}

fun buildRealtimeUrl(): String {
    val host = AppSyncConfig.HOST
    val realtimeHost = host.replace("appsync-api", "appsync-realtime-api")

    val headerJson = """
        {
          "host": "$host",
          "x-api-key": "${AppSyncConfig.API_KEY}"
        }
    """.trimIndent()

    val encodedHeader = Base64.encodeToString(headerJson.toByteArray(), Base64.NO_WRAP)
    val encodedPayload = Base64.encodeToString("{}".toByteArray(), Base64.NO_WRAP)

    return "wss://$realtimeHost/graphql?header=$encodedHeader&payload=$encodedPayload"
}*/