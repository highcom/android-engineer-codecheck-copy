/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
    private val searchRepositoryViewModel : SearchRepositoryViewModel by lazy {
        val repository = GitHubRepository()
        val factory = SearchRepositoryViewModel.Factory(repository)
        ViewModelProvider(this, factory)[SearchRepositoryViewModel::class.java]
    }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var customAdapter: CustomAdapter

    /**
     * リポジトリ検索画面生成
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSearchRepositoryBinding = FragmentSearchRepositoryBinding.bind(view)

        linearLayoutManager = LinearLayoutManager(requireContext())
        customAdapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(ItemDetail: ItemDetail) {
                gotoRepositoryFragment(ItemDetail)
            }
        })

        fragmentSearchRepositoryBinding.recyclerView.also {
            it.layoutManager = linearLayoutManager
            it.adapter = customAdapter
        }

        // 入力文字列でリポジトリを検索
        fragmentSearchRepositoryBinding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        searchRepositoryViewModel.searchRepositories(it)
                    }

                    // 検索した後はキーボードを閉じる
                    val inputMethodManager: InputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        // 検索結果を監視してアダプタに反映
        searchRepositoryViewModel.itemDetails.observe(viewLifecycleOwner, { it ->
            it?.let {
                customAdapter.submitList(it)

                if (it.isEmpty()) {
                    // 検索結果が0件だった事を通知
                    Toast.makeText(context, getString(R.string.search_result_zero),
                        Toast.LENGTH_SHORT).show()
                }
            }
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
