package com.saj.rickandmorty.testUtils

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher

object MyViewAction {

    fun clickViewWithId(id: Int): ViewAction {
        return object: ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click view with matching id"
            }

            override fun perform(uiController: UiController?, view: View?) {
                view?.findViewById<View>(id)?.performClick() ?: throw Exception()
            }

        }
    }
}