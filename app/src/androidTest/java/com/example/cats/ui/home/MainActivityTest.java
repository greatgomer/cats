package com.example.cats.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.cats.R;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() {
    }

    @Test
    public void onSwipe() {
        onView(withId(R.id.action_authorisation)).perform(click());
        onView(withId(R.id.editTextTextPersonName))
                .perform(setTextInTextView("test3"));
        onView(withId(R.id.button_apply)).perform(click());
    }

    @Test
    public void onButtonPage2Click() {
        onView(ViewMatchers.withId(R.id.catRecyclerView)).perform(ViewActions.swipeUp());
        onView(ViewMatchers.withId(R.id.catRecyclerView)).perform(ViewActions.swipeDown());
    }

    @Test
    public void onButtonPage3Click() {
        onView(withId(R.id.page_3)).perform(click());
        onView(withId(R.id.fab)).perform(click());
    }

    public static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }

}