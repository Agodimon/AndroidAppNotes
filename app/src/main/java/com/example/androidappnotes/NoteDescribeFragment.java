package com.example.androidappnotes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


public class NoteDescribeFragment extends Fragment {

    public static final String KEY_ARG = "key_arg";

    public static NoteDescribeFragment newInstance(NoteData city) {
        NoteDescribeFragment fragment = new NoteDescribeFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_describe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        TextView cityName= view.findViewById(R.id.tv_name);
        TextView describe= view.findViewById(R.id.tv_describe);
        TextView date= view.findViewById(R.id.tv_date);

        if (getArguments() != null) {
            NoteData noteData = getArguments().getParcelable(KEY_ARG);

            if(noteData != null) {
                cityName.setText(noteData.getName());
                describe.setText(noteData.getDescribe());
                date.setText(noteData.getDate().toString());
            }
        }

        DatePicker datePicker = view.findViewById(R.id.dp_date);
        datePicker.getMonth();
    }
}