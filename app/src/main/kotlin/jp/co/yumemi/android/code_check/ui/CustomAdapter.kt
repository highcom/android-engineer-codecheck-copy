/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.datamodel.ItemDetail

/**
 * リポジトリ一覧表示項目用アダプタ
 */
class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<ItemDetail, CustomAdapter.ViewHolder>(DIFF___UTIL) {

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
        return ViewHolder.create(parent)
    }

    /**
     * 表示項目データホルダーのバインディング
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }

    /**
     * 表示項目データホルダー
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val repositoryNameView: TextView = view.findViewById(R.id.repositoryNameView)

        /**
         * 表示項目のバインド
         */
        fun bind(itemDetail: ItemDetail, itemClickListener: OnItemClickListener) {
            repositoryNameView.text = itemDetail.name
            itemView.setOnClickListener {
                itemClickListener.itemClick(itemDetail)
            }
        }

        companion object {
            /**
             * 表示項目データホルダー生成
             */
            fun create(parent: ViewGroup): ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_repository_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

    companion object {
        /**
         * アイテム一覧変化通知
         */
        private val DIFF___UTIL: DiffUtil.ItemCallback<ItemDetail> =
            object : DiffUtil.ItemCallback<ItemDetail>() {
                override fun areItemsTheSame(
                    oldItemDetail: ItemDetail,
                    newItemDetail: ItemDetail
                ): Boolean {
                    return oldItemDetail.name == newItemDetail.name
                }

                override fun areContentsTheSame(
                    oldItemDetail: ItemDetail,
                    newItemDetail: ItemDetail
                ): Boolean {
                    return oldItemDetail == newItemDetail
                }

            }
    }
}
