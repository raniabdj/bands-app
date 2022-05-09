package com.example.test_code.data.entity
import com.example.test_code.domain.Album
import java.net.URL
import java.time.Month

data class AlbumEntity constructor(
    val title: String,
    val year: Int,
    val month: Int?,
    val lineUp: String?,
    val picture: String?) {

    fun toAlbum(): Album =
        Album(title, year,
            if (month == null) null else Month.of(month),
            lineUp,
            if (picture == null) null else URL(picture))
}