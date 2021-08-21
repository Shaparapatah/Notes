package com.shaparapatah.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.listNotes_container, ListNotesFragment.newInstance())
                    .commit();

        }
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