package jp.co.yumemi.android.code_check.datamodel

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.SearchRepositoryMainActivity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.util.*

/**
 * GitHubデータ取得リポジトリ
 */
class GitHubRepository {

    /**
     * 検索結果
     */
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun searchResults(inputText: String): List<ItemDetail> {
        val client = HttpClient(Android)

        val items = mutableListOf<ItemDetail>()

        try {
            val response: HttpResponse =
                client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }

            val jsonBody = JSONObject(response.receive<String>())

            val jsonItems = jsonBody.optJSONArray("items")!!

            // アイテムの個数分ループする
            for (i in 0 until jsonItems.length()) {
                val jsonItem = jsonItems.optJSONObject(i)
                val item = Json.decodeFromString<ItemDetail>("""$jsonItem""")
//                val name = jsonItem?.optString("full_name")
//                val ownerIconUrl = jsonItem?.optJSONObject("owner")?.optString("avatar_url")
//                val htmlUrl = jsonItem?.optJSONObject("owner")?.optString("html_url")
//                val language = jsonItem?.optString("language")
//                val stargazersCount = jsonItem?.optLong("stargazers_count")
//                val watchersCount = jsonItem?.optLong("watchers_count")
//                val forksCount = jsonItem?.optLong("forks_count")
//                val openIssuesCount = jsonItem?.optLong("open_issues_count")

                items.add(
                    ItemDetail(
                        name = item.name ?: "Anonymous",
                        ownerIconUrl = item.ownerIconUrl ?: "",
                        htmlUrl = item.htmlUrl ?: "",
                        language = item.language ?: "None",
                        stargazersCount = item.stargazersCount ?: 0,
                        watchersCount = item.watchersCount ?: 0,
                        forksCount = item.forksCount ?: 0,
                        openIssuesCount = item.openIssuesCount ?: 0
                    )
                )
            }
            SearchRepositoryMainActivity.lastSearchDate = Date()

            return items.toList()
        } catch (e: Exception) {
            Log.d("HTTPクライアント", e.message.toString())
            return items.toList()
        }
    }
}