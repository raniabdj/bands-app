package com.example.test_code.presentation.entity
import com.example.test_code.domain.Album
import java.net.URL

data class AlbumViewEntity(val title: String, val date: String, val picture: URL?) {
    companion object {
        fun from(album: Album) =
            AlbumViewEntity(
                album.title,
                if (album.month == null) "${album.year}" else "${album.year} · ${album.month}",
                album.picture)
    }
}