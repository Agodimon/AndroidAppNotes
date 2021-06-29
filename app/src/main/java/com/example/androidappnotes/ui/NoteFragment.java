package com.example.androidappnotes.ui;
import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.androidappnotes.MainActivity;
import com.example.androidappnotes.NoteData;
import com.example.androidappnotes.R;
import com.example.androidappnotes.observer.Publisher;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class NoteFragment extends Fragment {

    public static final String CURRENT_NOTE = "currentNote";
    public static final String CURRENT_DATA = "currentData";
    private NoteData note;
    private Publisher publisher;


    private EditText titleText;
    private EditText contentText;
    private TextView dateOfCreationText;
    private int color;
    private String dateOfCreation;
    private boolean isNewNote = false;

    public static NoteFragment newInstance(NoteData note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(CURRENT_NOTE);
        }
        if (note == null) {
            isNewNote = true;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //только тут
        initView(view);
        if (note != null) {
            color = note.getColor();
            dateOfCreation = note.getCreationDate();
            populateView(view);
        }
        if (isNewNote) {
            color = getColor();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy",
                    Locale.getDefault());
            dateOfCreation = String.format("Дата создания: %s", formatter.format(Calendar.getInstance().getTime()));
//            dateOfCreation = String.format("Дата создания: %s", new Date().toString());
            populateView(view);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        note = collectNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(note);
    }

    private NoteData collectNote() {
        String title = Objects.requireNonNull(this.titleText.getText()).toString();
        String content = Objects.requireNonNull(this.contentText.getText()).toString();
        if (isNewNote) {
            isNewNote = false;
        }
        return new NoteData(title, content, dateOfCreation, color);
    }

    private void initView(View view) {
        titleText = view.findViewById(R.id.note_title);
        contentText = view.findViewById(R.id.note_content);
        dateOfCreationText = view.findViewById(R.id.note_date_of_creation);
    }

    private void populateView(View view) {
        if (isNewNote) {
            dateOfCreationText.setText(dateOfCreation);
            view.setBackgroundColor(color);
        } else {
            dateOfCreationText.setText(note.getCreationDate());
            titleText.setText(note.getTitle());
            contentText.setText(note.getContent());
            view.setBackgroundColor(note.getColor());
        }
    }

    public int getColor() {
        int[] colors = getResources().getIntArray(R.array.colors);
        Random random = new Random();
        return colors[random.nextInt(colors.length)];
    }
}
