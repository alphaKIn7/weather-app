package com.example.weatherapp2.data

import com.example.weatherapp2.data.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "e2e64c27495e1d561d1300da77fde5f4"
const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val interceptor = Interceptor{
    chain->
    val request = chain.request()
    val url = request.url().newBuilder()
        .addQueryParameter("appid", API_KEY)
        .build()
    val newRequest = request.newBuilder().url(url).build()
    chain.proceed(newRequest)
}

private val httpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

private  val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(httpClient)
    .baseUrl(BASE_URL)
    .build()

interface OpenWeatherApiSerrvice {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") location : String
    ): Deferred<CurrentWeatherResponse>

}

object OpenWeatherApi {
    val retrofitSeervice : OpenWeatherApiSerrvice by lazy {
        retrofit.create(OpenWeatherApiSerrvice::class.java)
    }
}