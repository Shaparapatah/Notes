package com.shaparapatah.notes.data;

public class CardData {

    private final String listNote;
    private final String listTodo;


    public String getListNote() {
        return listNote;
    }

    public String getListTodo() {
        return listTodo;
    }


    public CardData(String listNote, String listTodo) {
        this.listNote = listNote;
        this.listTodo = listTodo;
    }
}