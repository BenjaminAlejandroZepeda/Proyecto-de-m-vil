package com.example.motorsportapp.data.remote

import com.example.motorsportapp.data.local.PrefDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.flow.firstOrNull


class AuthInterceptor(private val prefDataStore: PrefDataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()


        val token = runBlocking { prefDataStore.getToken.firstOrNull() }

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
