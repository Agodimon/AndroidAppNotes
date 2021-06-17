package com.example.androidappnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class NoteDescribeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note_describe);
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
    if (savedInstanceState==null){
        NoteDescribe details =new NoteDescribe();
        details.setArguments(getIntent().getExtras());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,details)
                .commit();
    }


    }
}