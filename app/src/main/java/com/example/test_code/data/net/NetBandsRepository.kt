package com.example.test_code.data.net
import com.example.test_code.application.repository.BandRepository
import com.example.test_code.domain.Band
import com.example.test_code.domain.BandDetails
import io.reactivex.Single
import javax.inject.Inject

open class NetBandsRepository
@Inject constructor(private val bandsApiService: BandsApiService)
    : BandRepository {
    override fun bands(): Single<List<Band>> =
        bandsApiService.bands().map { it.map { e -> e.toBand() } }

    override fun bandDetails(bandId: Int): Single<BandDetails> =
        bandsApiService.bandDetails(bandId).map { it.toBandDetails() }
}