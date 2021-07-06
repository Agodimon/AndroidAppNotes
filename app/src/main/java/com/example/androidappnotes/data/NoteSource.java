package com.example.androidappnotes.data;

public interface NoteSource {

    NoteSource init(com.example.androidappnotes.data.NoteSourceResponse noteSourceResponse);

    com.example.androidappnotes.data.NoteData getNoteData(int position);

    int getSize();

    void deleteNoteData(int position);

    void updateNoteData(int position, com.example.androidappnotes.data.NoteData noteData);

    void addNoteData(com.example.androidappnotes.data.NoteData noteData);

    void clearNoteData();

}
