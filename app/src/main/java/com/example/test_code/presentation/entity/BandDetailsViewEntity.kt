package com.example.test_code.presentation.entity
import com.example.test_code.domain.Album
import com.example.test_code.domain.BandDetails
import java.net.URL
data class BandDetailsViewEntity(
    val name: String,
    val genre: String?,
    val description: String?,
    val foundationYear: String,
    val picture: URL?,
    val albums: List<AlbumViewEntity>) {

    companion object {
        fun from(bandDetails: BandDetails) = BandDetailsViewEntity(bandDetails.name,
            bandDetails.genre, bandDetails.description, bandDetails.foundation_year.toString(),
            bandDetails.picture, bandDetails.albums.map { AlbumViewEntity.from(it) })
    }
}