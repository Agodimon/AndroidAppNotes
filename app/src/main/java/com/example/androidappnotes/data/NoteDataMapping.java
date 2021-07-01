package com.example.androidappnotes.data;

import com.example.androidappnotes.NoteData;
import com.example.androidappnotes.ui.NoteFragment;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {
    public static class Fields {
        public final static String TITLE = "title";
        public final static String CONTENT = "content";
        public final static String CREATION_DATE = "creationDate";
        public final static int color = 2;
    }

    public static Map<String, Object> toDocument(NoteData noteData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, noteData.getTitle());
        answer.put(Fields.CONTENT, noteData.getContent());
        answer.put(Fields.CREATION_DATE, noteData.getCreationDate());
        answer.put(String.valueOf(Fields.color), noteData.getColor());
        return answer;
    }

    public static NoteData toNoteData(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp) doc.get(Fields.CREATION_DATE);
        NoteData answer = new NoteData((String) doc.get(Fields.TITLE),
                (String)doc.get(Fields.CONTENT),
                (String)doc.get(Fields.CREATION_DATE),
                NoteFragment.newInstance().getColor()
        );
        answer.setId(id);
        return answer;
    }


}
