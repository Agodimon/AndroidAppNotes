package com.example.androidappnotes;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ListNoteFragment extends Fragment {

    private boolean isLandscape;
    private NoteData[] notes;
    private NoteData currentNote;


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        Configuration configuration = getResources().getConfiguration();
        isLandscape=configuration.orientation==Configuration.ORIENTATION_LANDSCAPE;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_of_notes, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initList(view);


    }



    private void initList(View view) {
        notes = new NoteData[]{
                new NoteData(getString(R.string.first_note_title), getString(R.string.first_note_content), Calendar.getInstance()),
                new NoteData(getString(R.string.second_note_title), getString(R.string.second_note_content), Calendar.getInstance()),
                new NoteData(getString(R.string.third_note_title), getString(R.string.third_note_content), Calendar.getInstance()),
        };

        for (NoteData note : notes) {
            Context context = getContext();
            if (context != null) {
                LinearLayout linearView = (LinearLayout) view;
                TextView firstTextView = new TextView(context);
                TextView secondTextView = new TextView(context);
                firstTextView.setText(note.getTitle());
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
                secondTextView.setText(formatter.format(note.getCreationDate().getTime()));
                linearView.addView(firstTextView);
                firstTextView.setTextColor(Color.MAGENTA);
                secondTextView.setTextColor(Color.MAGENTA);
                linearView.addView(secondTextView);
                firstTextView.setPadding(0, 22, 0, 0);
                firstTextView.setOnClickListener(v -> initCurrentNote(note));
                secondTextView.setOnClickListener(v -> initCurrentNote(note));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NoteFragment.CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    private void initCurrentNote(NoteData note) {
        currentNote = note;
        showNote(note);
    }



    private void showNote(NoteData currentNote) {
        if (isLandscape) {
            showLandNote(currentNote);
        } else {
            showPortNote(currentNote);
        }
    }

    private void showLandNote(NoteData currentNote) {
        NoteFragment fragment = NoteFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    private void showPortNote(NoteData currentNote) {
        NoteFragment fragment = NoteFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("list_fragment");
        fragmentTransaction.replace(R.id.list_of_notes_fragment_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
