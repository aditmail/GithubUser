package com.demo.task.submissiontwodicoding.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.demo.task.submissiontwodicoding.R
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitServices(context: Context) {

    private val _context: Context = context

    /** Define Moshi Object for JSON Parsing Format **/
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun setInterceptor(): OkHttpClient {
        //Interceptor
        /*val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS*/

        /** Adding Header Authorization for API
         * It's Right? or Need Some Improvement in Here..?
         * **/
        val builder = OkHttpClient.Builder()
        builder.addInterceptor {
            val newRequest = it.request().newBuilder()
                .addHeader(_context.getString(R.string.auth), _context.getString(R.string.api_key))
                .build()
            it.proceed(newRequest)
        }
        return builder.build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(setInterceptor())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) /** Supporting Coroutine in API Call **/
            .build()
    }

    /** Get Class For List of API **/
    fun getServiceAPI(): GithubAPIService = getRetrofit().create(GithubAPIService::class.java)
}

/** Set/Checking Networks Availability **/
fun isNetworkAvailable(context: Context): Boolean {
    var result = false
    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connMgr.activeNetwork ?: return false
        val activeNetwork = connMgr.getNetworkCapabilities(networkCapabilities) ?: return false

        result = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        connMgr.run {
            connMgr.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }

    return result
}


