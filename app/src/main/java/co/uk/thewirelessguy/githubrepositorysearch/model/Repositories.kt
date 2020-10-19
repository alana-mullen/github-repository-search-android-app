package co.uk.thewirelessguy.githubrepositorysearch.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Repositories(
    @Json(name = "total_count")
    val totalCount: Int? = 0,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean? = false,
    @Json(name = "items")
    val items: List<Item>? = listOf()
) {
    data class Item(
        @Json(name = "id")
        val id: Int? = 0,
        @Json(name = "node_id")
        val nodeId: String? = "",
        @Json(name = "name")
        val name: String? = "",
        @Json(name = "full_name")
        val fullName: String? = "",
        @Json(name = "owner")
        val owner: Owner? = Owner(),
        @Json(name = "private")
        val `private`: Boolean? = false,
        @Json(name = "html_url")
        val htmlUrl: String? = "",
        @Json(name = "description")
        val description: String? = "",
        @Json(name = "fork")
        val fork: Boolean? = false,
        @Json(name = "url")
        val url: String? = "",
        @Json(name = "created_at")
        val createdAt: String? = "",
        @Json(name = "updated_at")
        val updatedAt: String? = "",
        @Json(name = "pushed_at")
        val pushedAt: String? = "",
        @Json(name = "homepage")
        val homepage: String? = "",
        @Json(name = "size")
        val size: Int? = 0,
        @Json(name = "stargazers_count")
        val stargazersCount: Int? = 0,
        @Json(name = "watchers_count")
        val watchersCount: Int? = 0,
        @Json(name = "language")
        val language: String? = "",
        @Json(name = "forks_count")
        val forksCount: Int? = 0,
        @Json(name = "open_issues_count")
        val openIssuesCount: Int? = 0,
        @Json(name = "master_branch")
        val masterBranch: String? = "",
        @Json(name = "default_branch")
        val defaultBranch: String? = "",
        @Json(name = "score")
        val score: Double? = 0.0
    ) {
        data class Owner(
            @Json(name = "login")
            val login: String? = "",
            @Json(name = "id")
            val id: Int? = 0,
            @Json(name = "node_id")
            val nodeId: String? = "",
            @Json(name = "avatar_url")
            val avatarUrl: String? = "",
            @Json(name = "gravatar_id")
            val gravatarId: String? = "",
            @Json(name = "url")
            val url: String? = "",
            @Json(name = "received_events_url")
            val receivedEventsUrl: String? = "",
            @Json(name = "type")
            val type: String? = ""
        )
    }
}
