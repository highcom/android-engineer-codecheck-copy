/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GitHubApiInterface {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("items")
    suspend fun getItemDetails(
        @Query("q") q: String?
    ): Call<List<ItemDetail>>
}
/**
 * 詳細画面表示項目
 */
@Parcelize
data class ItemDetail(
    /**
     * リポジトリ名
     */
    val name: String,
    /**
     * ユーザーアイコン
     */
    val ownerIconUrl: String,
    /**
     * GitHubユーザーホームページ
     */
    val htmlUrl: String,
    /**
     * 使用言語
     */
    val language: String,
    /**
     * 星獲得数
     */
    val stargazersCount: Long,
    /**
     * ウォッチャー数
     */
    val watchersCount: Long,
    /**
     * フォーク数
     */
    val forksCount: Long,
    /**
     * イシュー数
     */
    val openIssuesCount: Long,
) : Parcelable
