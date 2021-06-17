package com.example.androidappnotes;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {
    private boolean isLandscape;
    public static final String CURRENT_NOTE = "CurrentNote";
    private NoteData currentNote; // Текущая позиция (выбранный город)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }
    // вызывается после создания макета фрагмента, здесь мы проинициализируем список

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // создаём список городов на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout)view;
        String[] notes = getResources().getStringArray(R.array.notes);
// В этом цикле создаём элемент TextView,
// заполняем его значениями,// и добавляем на экран.
//// Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < notes.length; i++) {

            String note = notes[i];

            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int index =i;
            tv.setOnClickListener(v->{
                String date="__.__.__";
                currentNote =new NoteData(index,note,date);

                showPortDescribeNote(currentNote);
            });
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState!=null){
            currentNote =savedInstanceState.getParcelable(CURRENT_NOTE);
        }else {
            currentNote =new NoteData(0,getResources().getStringArray(R.array.notes)[0],"__.__.__");
        }

        if (isLandscape) {
            showDescribeNote(currentNote);
        }

    }

    @Override
    public void onAttach(@NonNull  Context context) {
        super.onAttach(context);
        // Определение, можно ли будет расположить рядом герб в другом
//        фрагменте
        isLandscape = getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
// Если можно нарисовать рядом герб, то сделаем это


    }

    private void showDescribeNote(NoteData currentCity) {
        if (isLandscape){
            showLandDescribe(currentCity);
        }else {
            showPortDescribeNote(currentCity);
        }
    }

    private void showLandDescribe(NoteData currentCity) {
NoteDescribe detail= NoteDescribe.newInstance(currentCity);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.describeNote,detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }


    private void showPortDescribeNote(NoteData noteData){
    Intent intent=new Intent();
    intent.setClass(getActivity(), NoteDescribe.class);
    intent.putExtra(NoteDescribe.ARG_INDEX, noteData);
    startActivity(intent);
}

}