package com.shaparapatah.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shaparapatah.notes.data.CardData;
import com.shaparapatah.notes.data.MyOnClickListener;
import com.shaparapatah.notes.R;
import com.shaparapatah.notes.data.CardSource;
import com.shaparapatah.notes.data.CardSourceImpl;

import javax.sql.DataSource;


public class ListNotesFragment extends Fragment {

    Note currentNotes;
    public static String KEY_NOTE = "note";
    boolean isLandScape;
    private RecyclerView recyclerView;
    private CardSource data;
    private NoteAdapter noteAdapter;


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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        data = new CardSourceImpl(getResources()).init();
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource data) {

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        noteAdapter = new NoteAdapter(data);
        noteAdapter.setOnMyClickListener(new MyOnClickListener() {
            @Override
            public void onMyClick(View view, int position) {
                Toast.makeText(getContext(), "Тяжёлая обработка для " + position, Toast.LENGTH_SHORT).show();

            }
        });
        recyclerView.setAdapter(noteAdapter);
    }


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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                data.addCardData(new CardData("Новая " + data.size(),
                        "Описание" + data.size()));
                noteAdapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                noteAdapter.notifyDataSetChanged();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}