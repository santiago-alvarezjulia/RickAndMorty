package com.saj.rickandmorty

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.saj.rickandmorty.testUtils.AndroidTestsUtils
import com.saj.rickandmorty.testUtils.RecyclerViewItemCounterAssertion
import com.saj.rickandmorty.testUtils.launchFragmentInHiltContainer
import com.saj.rickandmorty.ui.MasterFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ListCharactersTest {

    companion object {
        private const val EXP_ITEM_COUNT = 1
    }

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var mockWebServer : MockWebServer

    @Inject
    lateinit var okHttp: OkHttpClient

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", okHttp))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun charactersList() {
        launchMasterFragment()

        mockWebServer.enqueue(
            AndroidTestsUtils.getMockResponse(
                AndroidTestsUtils.readJsonResponseAsString("1-character-200.json"),
                200
            )
        )

        onView(withId(R.id.characters_list)).check(RecyclerViewItemCounterAssertion(EXP_ITEM_COUNT))
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
