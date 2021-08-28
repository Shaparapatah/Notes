package com.shaparapatah.notes.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shaparapatah.notes.MainActivity;
import com.shaparapatah.notes.Navigation;
import com.shaparapatah.notes.R;
import com.shaparapatah.notes.data.CardData;
import com.shaparapatah.notes.data.CardSource;
import com.shaparapatah.notes.data.CardSourceImpl;
import com.shaparapatah.notes.data.MyOnClickListener;
import com.shaparapatah.notes.observer.Observer;
import com.shaparapatah.notes.observer.Publisher;


public class ListNotesFragment extends Fragment {

    Note currentNotes;
    public static String KEY_NOTE = "note";
    boolean isLandScape;

    private RecyclerView recyclerView;
    private CardSource data;
    private NoteAdapter noteAdapter;

    private Navigation navigation;
    private Publisher publisher;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }


    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new CardSourceImpl(getResources()).init();
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
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource data) {

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);




        noteAdapter = new NoteAdapter(data, this);
        recyclerView.setAdapter(noteAdapter);
        defaultAnimation(recyclerView);

        noteAdapter.setOnMyClickListener(new MyOnClickListener() {
            @Override
            public void onMyClick(View view, int position) {
                Toast.makeText(getContext(), "Тяжёлая обработка для " + position, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void defaultAnimation(RecyclerView recyclerView) {
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(2000);
        defaultItemAnimator.setRemoveDuration(2000);
        defaultItemAnimator.setChangeDuration(2000);
        recyclerView.setItemAnimator(defaultItemAnimator);
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
                navigation.addFragment(CardUpdateFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(CardData cardData) {
                        data.addCardData(cardData);
                        noteAdapter.notifyItemInserted(data.size() - 1);
                    }
                });
                return true;
            case R.id.action_clear:
                data.clearCardData();
                noteAdapter.notifyDataSetChanged();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = noteAdapter.getMenuContextClickPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
             /*   data.getCardData(position).setListNote("Обновили" + position);
                noteAdapter.notifyItemChanged(position); */

                navigation.addFragment(CardUpdateFragment.newInstance(data.getCardData(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(CardData cardData) {
                        data.updateCardData(position, cardData);
                        noteAdapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                noteAdapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}