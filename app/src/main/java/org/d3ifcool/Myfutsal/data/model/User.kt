package org.d3ifcool.Myfutsal.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val noWhatsapp : String = "",
    val profileCompleted: Int = 0
) : Parcelable