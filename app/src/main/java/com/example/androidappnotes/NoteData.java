package com.example.androidappnotes;


import android.os.Parcel;
import android.os.Parcelable;

public class NoteData implements Parcelable {
    private String title;
    private String content;
    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public com.example.androidappnotes.NoteData createFromParcel(Parcel in) {
            return new com.example.androidappnotes.NoteData(in);
        }

        @Override
        public com.example.androidappnotes.NoteData[] newArray(int size) {
            return new com.example.androidappnotes.NoteData[size];
        }
    };
    private int color;
    private String creationDate;

    public NoteData(String title, String content, String creationDate, int color) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.color = color;
    }

    protected NoteData(Parcel in) {
        title = in.readString();
        content = in.readString();
        creationDate = in.readString();
        color = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(creationDate);
        dest.writeInt(color);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

