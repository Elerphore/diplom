package data

import java.util.*

data class Student (
    val persKod: Int,
    val kodAgr: Int,
    val Sex: String?,
    val osnova: String,
    val codeSpec: String,
    val nameSpec: String,
    val isActive: Boolean,
    val datePrikazFirst: Date,
    val dateEndOb: Date,
    val age: Int,
    val depart: String,
    val nameGr: String,
    val nameFormO: String,
)