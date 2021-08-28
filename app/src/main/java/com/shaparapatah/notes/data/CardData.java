package com.shaparapatah.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CardData implements Parcelable {

    private String listNote;
    private String listTodo;
    private Date date;

    public void setListTodo(String listTodo) {
        this.listTodo = listTodo;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    protected CardData(Parcel in) {
        listNote = in.readString();
        listTodo = in.readString();
    }

    public Date getDate() {
        return date;
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };



    public String getListNote() {
        return listNote;
    }

    public String getListTodo() {
        return listTodo;
    }

    public void setListNote(String listNote) {
        this.listNote = listNote;
    }

    public CardData(String listNote, String listTodo, Date date) {
        this.listNote = listNote;
        this.listTodo = listTodo;
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(listNote);
        dest.writeString(listTodo);
    }
}