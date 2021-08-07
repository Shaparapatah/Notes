package com.shaparapatah.notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ListNotesFragment extends Fragment {

    Note currentNotes;
    public static String KEY_NOTE = "note";
    boolean isLandScape;


    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notesList);

        for (int i = 0; i < notes.length; i++) {
            String name = notes[i];
            TextView textView = new TextView(getContext());
            textView.setText(name);
            textView.setTextSize(30);
            linearLayout.addView(textView);

            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNotes(finalI);
                }
            });
        }
        return view;
    }

    private void showNotes(int index) {
        currentNotes = new Note(index,
                (getResources().getStringArray(R.array.notesList)[index]));
        if (isLandScape) {
            showNotesLand();
        } else {
            showNotesPort();
        }
    }


    private void showNotesPort() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.listNotes_container, NotesFragment.newInstance(currentNotes))
                .addToBackStack("")
                .commit();

    }

    private void showNotesLand() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notesFragment_container, NotesFragment.newInstance(currentNotes))
                .commit();

    }


}