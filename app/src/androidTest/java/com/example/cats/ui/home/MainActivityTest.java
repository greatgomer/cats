package com.example.cats.ui.home;

import androidx.test.rule.ActivityTestRule;

import com.example.cats.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() {
    }

    @Test
    public void onButtonPage2Click() {
        onView(withId(R.id.page_2)).perform(click());
    }

    @Test
    public void onButtonPage3Click() {
        onView(withId(R.id.page_3)).perform(click());
    }

    @Test
    public void onButtonChoose() {
        onView(withId(R.id.page_3)).perform(click());
        onView(withId(R.id.fab)).perform(click());
    }
}