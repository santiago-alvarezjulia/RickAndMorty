package com.saj.rickandmorty

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.saj.rickandmorty.idlingResources.EspressoCountingIdlingResource
import com.saj.rickandmorty.testUtils.AndroidTestsUtils
import com.saj.rickandmorty.testUtils.MyViewAction
import com.saj.rickandmorty.testUtils.launchFragmentInHiltContainer
import com.saj.rickandmorty.ui.MasterFragment
import com.saj.rickandmorty.ui.adapters.ShowCharactersAdapter
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
class DetailFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

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
    fun showCharacterDetailData() {
        launchMasterFragment()

        mockWebServer.enqueue(
            AndroidTestsUtils.getMockResponse(
                AndroidTestsUtils.readJsonResponseAsString("3-characters-200.json"),
                200
            )
        )

        onView(ViewMatchers.withId(R.id.characters_list))
            .perform(actionOnItemAtPosition<ShowCharactersAdapter.ViewHolder>(0,
                MyViewAction.clickViewWithId(R.id.character_item)))

        onView(withText("Name: Rick Sanchez")).check(matches(isDisplayed()))
        onView(withText("Status: Alive")).check(matches(isDisplayed()))
    }

    private fun launchMasterFragment() {
        launchFragmentInHiltContainer<MasterFragment> {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.masterFragment)
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    // The fragment’s view has just been created
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
    }
}