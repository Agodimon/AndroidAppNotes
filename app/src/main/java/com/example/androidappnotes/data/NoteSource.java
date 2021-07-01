package com.example.androidappnotes.data;

import android.os.Parcelable;

import com.example.androidappnotes.NoteData;

public interface NoteSource{
    NoteSource init(NoteSourceResponse noteSourceResponse);

    NoteData getNoteData(int position);
    int size();
    void deleteNoteData(int position);
    void updateNoteData(int position, NoteData noteData);
    void addCardNote(NoteData noteData);
    void clearCardNote();
}
