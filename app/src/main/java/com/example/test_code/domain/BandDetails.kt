package com.example.test_code.domain

import java.net.URL

data class BandDetails(val id: Int,
                       val name: String,
                       val genre: String?,
                       val description: String?,
                       val foundation_year: Int?,
                       val picture: URL?,
                       val albums: List<Album>) {
}

