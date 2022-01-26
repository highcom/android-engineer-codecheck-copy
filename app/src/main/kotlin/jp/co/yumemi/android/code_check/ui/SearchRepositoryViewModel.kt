/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.content.ClipData
import androidx.lifecycle.*
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

    class Factory(
        private val repository: GitHubRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchRepositoryViewModel(repository) as T
        }
    }

    /**
     * GitHubリポジトリデータ一覧
     */
    var itemDetails: LiveData<List<ItemDetail>> = _itemDetails

    /**
     * リポジトリ検索処理
     */
    fun searchRepositories(inputText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _itemDetails.postValue(gitHubRepository.searchResults(inputText))
        }
    }
}
