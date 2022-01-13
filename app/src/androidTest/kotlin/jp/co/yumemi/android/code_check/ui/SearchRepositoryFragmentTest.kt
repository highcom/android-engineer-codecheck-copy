package jp.co.yumemi.android.code_check.ui

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.SearchRepositoryMainActivity
import jp.co.yumemi.android.code_check.datamodel.ItemDetail
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchRepositoryFragmentTest {
    private lateinit var itemDetail: ItemDetail

    @get:Rule
    var activityRule: ActivityTestRule<SearchRepositoryMainActivity>
            = ActivityTestRule(SearchRepositoryMainActivity::class.java)

    @Before
    fun initValidString() {
        itemDetail = ItemDetail(
            name = "test",
            ownerIconUrl = "",
            htmlUrl = "",
            language = "Kotlin",
            stargazersCount = 0,
            watchersCount = 1,
            forksCount = 2,
            openIssuesCount = 3
        )
    }

    @Test
    fun checkTitle() {
//        val scenario = launchFragmentInContainer<SearchRepositoryFragment>()
//        scenario.onFragment {
//            fragment -> fragment.gotoRepositoryFragment(itemDetail)
//        }
        Thread.sleep(3000)
//        typeText("aaa")
//        val testView = onView(withId(R.id.nav_host_fragment))
//        testView.perform(click())
//        Thread.sleep(3000)
//        val searchInputText = onView(withId(R.id.searchInputText))
//        searchInputText.perform(replaceText("test"))
        onView(withId(R.id.searchInputText)).perform(replaceText("test"), closeSoftKeyboard())
        pressKey(EditorInfo.IME_ACTION_SEARCH)
//        onView(withId(R.id.searchInputText)).perform(pressKey(EditorInfo.IME_ACTION_SEARCH))
        Thread.sleep(3000)
    }
}