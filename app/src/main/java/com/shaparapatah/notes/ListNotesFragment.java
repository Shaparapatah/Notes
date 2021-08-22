package com.shaparapatah.notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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

        if (savedInstanceState != null) {
            currentNotes = savedInstanceState.getParcelable(KEY_NOTE);
        }


        if (isLandScape)
            if (currentNotes != null) {
                showNotes(currentNotes.getToDo());
            } else {
                showNotes(0);
            }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_NOTE, currentNotes);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        String[] notes = getResources().getStringArray(R.array.notesList);

        initRecycler(view, notes);


        //   LinearLayout linearLayout = (LinearLayout) view;
        //  createTextViewList(linearLayout);
        return view;
    }

    private void initRecycler(View view, String[] notes) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NoteAdapter noteAdapter = new NoteAdapter(notes);
        noteAdapter.setOnMyClickListener(new MyOnClickListener() {
            @Override
            public void onMyClick(View view, int position) {
                Toast.makeText(getContext(), "Тяжёлая обработка для " + position, Toast.LENGTH_SHORT).show();

            }
        });
        recyclerView.setAdapter(noteAdapter);
    }

   /* private void createTextViewList(LinearLayout linearLayout) {
        String[] notes = getResources().getStringArray(R.array.notesList);

        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < notes.length; i++) {
            String name = notes[i];
            TextView textView = (TextView) layoutInflater.inflate(R.layout.item, linearLayout, false);
             //TextView textView = new TextView(getContext());
             textView.setText(name);
           //   textView.setTextSize(30);
            linearLayout.addView(textView);

            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNotes(finalI);
                }
            });
        }
    } */

    private void showNotes(int index) {
        currentNotes = new Note((getResources().getStringArray(R.array.notesList)[index]),
                index);
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