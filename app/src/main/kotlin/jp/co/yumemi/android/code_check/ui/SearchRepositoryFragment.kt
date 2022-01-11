/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentSearchRepositoryBinding
import jp.co.yumemi.android.code_check.datamodel.GitHubRepository
import jp.co.yumemi.android.code_check.datamodel.ItemDetail
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * リポジトリ検索画面
 */
@DelicateCoroutinesApi
class SearchRepositoryFragment : Fragment(R.layout.fragment_search_repository) {

    private lateinit var fragmentSearchRepositoryBinding: FragmentSearchRepositoryBinding
    private lateinit var searchRepositoryViewModel: SearchRepositoryViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private lateinit var customAdapter: CustomAdapter

    /**
     * リポジトリ検索画面生成
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSearchRepositoryBinding = FragmentSearchRepositoryBinding.bind(view)

        searchRepositoryViewModel = SearchRepositoryViewModel(GitHubRepository())

        linearLayoutManager = LinearLayoutManager(requireContext())
        dividerItemDecoration =
            DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        customAdapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(ItemDetail: ItemDetail) {
                gotoRepositoryFragment(ItemDetail)
            }
        })

        fragmentSearchRepositoryBinding.recyclerView.also {
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = customAdapter
        }

        // 入力文字列でリポジトリを検索
        fragmentSearchRepositoryBinding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        searchRepositoryViewModel.searchRepositories(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        // 検索結果を監視してアダプタに反映
        searchRepositoryViewModel.itemDetails.observe(viewLifecycleOwner, { it ->
            it?.let { customAdapter.submitList(it) }
        })
    }

    /**
     * リポジトリ詳細画面遷移
     */
    fun gotoRepositoryFragment(itemDetail: ItemDetail) {
        val navDirections = SearchRepositoryFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(itemDetail = itemDetail)
        findNavController().navigate(navDirections)
    }
}
