package com.example.test_code.tests.common


import java.io.File

/*
 * Auxiliary function to read files from test resources
 */

private val instance = Dummy()
class Dummy

fun readFromResources(path : String) : String {
    val uri = instance.javaClass.classLoader!!.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}