package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.davidson.silva.mylibrary.JokeActivity;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private BackendClient mBackendClient;

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(isInternal()).respondWith(new Instrumentation.ActivityResult(
                Activity.RESULT_OK, null));
    }

    @Before
    public void registerIdlingResource() {
        mBackendClient = new BackendClient();
        mBackendClient.setUnderEspresso();
        IdlingRegistry.getInstance().register(mBackendClient);
    }

    @Test
    public void tellJokeFromAPI() {
        mBackendClient.execute(mActivityRule.getActivity());

        intended(allOf(
                hasComponent(JokeActivity.class.getName()),
                hasExtraWithKey(JokeActivity.JOKE_KEY),
                hasExtra(equalTo(JokeActivity.JOKE_KEY), allOf(
                        not(equalTo("")),
                        not(equalTo(null))))

        ));
    }

    @After
    public void unregisterIdlingResource() {
        if (mBackendClient != null) {
            IdlingRegistry.getInstance().unregister(mBackendClient);
        }
    }
}