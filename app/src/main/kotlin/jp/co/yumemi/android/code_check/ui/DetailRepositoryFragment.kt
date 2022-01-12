/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
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
        detailRepositoryBinding.starsCountView.text = itemDetail.stargazersCount.toString()
        detailRepositoryBinding.watchersCountView.text = itemDetail.watchersCount.toString()
        detailRepositoryBinding.forksCountView.text = itemDetail.forksCount.toString()
        detailRepositoryBinding.openIssuesCountView.text = itemDetail.openIssuesCount.toString()
    }
}
