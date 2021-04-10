//package com.example.ultratracker;
//
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import androidx.test.espresso.DataInteraction;
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.filters.LargeTest;
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.rule.ActivityTestRule;
//import androidx.test.runner.AndroidJUnit4;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.time.LocalDate;
//
//import static androidx.test.espresso.Espresso.onData;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.replaceText;
//import static androidx.test.espresso.action.ViewActions.scrollTo;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withParent;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.anything;
//import static org.hamcrest.Matchers.is;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class CreateTaskWithTodo {
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void createTaskWithTodo() {
//        ViewInteraction materialButton = onView(
//                allOf(withId(R.id.Todo_Button), withText("Todo"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                1),
//                        isDisplayed()));
//        materialButton.perform(click());
//
//        ViewInteraction materialButton2 = onView(
//                allOf(withId(R.id.todo_add_button), withText("Add"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                9),
//                        isDisplayed()));
//        materialButton2.perform(click());
//
//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.name_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                5),
//                        isDisplayed()));
//        appCompatEditText.perform(replaceText("TestAddTastWithTodo"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.description_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                11),
//                        isDisplayed()));
//        appCompatEditText2.perform(replaceText("test"), closeSoftKeyboard());
//
//        ViewInteraction materialButton3 = onView(
//                allOf(withId(R.id.edit_date), withText("Edit"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                6),
//                        isDisplayed()));
//        materialButton3.perform(click());
//
//        ViewInteraction materialButton4 = onView(
//                allOf(withId(android.R.id.button1), withText("Set"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        materialButton4.perform(scrollTo(), click());
//
//        ViewInteraction materialButton5 = onView(
//                allOf(withId(R.id.edit_time), withText("Edit"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                15),
//                        isDisplayed()));
//        materialButton5.perform(click());
//
//        ViewInteraction materialButton6 = onView(
//                allOf(withId(android.R.id.button1), withText("Set"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        materialButton6.perform(scrollTo(), click());
//
//        ViewInteraction appCompatSpinner = onView(
//                allOf(withId(R.id.priority_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                13),
//                        isDisplayed()));
//        appCompatSpinner.perform(click());
//
//        DataInteraction appCompatCheckedTextView = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(1);
//        appCompatCheckedTextView.perform(click());
//
//        ViewInteraction materialButton7 = onView(
//                allOf(withId(R.id.create_task_button), withText("Create Task"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                3),
//                        isDisplayed()));
//        materialButton7.perform(click());
//
//        ViewInteraction textView = onView(
//                allOf(withText("Task"),
//                        withParent(withParent(withId(R.id.recent_table))),
//                        isDisplayed()));
//        textView.check(matches(withText("Task")));
//
//        ViewInteraction textView2 = onView(
//                allOf(withText("TestAddTastW.."),
//                        withParent(withParent(withId(R.id.recent_table))),
//                        isDisplayed()));
//        textView2.check(matches(withText("TestAddTastW..")));
//
//        ViewInteraction textView3 = onView(
//                allOf(withText(LocalDate.now().toString()),
//                        withParent(withParent(withId(R.id.recent_table))),
//                        isDisplayed()));
//        textView3.check(matches(withText(LocalDate.now().toString())));
//    }
//
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
//}
