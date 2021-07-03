package com.example.androidappnotes.data;

public interface NoteSourceInterf {

    NoteSourceInterf init(NoteSourceResponse noteSourceResponse);
        NoteData getNoteData(int position);
        int getSize();
        void deleteNoteData(int position);
        void updateNoteData(int position, NoteData noteData);
        void addNoteData(NoteData noteData);
        void clearNoteData();

    }
