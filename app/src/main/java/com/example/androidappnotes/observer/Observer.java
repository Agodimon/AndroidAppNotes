package com.example.androidappnotes.observer;

import com.example.androidappnotes.data.NoteData;

public interface Observer {
    void updateNoteData(NoteData noteData);
}
