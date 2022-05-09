package com.example.test_code.application.interactor

import com.example.test_code.application.repository.BandRepository
import com.example.test_code.domain.BandDetails
import com.example.test_code.infrastructure.platform.SchedulersProvider

import javax.inject.Inject
import io.reactivex.Single

class GetBandDetails
@Inject
constructor(private val bandRepository: BandRepository,
            schedulersProvider: SchedulersProvider)
    : UseCaseSingle<BandDetails, Int>(schedulersProvider) {

    override fun buildUseCaseSingle(params: Int): Single<BandDetails> =
        bandRepository.bandDetails(params)}