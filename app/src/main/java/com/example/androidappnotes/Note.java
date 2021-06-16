package com.example.androidappnotes;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String noteName;
    private String noteDescribe;
    private String noteDataCreate;

    public Note(String noteName, String noteDescribe, String noteDataCreate) {
        this.noteName = noteName;
        this.noteDescribe = noteDescribe;
        this.noteDataCreate = noteDataCreate;
    }

    protected Note(Parcel in) {
        noteName = in.readString();
        noteDescribe = in.readString();
        noteDataCreate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName);
        dest.writeString(noteDescribe);
        dest.writeString(noteDataCreate);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDescribe() {
        return noteDescribe;
    }

    public String getNoteDataCreate() {
        return noteDataCreate;
    }
}
