/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchRepositoryBinding
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * リポジトリ検索画面
 */
@DelicateCoroutinesApi
class SearchRepositoryFragment : Fragment(R.layout.fragment_search_repository) {

    private lateinit var fragmentSearchRepositoryBinding: FragmentSearchRepositoryBinding
    private lateinit var gitHubRepositoryViewModel: GitHubRepositoryViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private lateinit var customAdapter: CustomAdapter

    /**
     * リポジトリ検索画面生成
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSearchRepositoryBinding = FragmentSearchRepositoryBinding.bind(view)

        gitHubRepositoryViewModel = GitHubRepositoryViewModel()

        linearLayoutManager = LinearLayoutManager(requireContext())
        dividerItemDecoration =
            DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        customAdapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(ItemDetail: ItemDetail) {
                gotoRepositoryFragment(ItemDetail)
            }
        })

        fragmentSearchRepositoryBinding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        gitHubRepositoryViewModel.searchResults(it).apply {
                            customAdapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        fragmentSearchRepositoryBinding.recyclerView.also {
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = customAdapter
        }
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

/**
 * アイテム一覧変化通知
 */
val DIFF___UTIL: DiffUtil.ItemCallback<ItemDetail> = object : DiffUtil.ItemCallback<ItemDetail>() {
    override fun areItemsTheSame(oldItemDetail: ItemDetail, newItemDetail: ItemDetail): Boolean {
        return oldItemDetail.name == newItemDetail.name
    }

    override fun areContentsTheSame(oldItemDetail: ItemDetail, newItemDetail: ItemDetail): Boolean {
        return oldItemDetail == newItemDetail
    }

}

/**
 * リポジトリ一覧表示項目用アダプタ
 */
class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<ItemDetail, CustomAdapter.ViewHolder>(DIFF___UTIL) {

    /**
     * 表示項目データホルダー
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    /**
     * 項目タップ時のリスナーインターフェース
     */
    interface OnItemClickListener {
        /**
         * 項目タップ時のリスナー関数
         */
        fun itemClick(ItemDetail: ItemDetail)
    }

    /**
     * 表示項目データホルダー生成
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_repository_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * 表示項目データホルダーのバインディング
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.repositoryNameView).text = item.name
        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
