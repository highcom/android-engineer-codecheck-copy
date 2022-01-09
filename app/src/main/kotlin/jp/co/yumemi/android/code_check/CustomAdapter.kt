package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

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
