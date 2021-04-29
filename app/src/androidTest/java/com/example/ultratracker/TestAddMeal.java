//package com.example.ultratracker;
//
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.widget.DatePicker;
//
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.espresso.contrib.PickerActions;
//import androidx.test.filters.LargeTest;
//import androidx.test.rule.ActivityTestRule;
//import androidx.test.runner.AndroidJUnit4;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.Matchers;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.replaceText;
//import static androidx.test.espresso.action.ViewActions.scrollTo;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
//import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.is;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class TestAddMeal {
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void testAddMeal() {
//        ViewInteraction overflowMenuButton = onView(
//                allOf(withContentDescription("More options"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.action_bar),
//                                        1),
//                                0),
//                        isDisplayed()));
//        overflowMenuButton.perform(click());
//
//        ViewInteraction materialTextView = onView(
//                allOf(withId(R.id.title), withText("Settings"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.content),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialTextView.perform(click());
//
//        ViewInteraction materialButton = onView(
//                allOf(withId(R.id.erase_button), withText("Erase"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                3),
//                        isDisplayed()));
//        materialButton.perform(click());
//
//        ViewInteraction materialButton2 = onView(
//                allOf(withId(R.id.addHealth), withText("Health"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                8),
//                        isDisplayed()));
//        materialButton2.perform(click());
//
//        onView(withId(R.layout.activity_add_meal));
//
//        ViewInteraction materialButton5 = onView(
//                allOf(withId(R.id.add_meal_button), withText("Add Meal"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                6),
//                        isDisplayed()));
//        materialButton5.perform(click());
//
//        ViewInteraction materialButton6 = onView(
//                allOf(withId(R.id.add_food_button), withText("Add New Food"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                6),
//                        isDisplayed()));
//        materialButton6.perform(click());
//
//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.foodname_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                3),
//                        isDisplayed()));
//        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.calories_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                5),
//                        isDisplayed()));
//        appCompatEditText2.perform(replaceText("1"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText3 = onView(
//                allOf(withId(R.id.protein_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                7),
//                        isDisplayed()));
//        appCompatEditText3.perform(replaceText("2"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText4 = onView(
//                allOf(withId(R.id.carbs_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                9),
//                        isDisplayed()));
//        appCompatEditText4.perform(replaceText("3"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText5 = onView(
//                allOf(withId(R.id.fat_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                11),
//                        isDisplayed()));
//        appCompatEditText5.perform(replaceText("4"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText6 = onView(
//                allOf(withId(R.id.fiber_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                13),
//                        isDisplayed()));
//        appCompatEditText6.perform(replaceText("5"), closeSoftKeyboard());
//
//        ViewInteraction materialButton7 = onView(
//                allOf(withId(R.id.create_food_button), withText("Create Food"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                15),
//                        isDisplayed()));
//        materialButton7.perform(click());
//
//        ViewInteraction appCompatEditText7 = onView(
//                allOf(withId(R.id.meal_name_entry),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                15),
//                        isDisplayed()));
//        appCompatEditText7.perform(replaceText("Meal"), closeSoftKeyboard());
//
//        ViewInteraction materialButton8 = onView(
//                allOf(withId(R.id.create_meal_button), withText("Create"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                16),
//                        isDisplayed()));
//        materialButton8.perform(click());
//
//        ViewInteraction tableRow = onView(
//                childAtPosition(
//                        allOf(withId(R.id.meal_table),
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        0)),
//                        1));
//        tableRow.perform(scrollTo(), click());
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
