package com.android.youtubeproject.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CategoryModel(val type:Int, var id:String, val category:String) : Parcelable