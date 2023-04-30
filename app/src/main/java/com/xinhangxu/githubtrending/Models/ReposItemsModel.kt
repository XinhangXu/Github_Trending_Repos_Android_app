package com.xinhangxu.githubtrending.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReposItemsModel {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("node_id")
    @Expose
    var node_id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("html_url")
    @Expose
    var html_url: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("language")
    @Expose
    var language: String? = null

    @SerializedName("created_at")
    @Expose
    var created_at: String? = null

    @SerializedName("updated_at")
    @Expose
    var updated_at: String? = null

    @SerializedName("watchers_count")
    @Expose
    var watchers_count: String? = null

    @SerializedName("score")
    @Expose
    var score: String? = null

    @SerializedName("forks_count")
    @Expose
    var forks_count: String? = null

    @SerializedName("open_issues_count")
    @Expose
    var open_issues_count: String? = null

    @SerializedName("owner")
    @Expose
    var owner: OwnerModel? = null


}