/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.datamodel

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 詳細画面表示項目
 */
@Keep
@Parcelize
@Serializable
data class ItemDetail(
    /**
     * リポジトリ名
     */
    @SerialName("full_name")
    val name: String,
    /**
     * ユーザーアイコン
     */
    @SerialName("avatar_url")
    val ownerIconUrl: String,
    /**
     * GitHubユーザーホームページ
     */
    @SerialName("html_url")
    val htmlUrl: String,
    /**
     * 使用言語
     */
    @SerialName("language")
    val language: String,
    /**
     * 星獲得数
     */
    @SerialName("stargazers_count")
    val stargazersCount: Long,
    /**
     * ウォッチャー数
     */
    @SerialName("watchers_count")
    val watchersCount: Long,
    /**
     * フォーク数
     */
    @SerialName("forks_count")
    val forksCount: Long,
    /**
     * イシュー数
     */
    @SerialName("open_issues_count")
    val openIssuesCount: Long,
) : Parcelable
