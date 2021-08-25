package com.shaparapatah.notes.data;

import android.content.res.Resources;

import com.shaparapatah.notes.R;

import java.util.ArrayList;
import java.util.List;

public class CardSourceImpl implements CardSource {

    private List<CardData> dataSource;
    private final Resources resources;


    @Override
    public int size() {
        return dataSource.size();
    }

    public CardSourceImpl(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);

    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);
    }

    @Override
    public void addCardData(CardData newCardData) {
        dataSource.add(newCardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();

    }

    public CardSourceImpl init() {
        dataSource = new ArrayList<>();
        String[] notesList = resources.getStringArray(R.array.notesList);
        String[] toDoList = resources.getStringArray(R.array.toDoList);

        for (int i = 0; i < notesList.length; i++) {

            dataSource.add(new CardData(notesList[i], toDoList[i]));
        }
        return this;
    }
}
