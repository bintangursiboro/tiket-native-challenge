package com.ijniclohot.ticketnativechallenge.model

import com.google.gson.annotations.SerializedName

data class GithubUsersResponse (
    @SerializedName( "total_count")
    val totalCount: Long,

    @SerializedName( "incomplete_results")
    val incompleteResults: Boolean,

    val items: List<GithubUser>
)

data class GithubUser (
    val login: String,
    val id: Long,

    @SerializedName( "node_id")
    val nodeID: String,

    @SerializedName( "avatar_url")
    val avatarURL: String,

    @SerializedName( "gravatar_id")
    val gravatarID: String,

    val url: String,

    @SerializedName( "html_url")
    val htmlURL: String,

    @SerializedName( "followers_url")
    val followersURL: String,

    @SerializedName( "following_url")
    val followingURL: String,

    @SerializedName( "gists_url")
    val gistsURL: String,

    @SerializedName( "starred_url")
    val starredURL: String,

    @SerializedName( "subscriptions_url")
    val subscriptionsURL: String,

    @SerializedName( "organizations_url")
    val organizationsURL: String,

    @SerializedName( "repos_url")
    val reposURL: String,

    @SerializedName( "events_url")
    val eventsURL: String,

    @SerializedName( "received_events_url")
    val receivedEventsURL: String,

    val type: Type,

    @SerializedName( "site_admin")
    val siteAdmin: Boolean,

    val score: Long
)

enum class Type(val value: String) {
    User("User");

    companion object {
        public fun fromValue(value: String): Type = when (value) {
            "User" -> User
            else   -> throw IllegalArgumentException()
        }
    }
}
