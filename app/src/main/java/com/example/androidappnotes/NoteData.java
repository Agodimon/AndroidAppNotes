package com.example.androidappnotes;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class NoteData implements Parcelable {
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
    private String title;
    private String content;
    private Calendar creationDate;
    private int color;

    public NoteData(String title, String content, Calendar creationDate, int color) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.color = color;
    }

    protected NoteData(Parcel in) {
        title = in.readString();
        content = in.readString();
        creationDate = (Calendar) in.readSerializable();
        color = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeSerializable(creationDate);
        dest.writeInt(color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public int getColor() {
        return color;
    }
}

