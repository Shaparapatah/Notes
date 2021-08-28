package com.shaparapatah.notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.shaparapatah.notes.MainActivity;
import com.shaparapatah.notes.R;
import com.shaparapatah.notes.data.CardData;
import com.shaparapatah.notes.observer.Publisher;

import java.util.Calendar;
import java.util.Date;

public class CardUpdateFragment extends Fragment {

    private static final String ARG_CARD_DATA = "Param_CardData";
    private CardData cardData;
    private Publisher publisher;
    private TextInputEditText listNote;
    private TextInputEditText listToDo;
    private DatePicker datePicker;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher = null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_card, container, false);
        initView(view);
        if (cardData != null) {
            populateView();
        }
        return view;
    }


    private void initView(View view) {
        listNote = view.findViewById(R.id.inputTitle);
        listToDo = view.findViewById(R.id.inputDescription);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView() {
        listNote.setText(cardData.getListNote());
        listToDo.setText(cardData.getListTodo());
        initDatePicker(cardData.getDate());
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }


    public static CardUpdateFragment newInstance(CardData cardData) {
        CardUpdateFragment fragment = new CardUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    public static CardUpdateFragment newInstance() {
        CardUpdateFragment fragment = new CardUpdateFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }


    private CardData collectCardData() {
        String listNote = this.listNote.getText().toString();
        String listToDo = this.listToDo.getText().toString();
        Date date = getDateFromDatePicker();

        if (cardData != null) {
            cardData.setListNote(listNote);
            cardData.setListTodo(listToDo);
            cardData.setDate(date);
            return cardData;
        } else {
            return new CardData(listNote, listToDo, date);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifyTask(cardData);
    }


}
