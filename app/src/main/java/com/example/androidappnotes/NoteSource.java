package com.example.androidappnotes;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteSource implements Parcelable {



    private List<NoteData> notes;
    private Resources resources;
    private int counter=0;

    public NoteSource(Resources resources){
        this.resources=resources;
        notes=new ArrayList<>();
    }

    protected NoteSource(Parcel in) {
        notes = in.createTypedArrayList(NoteData.CREATOR);
        counter = in.readInt();
    }

    public static final Creator<NoteSource> CREATOR = new Creator<NoteSource>() {
        @Override
        public NoteSource createFromParcel(Parcel in) {
            return new NoteSource(in);
        }

        @Override
        public NoteSource[] newArray(int size) {
            return new NoteSource[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(notes);
        dest.writeInt(counter);
    }

    public NoteSource init() {
        NoteData[] notesArray = new NoteData[]{
                new NoteData(resources.getString(R.string.first_note_title),
                        resources.getString(R.string.first_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.second_note_title),
                        resources.getString(R.string.second_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.third_note_title),
                        resources.getString(R.string.third_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.fourth_note_title),
                        resources.getString(R.string.fourth_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.fifth_note_title),
                        resources.getString(R.string.fifth_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.sixth_note_title),
                        resources.getString(R.string.sixth_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.seventh_note_title),
                        resources.getString(R.string.seventh_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.eighth_note_title),
                        resources.getString(R.string.eighth_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.ninth_note_title),
                        resources.getString(R.string.ninth_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.tenth_note_title),
                        resources.getString(R.string.tenth_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.eleventh_note_title),
                        resources.getString(R.string.eleventh_note_content),
                        getDateOfCreation(), getColor()),
                new NoteData(resources.getString(R.string.twelfth_note_title),
                        resources.getString(R.string.twelfth_note_content),
                        getDateOfCreation(), getColor())
        };
        Collections.addAll(notes, notesArray);
        return this;
    }

    public NoteData getNote(int position) {
        return notes.get(position);
    }

    public int size() {
        return notes.size();
    }

    public void deleteNote(int position) {
        notes.remove(position);
    }

    public void changeNote(int position, NoteData note) {
        notes.set(position, note);
    }

    public void addNote(NoteData note) {
        notes.add(note);
    }

    public String getDateOfCreation() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy",
                Locale.getDefault());
        return String.format("Дата создания: %s", formatter.format(Calendar.getInstance().getTime()));
//        return String.format("Дата создания: %s", new Date().toString());
    }

    public int getColor() {
        int[] colors = resources.getIntArray(R.array.colors);
        int color = colors[counter];
        if (counter < colors.length - 1) {
            counter++;
        } else {
            counter = 0;
        }
        return color;
    }

}
