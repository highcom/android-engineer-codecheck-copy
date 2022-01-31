/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.SearchRepositoryMainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentDetailRepositoryBinding

/**
 * リポジトリ詳細画面
 */
@Keep
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

        // GitHubのユーザーホーム画面にブラウザで遷移する
        detailRepositoryBinding.htmlUrlBtn.setOnClickListener {
            if (!itemDetail.htmlUrl.equals("")) {
                val uri = Uri.parse(itemDetail.htmlUrl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                val chooser = Intent.createChooser(intent, "選択")
                startActivity(chooser)
            }
        }
    }
}
