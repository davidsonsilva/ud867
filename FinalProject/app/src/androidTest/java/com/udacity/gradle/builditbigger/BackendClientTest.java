package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BackendClientTest {

    @Test
    public void tellJokeFromAPI() {
        new BackendClient().doInBackground(new BackendClient.Callback() {
            @Override
            public void onDone(String result) {
                Assert.assertEquals("Q: What is a well-read cat's favorite book?\n" +
                        "\n" +
                        "A: Of Mice and Men\n" +
                        "\n",  result);
            }
        });
    }
}