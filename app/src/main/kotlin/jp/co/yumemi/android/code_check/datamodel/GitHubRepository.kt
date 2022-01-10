package jp.co.yumemi.android.code_check.datamodel

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.SearchRepositoryMainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.*

/**
 * GitHubデータ取得リポジトリ
 */
class GitHubRepository {

    /**
     * 検索結果
     */
    @DelicateCoroutinesApi
    fun searchResults(inputText: String): List<ItemDetail> = runBlocking {
        val client = HttpClient(Android)

        return@runBlocking GlobalScope.async {
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
                    val name = jsonItem?.optString("full_name")
                    val ownerIconUrl = jsonItem?.optJSONObject("owner")?.optString("avatar_url")
                    val language = jsonItem?.optString("language")
                    val stargazersCount = jsonItem?.optLong("stargazers_count")
                    val watchersCount = jsonItem?.optLong("watchers_count")
                    val forksCount = jsonItem?.optLong("forks_count")
                    val openIssuesCount = jsonItem?.optLong("open_issues_count")

                    items.add(
                        ItemDetail(
                            name = name ?: "Anonymous",
                            ownerIconUrl = ownerIconUrl ?: "",
                            language = language ?: "None",
                            stargazersCount = stargazersCount ?: 0,
                            watchersCount = watchersCount ?: 0,
                            forksCount = forksCount ?: 0,
                            openIssuesCount = openIssuesCount ?: 0
                        )
                    )
                }
                SearchRepositoryMainActivity.lastSearchDate = Date()

                return@async items.toList()
            } catch (e: Exception) {
                Log.d("HTTPクライアント", e.message.toString())
                return@async items.toList()
            }
        }.await()
    }
}