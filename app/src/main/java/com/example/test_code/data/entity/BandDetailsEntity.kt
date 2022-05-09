package com.example.test_code.data.entity
import com.example.test_code.domain.BandDetails

import java.net.URL
data class BandDetailsEntity  constructor(
    val id: Int,
    val name: String,
    val genre: String?,
    val description: String?,
    val year: Int?,
    val picture: String?,
    val albums: List<AlbumEntity>) {
    fun toBandDetails(): BandDetails =
        BandDetails(id, name, genre, description, year,
            if (picture == null) null else URL(picture), albums.map { it.toAlbum() })
}