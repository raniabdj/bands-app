package com.example.test_code.data.net
import com.example.test_code.application.exception.ServerErrorException
import com.example.test_code.data.entity.BandEntity
import com.example.test_code.data.entity.BandDetailsEntity

import io.reactivex.Single
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BandsApiService
@Inject constructor(retrofit: Retrofit) : BandsApi {
    private val musicApi by lazy { retrofit.create(BandsApi::class.java) }
    override fun bands(): Single<List<BandEntity>> =
        musicApi
            .bands()
            .onErrorResumeNext { error: Throwable -> Single.error(translate(error)) }
    override fun bandDetails(bandId: Int): Single<BandDetailsEntity> =
        musicApi
            .bandDetails(bandId)

            .onErrorResumeNext { error: Throwable -> Single.error(translate(error)) }
    private fun translate(throwable: Throwable): Throwable =
        when(throwable) {
            is HttpException -> ServerErrorException(throwable)
            else -> throwable
        }}