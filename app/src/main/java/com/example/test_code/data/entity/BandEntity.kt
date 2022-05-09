package com.example.test_code.data.entity

import com.example.test_code.domain.Band
import java.net.URL
data class BandEntity  constructor(
    val id: Int,
    val name: String,
    val picture: String?) {

    fun toBand() = Band(id, name, if (picture == null) null else URL(picture))
}