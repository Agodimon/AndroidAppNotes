package com.example.androidappnotes.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {


    public static class Fields {

        public final static String TITLE = "title";
        public final static String  DESCRIPTION = "description";
        public final static String DATE = "date";
    }



        public static Map<String, Object> toDocument(com.example.androidappnotes.data.NoteData noteData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, noteData.getTitle());
        answer.put(Fields.DESCRIPTION, noteData.getDescription());
        answer.put(Fields.DATE, noteData.getDate());
        return answer;


    }

       public static com.example.androidappnotes.data.NoteData toNoteData(String id, Map<String, Object> doc) {
        Timestamp timestamp = (Timestamp)doc.get(Fields.DATE);
           com.example.androidappnotes.data.NoteData answer = new com.example.androidappnotes.data.NoteData((String)doc.get(Fields.TITLE),
                (String)doc.get(Fields.DESCRIPTION),
                timestamp.toDate());
        answer.setId(id);
        return answer;

       }



}
