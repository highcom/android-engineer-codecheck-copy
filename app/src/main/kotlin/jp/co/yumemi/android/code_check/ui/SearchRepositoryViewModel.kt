/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.yumemi.android.code_check.datamodel.GitHubRepository
import jp.co.yumemi.android.code_check.datamodel.ItemDetail
import kotlinx.coroutines.*

/**
 * GitHubリポジトリデータ取得
 */
class SearchRepositoryViewModel : ViewModel() {
    private val _itemDetails = MutableLiveData<List<ItemDetail>>()

    /**
     * GitHubリポジトリデータ一覧
     */
    var itemDetails: LiveData<List<ItemDetail>> = _itemDetails
    private val gitHubRepository: GitHubRepository = GitHubRepository()

    /**
     * リポジトリ検索処理
     */
    @DelicateCoroutinesApi
    fun searchRepositories(inputText: String) {
        _itemDetails.value = gitHubRepository.searchResults(inputText)
    }
}
