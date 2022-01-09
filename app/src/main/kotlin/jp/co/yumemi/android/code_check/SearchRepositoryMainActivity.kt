/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * GitHubリポジトリ検索メインアクティビティ
 */
class SearchRepositoryMainActivity : AppCompatActivity(R.layout.activity_search_repository_main) {

    companion object {
        /**
         * 前回検索日付
         */
        lateinit var lastSearchDate: Date
    }
}
