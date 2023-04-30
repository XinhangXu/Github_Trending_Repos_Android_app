package com.xinhangxu.githubtrending.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OwnerModel {


    @SerializedName("avatar_url")
    @Expose
    var avatar_url: String? = null


    @SerializedName("login")
    @Expose
    var login: String? = null


}