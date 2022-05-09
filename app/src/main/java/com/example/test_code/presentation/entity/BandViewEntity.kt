package com.example.test_code.presentation.entity

import android.os.Parcelable
import com.example.test_code.domain.Band
import kotlinx.android.parcel.Parcelize
@Parcelize
data class BandViewEntity(val id: Int, val name: String, val picture: String) : Parcelable {
    companion object {
        fun from(band: Band): BandViewEntity =
            BandViewEntity(band.id, band.name, band.picture.toString())
    }
}