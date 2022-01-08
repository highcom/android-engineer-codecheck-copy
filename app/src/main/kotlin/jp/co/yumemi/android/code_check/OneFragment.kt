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
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding

/**
 * リポジトリ検索画面
 */
class OneFragment : Fragment(R.layout.fragment_one) {

    /**
     * リポジトリ検索画面生成
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentOneBinding = FragmentOneBinding.bind(view)

        val oneViewModel = OneViewModel(context!!)

        val linearLayoutManager = LinearLayoutManager(context!!)
        val dividerItemDecoration =
            DividerItemDecoration(context!!, linearLayoutManager.orientation)
        val customAdapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: item) {
                gotoRepositoryFragment(item)
            }
        })

        fragmentOneBinding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        oneViewModel.searchResults(it).apply {
                            customAdapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        fragmentOneBinding.recyclerView.also {
            it.layoutManager = linearLayoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = customAdapter
        }
    }

    /**
     * リポジトリ詳細画面遷移
     */
    fun gotoRepositoryFragment(item: item) {
        val navDirections = OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(navDirections)
    }
}

/**
 * アイテム一覧変化通知
 */
val DIFF___UTIL: DiffUtil.ItemCallback<item> = object : DiffUtil.ItemCallback<item>() {
    override fun areItemsTheSame(oldItem: item, newItem: item): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: item, newItem: item): Boolean {
        return oldItem == newItem
    }

}

/**
 * リポジトリ一覧表示項目用アダプタ
 */
class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<item, CustomAdapter.ViewHolder>(DIFF___UTIL) {

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
        fun itemClick(item: item)
    }

    /**
     * 表示項目データホルダー生成
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * 表示項目データホルダーのバインディング
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
