package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class BackendClient extends AsyncTask<BackendClient.Callback, Void, String>{

    private MyApi mApi = null;
    private Callback mCallback = null;

    @Override
    protected String doInBackground(Callback... callbacks) {
        mCallback = callbacks[0];

        if (mApi == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            mApi = builder.build();
        }

        try {
            return mApi.getJoke().execute().getJoke();
        } catch (IOException e) {
            Log.e(BackendClient.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        if (mCallback != null) {
            mCallback.onDone(joke);
        }
    }

    interface Callback {
        void onDone(String result);
    }
}