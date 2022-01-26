package jp.co.yumemi.android.code_check.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import jp.co.yumemi.android.code_check.datamodel.GitHubRepository
import jp.co.yumemi.android.code_check.datamodel.ItemDetail
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SearchRepositoryViewModelUnitTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        // Exception in thread "main" java.lang.IllegalStateException 対策で必要
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        // Exception in thread "main" java.lang.IllegalStateException 対策で必要
        Dispatchers.resetMain()
    }

    @DelicateCoroutinesApi
    @Test
    fun searchRepository_正常系テスト() {
        val inputText = "test"
        val mockRepository = mockk<GitHubRepository>()
        val target = SearchRepositoryViewModel(mockRepository)
        val result = mutableListOf<ItemDetail>()
        result.add(
            ItemDetail(
                name = "CorrectTest",
                ownerIconUrl = "https://github.com/",
                htmlUrl = "https://github.com/",
                language = "Kotlin",
                stargazersCount = 1,
                watchersCount = 2,
                forksCount = 3,
                openIssuesCount = 4
            )
        )

        coEvery { mockRepository.searchResults(inputText) } returns result

        val mockObserver = spyk<Observer<List<ItemDetail>>>()
        target.itemDetails.observeForever(mockObserver)

        target.searchRepositories(inputText)

        verify(exactly = 1) {
            mockObserver.onChanged(result)
        }

        assertEquals(1, target.itemDetails.value?.size)
    }

    @DelicateCoroutinesApi
    @Test
    fun searchRepository_異常系テスト() {
        val inputText = ""
        val mockRepository = mockk<GitHubRepository>()
        val target = SearchRepositoryViewModel(mockRepository)
        val result = mutableListOf<ItemDetail>()

        coEvery { mockRepository.searchResults(inputText) } returns result

        val mockObserver = spyk<Observer<List<ItemDetail>>>()
        target.itemDetails.observeForever(mockObserver)

        target.searchRepositories(inputText)

        verify(exactly = 1) {
            mockObserver.onChanged(result)
        }

        assertEquals(0, target.itemDetails.value?.size)
    }
}