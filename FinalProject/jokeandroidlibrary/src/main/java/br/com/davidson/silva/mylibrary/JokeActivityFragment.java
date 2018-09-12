package br.com.davidson.silva.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static br.com.davidson.silva.mylibrary.JokeActivity.JOKE_KEY;

public class JokeActivityFragment extends Fragment {
    public JokeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke, container, false);
        TextView jokeTextView = root.findViewById(R.id.joke_textview);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(JOKE_KEY)) {
            String joke = intent.getStringExtra(JOKE_KEY);
            jokeTextView.setText(joke);
        } else {
            jokeTextView.setText(R.string.error_no_joke);
        }

        return root;
    }
}