package com.example.myapplication

import com.apollographql.apollo.ApolloClient
import timber.log.Timber
import javax.inject.Inject

class FuelQueryRepository @Inject constructor(
    private val apolloClient: ApolloClient
){

    suspend fun executeFuelQuery() {

        try {
            val response = apolloClient
                .query(ListFuelsQuery())
                .execute()

            Timber.tag("GraphQL")
                .d("Response: ${response.data}")

        } catch (e: Exception) {
            Timber.tag("GraphQL")
                .e(e, "Query failed")
        }
    }
}