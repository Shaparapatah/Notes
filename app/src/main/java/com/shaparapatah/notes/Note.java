package com.shaparapatah.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String name;
    private String toDo;


    protected Note(Parcel in) {
        name = in.readString();
        toDo = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public Note(String name, String toDo) {
        this.name = name;
        this.toDo = toDo;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(toDo);
    }
}
