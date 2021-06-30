package com.example.androidappnotes.observer;

import com.example.androidappnotes.NoteData;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private final List<Observer> observers;

    public Publisher(){
        observers=new ArrayList<>();
    }

    public void subscribe(Observer observer){
        observers.add(observer);
    }

    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }

    public void notifySingle(NoteData noteData){
        for (Observer observer:observers) {
            observer.updateNoteData(noteData);
            unsubscribe(observer);
        }
    }
}