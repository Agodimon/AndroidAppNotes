package com.example.androidappnotes;

import android.content.Context;
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

import java.util.Date;

public class NotesFragment extends Fragment {
    private boolean isLandscape;
    public static final String CURRENT_NOTE = "CurrentNote";
    private NoteData currentNote; // Текущая позиция (выбранная заметка)

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

    // создаём список записок на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
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
            final int index = i;
            tv.setOnClickListener(v -> {
                currentNote = new NoteData(note, note, new Date());

                showDescribeNote(currentNote);
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
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new NoteData("No name", getResources().getStringArray(R.array.notes)[0], new Date());
        }

        if (isLandscape) {
            showDescribeNote(currentNote);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Определение, можно ли будет расположить рядом описание заметки (ИМЯ,ОПИСАНИЕ,ДАТА) в другом
//        фрагменте
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
// Если можно ВСТАВИТЬ рядом ТЕКС ОПИСАНИЕ, то сделаем это


    }

    private void showDescribeNote(NoteData currentCity) {
        if (isLandscape) {
            showLandDescribe(currentCity);
        } else {
            showPortDescribeNote(currentCity);
        }
    }

    private void showLandDescribe(NoteData currentCity) {
        NoteDescribeFragment detail = NoteDescribeFragment.newInstance(currentCity);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_describe_note_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }


    private void showPortDescribeNote(NoteData noteData) {
        NoteDescribeFragment detail = NoteDescribeFragment.newInstance(noteData);

        String backStateName = this.getClass().getName();

        FragmentManager manager = requireActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fl_notes_container, detail);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

}