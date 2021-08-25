package com.shaparapatah.notes.data;

public class CardData {

    private  String listNote;
    private  String listTodo;


    public String getListNote() {
        return listNote;
    }

    public String getListTodo() {
        return listTodo;
    }

    public void setListNote(String listNote) {
        this.listNote = listNote;
    }

    public CardData(String listNote, String listTodo) {
        this.listNote = listNote;
        this.listTodo = listTodo;
    }
}