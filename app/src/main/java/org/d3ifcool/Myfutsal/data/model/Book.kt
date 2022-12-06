package org.d3ifcool.Myfutsal.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val userId: String = "",
    val userName: String = "",
    val dateRental: String = "",
    val hoursRental: String = "",
    val groundRental: String = "",
    val status: String = "",
    var bookId: String = ""
) : Parcelable