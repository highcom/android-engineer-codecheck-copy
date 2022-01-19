/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.datamodel.GitHubRepository
import jp.co.yumemi.android.code_check.datamodel.ItemDetail
import kotlinx.coroutines.*

/**
 * GitHubリポジトリデータ取得
 */
class SearchRepositoryViewModel(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {
    private val _itemDetails = MutableLiveData<List<ItemDetail>>()

    /**
     * GitHubリポジトリデータ一覧
     */
    var itemDetails: LiveData<List<ItemDetail>> = _itemDetails

    /**
     * リポジトリ検索処理
     */
    fun searchRepositories(inputText: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _itemDetails.postValue(gitHubRepository.searchResults(inputText))
        }
    }
}
