package com.example.test_code.infrastructure.di
import android.annotation.SuppressLint
import android.content.Context
import com.example.test_code.BuildConfig
import com.example.test_code.application.repository.BandRepository
import com.example.test_code.data.net.BandsApiService
import com.example.test_code.data.net.NetBandsRepository
import com.example.test_code.infrastructure.App
import com.example.test_code.infrastructure.platform.SchedulersProvider
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.*
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class ApplicationModule(private val application: App) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/cmauri/assets-for-examples/main/clean-code/")
            .client(if (BuildConfig.DEBUG) createUnsafeClient() else createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    private fun createUnsafeClient(): OkHttpClient {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>, authType: String) = Unit
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>, authType: String) = Unit
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
        builder.connectTimeout(5, TimeUnit.SECONDS)
        builder.readTimeout(5, TimeUnit.SECONDS)
        builder.writeTimeout(5, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides @Singleton fun provideSchedulerProvider(): SchedulersProvider {
        class JobThreadFactory : ThreadFactory {
            private var counter = 0
            override fun newThread(runnable: Runnable): Thread {
                return Thread(runnable, "android_" + counter++)
            }
        }

        return (object : SchedulersProvider {
            override fun getIOScheduler(): Scheduler {
                return Schedulers.from(object: Executor {
                    private val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
                        3, 5, 10, TimeUnit.SECONDS,
                        LinkedBlockingQueue(), JobThreadFactory()
                    )

                    override fun execute(runnable: Runnable) {
                        threadPoolExecutor.execute(runnable)
                    }
                })
            }

            override fun getUIScheduler(): Scheduler = AndroidSchedulers.mainThread()
        })
    }

    @Provides fun provideBandRepository(bandsApiService: BandsApiService): BandRepository =
        NetBandsRepository(bandsApiService)

}