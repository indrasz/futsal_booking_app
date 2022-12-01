package org.d3ifcool.Myfutsal.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gallery(
    var image: Int = 0,
    var name: String? = "",
) : Parcelable