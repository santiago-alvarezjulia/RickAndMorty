package com.saj.rickandmorty

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.saj.rickandmorty.testUtils.RecyclerViewItemCounterAssertion
import com.saj.rickandmorty.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ListCharactersTest {

    private val EXP_ITEM_COUNT = 3

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun charactersList() {
        onView(withId(R.id.characters_list)).check(RecyclerViewItemCounterAssertion(EXP_ITEM_COUNT))
    }
}
