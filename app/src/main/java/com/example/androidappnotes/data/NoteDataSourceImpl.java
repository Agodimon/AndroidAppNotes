package com.example.androidappnotes.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.androidappnotes.R;

public class NoteDataSourceImpl implements com.example.androidappnotes.data.NoteSource {
    private List<com.example.androidappnotes.data.NoteData> noteDataSource;
    private Resources resources;


    public NoteDataSourceImpl(Resources resources) {
        this.resources = resources;
        noteDataSource = new ArrayList<>(20);
    }


    public NoteDataSourceImpl init(com.example.androidappnotes.data.NoteSourceResponse noteSourceResponse){
        String[] titles = resources.getStringArray(R.array.notes);
        String[] descriptions = resources.getStringArray(R.array.descriptions);

        for(int i = 0; i < descriptions.length; i++){
            noteDataSource.add(new com.example.androidappnotes.data.NoteData(titles[i], descriptions[i], Calendar.getInstance().getTime()));
        }

        if (noteSourceResponse != null) {
            noteSourceResponse.initialized(this);
        }

        return this;
    }

    @Override
    public com.example.androidappnotes.data.NoteData getNoteData(int position) {
        return noteDataSource.get(position);
    }

    @Override
    public int getSize() {
        return noteDataSource.size();
    }

    @Override
    public void deleteNoteData(int position) {
        noteDataSource.remove(position);
    }

    @Override
    public void updateNoteData(int position, com.example.androidappnotes.data.NoteData noteData) {
        noteDataSource.set(position, noteData);
    }

    @Override
    public void addNoteData(com.example.androidappnotes.data.NoteData noteData) {
        noteDataSource.add(noteData);
    }



    @Override
    public void clearNoteData() {
        noteDataSource.clear();
    }
}
