package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.davidson.silva.lib.JokeClass;
import br.com.davidson.silva.mylibrary.JokeActivity;


public class MainActivity extends AppCompatActivity implements BackendClient.Callback{

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Tell joke from library
     * @author Davidson Silva
     */

    public void tellJoke() {
        JokeClass myCatJoke = new JokeClass();
        Toast.makeText(this, myCatJoke.tellMeAJoke(), Toast.LENGTH_LONG).show();
    }


    public void tellJokeFromAndroidlibrary() {
        Intent intent = new Intent(this, JokeActivity.class);
        JokeClass jokeSource = new JokeClass();
        String joke = jokeSource.tellMeAJoke();
        intent.putExtra(JokeActivity.JOKE_KEY, joke);
        startActivity(intent);
    }

    private void tellJokeFromApi(){
        new BackendClient().execute(this);
    }

    public void tellJokeFromAPI() {
        mProgressBar.setVisibility(View.VISIBLE);
        tellJokeFromApi();
    }

    @Override
    public void onDone(String result) {
        Intent intent = new Intent(this, JokeActivity.class);
        if (result != null) {
            intent.putExtra(JokeActivity.JOKE_KEY, result);
        }
        startActivity(intent);
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
