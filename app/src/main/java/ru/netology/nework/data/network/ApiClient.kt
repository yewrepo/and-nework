package ru.netology.nework.data.network

import android.content.Context
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.netology.nework.BuildConfig
import ru.netology.nework.data.local.token.TokenDataSourceImpl
import java.time.LocalDateTime

const val TEST_BASE_URL = "https://netomedia.ru/"

class ApiClient(c: Context) {

    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocaleDateTimeDeserializer())
        .create()

    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(TEST_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(
            getRetrofitClient(
                NeWorkApiInterceptor(
                    buildTokenApi(),
                    TokenDataSourceImpl(c)
                )
            )
        )
        .build()

    private fun getRetrofitClient(apiInterceptor: Interceptor? = null): OkHttpClient {
        return OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .retryOnConnectionFailure(true)
            .also { client ->
                if (apiInterceptor != null) {
                    client.addInterceptor(apiInterceptor)
                }
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }

    private fun buildTokenApi(): TokenApi {

        return Retrofit.Builder()
            .baseUrl(TEST_BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(TokenApi::class.java)
    }

    fun neWorkApi(): NeWorkApi = retrofit.create(NeWorkApi::class.java)

    fun tokenApi(): TokenApi = buildTokenApi()

}