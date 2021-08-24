package com.shaparapatah.notes.ui;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaparapatah.notes.R;


public class NotesFragment extends Fragment {

    public static String ARG_NOTE = "note";
    private Note note;


    public static NotesFragment newInstance(Note note) {
        NotesFragment fragment = new NotesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.note = getArguments().getParcelable(ARG_NOTE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        AppCompatEditText editText = view.findViewById(R.id.editTextView);
        AppCompatTextView textView = view.findViewById(R.id.textView);
        textView.setText(this.note.getName());

        TypedArray typedArray = getResources().obtainTypedArray(R.array.toDoList);
        editText.setText(typedArray.getResourceId(this.note.getToDo(), -1));
        return view;
    }
}