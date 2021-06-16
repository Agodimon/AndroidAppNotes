package com.example.androidappnotes;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CoatOfArmsFragment extends Fragment {


    public static final String ARG_INDEX = "index";

    private int index;


    public static CoatOfArmsFragment newInstance(Integer index) {
        CoatOfArmsFragment fragment = new CoatOfArmsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coat_of_arms, container, false);
        AppCompatTextView textView = view.findViewById(R.id.coat_of_arms);
        TypedArray texts=getResources().obtainTypedArray(R.array.coat_of_arms_notes);
        int text=texts.getResourceId(index,-1);
        textView.setText(text);
        return view;
    }
}