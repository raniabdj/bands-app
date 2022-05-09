package com.example.test_code.domain

import java.net.URL
import java.time.Month

data class Album(val title: String,
                 val year: Int,
                 val month: Month?,
                 val lineUp: String?,
                 val picture: URL?)