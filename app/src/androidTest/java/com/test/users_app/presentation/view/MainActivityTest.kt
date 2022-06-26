package com.test.users_app.presentation.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.test.users_app.R
import com.test.users_app.presentation.view.actions.ViewHolderItemAction
import com.test.users_app.presentation.view.adapter.viewholders.UserViewHolder
import com.test.users_app.util.ExpressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @Before
    fun registerIR() {
        IdlingRegistry.getInstance().register(ExpressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIR() {
        IdlingRegistry.getInstance().unregister(ExpressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testIsActivityInView() {
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.mainActivityView)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun testIsTextviewsEditTextRecyclerviewProgressBarDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.tvFindUser)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.edFindUser)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rvUserList)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.tvMessage)).check(
            ViewAssertions.matches(
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.pbFinduser)).check(
            ViewAssertions.matches(
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)
            )
        )
    }

    @Test
    fun testIsFindTextDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.tvFindUser)).check(
            ViewAssertions.matches(ViewMatchers.withText(R.string.find_user) )
        )
    }

    @Test
    fun testRecyclerView() {
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.rvUserList)).perform(RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(0, ViewHolderItemAction.clickChildViewWithId(R.id.tvPublications)))
        Espresso.pressBack()
    }

    @Test
    fun testRecyclerViewNotVisibleMessageTextDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.edFindUser)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.edFindUser)).perform(ViewActions.typeText("item not found"))

        Espresso.onView(ViewMatchers.withId(R.id.tvMessage)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rvUserList)).check(
            ViewAssertions.matches(
                ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.edFindUser)).perform(ViewActions.clearText())
    }
}