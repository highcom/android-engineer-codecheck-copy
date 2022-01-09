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

    private var binding: FragmentDetailRepositoryBinding? = null
    private val _binding get() = binding!!

    /**
     * リポジトリ詳細画面生成
     */
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentDetailRepositoryBinding.bind(view)

        val item = args.item

        _binding.ownerIconView.load(item.ownerIconUrl)
        _binding.nameView.text = item.name
        _binding.languageView.text = item.language
        _binding.starsView.text = "${item.stargazersCount} stars"
        _binding.watchersView.text = "${item.watchersCount} watchers"
        _binding.forksView.text = "${item.forksCount} forks"
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues"
    }
}
