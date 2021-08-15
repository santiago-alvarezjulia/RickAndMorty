package com.saj.rickandmorty

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.saj.rickandmorty.idlingResources.EspressoCountingIdlingResource
import com.saj.rickandmorty.testUtils.AndroidTestsUtils
import com.saj.rickandmorty.testUtils.RecyclerViewItemCounterAssertion
import com.saj.rickandmorty.testUtils.launchFragmentInHiltContainer
import com.saj.rickandmorty.ui.MasterFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MasterFragmentTest {

    companion object {
        private const val EXP_ITEM_COUNT = 3
    }

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var mockWebServer : MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(EspressoCountingIdlingResource.countingIdlingResource.value)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoCountingIdlingResource.countingIdlingResource.value)
    }

    @Test
    fun charactersList() {
        launchFragmentInHiltContainer<MasterFragment>()

        mockWebServer.enqueue(
            AndroidTestsUtils.getMockResponse(
                AndroidTestsUtils.readJsonResponseAsString("3-characters-200.json"),
                200
            )
        )

        onView(withId(R.id.characters_list)).check(RecyclerViewItemCounterAssertion(EXP_ITEM_COUNT))
    }
}
