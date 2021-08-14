package com.saj.rickandmorty

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.saj.rickandmorty.testUtils.launchFragmentInHiltContainer
import com.saj.rickandmorty.ui.DetailFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val showCharacterName = "Rick Sanchez"
    private val showCharacterStatus = "Alive"

    @Test
    fun showCharacterDetailData() {
        launchFragmentInHiltContainer<DetailFragment>()

        onView(withText(showCharacterName)).check(matches(isDisplayed()))
        onView(withText(showCharacterStatus)).check(matches(isDisplayed()))
    }
}