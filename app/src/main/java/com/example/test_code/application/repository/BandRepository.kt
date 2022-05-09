package com.example.test_code.application.repository
import com.example.test_code.domain.Band
import com.example.test_code.domain.BandDetails
import io.reactivex.Single

interface  BandRepository {
    fun bands(): Single<List<Band>>
    fun bandDetails(bandId: Int): Single<BandDetails>
}