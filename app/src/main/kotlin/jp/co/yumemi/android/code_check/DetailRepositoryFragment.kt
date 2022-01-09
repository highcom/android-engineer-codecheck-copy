/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.SearchRepositoryMainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentDetailRepositoryBinding

/**
 * リポジトリ詳細画面
 */
class DetailRepositoryFragment : Fragment(R.layout.fragment_detail_repository) {

    private val args: DetailRepositoryFragmentArgs by navArgs()

    private lateinit var detailRepositoryBinding: FragmentDetailRepositoryBinding

    /**
     * リポジトリ詳細画面生成
     */
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lastSearchDate?.toString()?.let { Log.d("検索した日時", it) }

        detailRepositoryBinding = FragmentDetailRepositoryBinding.bind(view)

        val itemDetail = args.itemDetail

        detailRepositoryBinding.ownerIconView.load(itemDetail.ownerIconUrl)
        detailRepositoryBinding.nameView.text = itemDetail.name
        detailRepositoryBinding.languageView.text =
            getString(R.string.written_language, itemDetail.language)
        detailRepositoryBinding.starsView.text =
            getString(R.string.count_starts, itemDetail.stargazersCount.toString())
        detailRepositoryBinding.watchersView.text =
            getString(R.string.count_watchers, itemDetail.watchersCount.toString())
        detailRepositoryBinding.forksView.text =
            getString(R.string.count_forks, itemDetail.forksCount.toString())
        detailRepositoryBinding.openIssuesView.text =
            getString(R.string.count_open_issues, itemDetail.openIssuesCount.toString())
    }
}
