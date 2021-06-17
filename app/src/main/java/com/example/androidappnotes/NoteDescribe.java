package com.example.androidappnotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NoteDescribe extends Fragment {


    public static final String ARG_INDEX = "index";

    private NoteData noteData;


    public static NoteDescribe newInstance(NoteData city) {
        NoteDescribe fragment = new NoteDescribe();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, city);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_describe, container, false);



        TextView cityName=view.findViewById(R.id.fragment_container);
        cityName.setText(noteData.describeContents());

        return view;


    }
}