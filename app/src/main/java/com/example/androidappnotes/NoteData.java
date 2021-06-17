package com.example.androidappnotes;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteData implements Parcelable {
    private int noteName;
    private String noteDescribe;
    private String noteDateCreate;


    public NoteData(int noteName, String noteDescribe, String noteDateCreate) {
        this.noteName = noteName;
        this.noteDescribe = noteDescribe;
        this.noteDateCreate = noteDateCreate;
    }

    protected NoteData(Parcel in) {
        noteName = in.readInt();
        noteDescribe = in.readString();
        noteDateCreate = in.readString();
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noteName);
        dest.writeString(noteDescribe);
        dest.writeString(noteDateCreate);
    }
}
