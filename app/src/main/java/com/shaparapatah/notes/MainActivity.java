package com.shaparapatah.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.listNotes_container, ListNotesFragment.newInstance())
                    .commit();

        }
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;

    }


    @Override
    protected void onResume() {
        super.onResume();

        Fragment backStackFragment = getSupportFragmentManager()
                .findFragmentById(R.id.listNotes_container);
        if (backStackFragment != null && backStackFragment instanceof NotesFragment) {
            onBackPressed();
        }
    }
}