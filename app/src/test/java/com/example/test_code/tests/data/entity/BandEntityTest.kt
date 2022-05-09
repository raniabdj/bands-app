package com.example.test_code.tests.data.entity


import org.junit.Test
import java.net.MalformedURLException

class BandEntityTest {
    companion object {
        private const val ID = 1
        private const val NAME = "name"
        private const val OK_URL = "http://url.com"
        private const val KO_URL = "this is not an URL"
    }

    @Test
    fun `all fields`() {
        BandEntity(ID, NAME, OK_URL).toBand()
    }

    @Test
    fun `missing optional fields`() {
        BandEntity(ID, NAME, null).toBand()
    }

    @Test( expected = MalformedURLException::class)
    fun `wrong url`() {
        BandEntity(ID, NAME, KO_URL).toBand()
    }
}