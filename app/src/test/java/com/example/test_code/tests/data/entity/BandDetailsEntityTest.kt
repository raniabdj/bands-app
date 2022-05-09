package com.example.test_code.tests.data.entity


import org.junit.Test
import java.net.MalformedURLException

class BandDetailsEntityTest {
    companion object {
        private const val ID = 1
        private const val NAME = "name"
        private const val GENRE = "genre"
        private const val DESCRIPTION = "description"
        private const val YEAR = 2020
        private const val OK_URL = "http://url.com"
        private const val KO_URL = "this is not an URL"
        private val ALBUMS = listOf(
            AlbumEntity("title", YEAR, 1, "lineup", OK_URL))
    }

    @Test
    fun `all fields`() {
        BandDetailsEntity(ID, NAME, GENRE, DESCRIPTION, YEAR, OK_URL, ALBUMS).toBandDetails()
    }

    @Test
    fun `missing optional fields`() {
        BandDetailsEntity(ID, NAME, null, null, YEAR, null,
            listOf()).toBandDetails()
    }

    @Test( expected = MalformedURLException::class)
    fun `wrong url`() {
        BandDetailsEntity(ID, NAME, GENRE, DESCRIPTION, YEAR, KO_URL, ALBUMS).toBandDetails()
    }
}