package com.example.test_code.data.net
import com.example.test_code.data.entity.BandEntity
import com.example.test_code.data.entity.BandDetailsEntity
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
internal interface BandsApi {

    companion object {
        private const val PARAM_BAND_ID = "bandId"
    }
    @GET("bands.json") fun bands(): Single<List<BandEntity>>
    @GET("band_{$PARAM_BAND_ID}.json")
    fun bandDetails(@Path(PARAM_BAND_ID) bandId: Int): Single<BandDetailsEntity>
}