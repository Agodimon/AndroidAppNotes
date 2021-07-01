package com.example.androidappnotes.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidappnotes.NoteData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotesSourceFirebaseImpl implements NoteSource {

    private static final String NOTES_COLLECTION = "notes";
    private static final String TAG = "[FirebaseImpl]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    private List<NoteData> notesData = new ArrayList<>();

    @Override
    public NoteSource init(NoteSourceResponse noteSourceResponse) {
        collection.orderBy(NoteDataMapping.Fields.CREATION_DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            notesData = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                NoteData noteData = NoteDataMapping.toNoteData(id, doc);
                                notesData.add(noteData);
                            }
                            Log.d(TAG, "success" + notesData.size() + "qnt");
                            noteSourceResponse.initialized(NotesSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with", e);
                    }
                });
        return this;
    }

    @Override
    public NoteData getNoteData(int position) {

        return notesData.get(position);
    }

    @Override
    public int size() {
        if (notesData == null) {
            return 0;
        }
        return notesData.size();
    }

    @Override
    public void deleteNoteData(int position) {
        collection.document(notesData.get(position).getId()).delete();
        notesData.remove(position);
    }

    @Override
    public void updateNoteData(int position, NoteData noteData) {
        String id = noteData.getId();
        collection.document(id).set(NoteDataMapping.toDocument(noteData));
    }

    @Override
    public void addCardNote(NoteData noteData) {
        collection.add(NoteDataMapping.toDocument(noteData))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        noteData.setId(documentReference.getId());
                    }
                });
    }

    @Override
    public void clearCardNote() {
        for (NoteData noteData : notesData) {
            collection.document(noteData.getId()).delete();
        }
        notesData = new ArrayList<NoteData>();
    }

}
