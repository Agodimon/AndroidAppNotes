package com.example.androidappnotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CitiesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cities, container, false);
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
        String[] cities = getResources().getStringArray(R.array.cities);
// В этом цикле создаём элемент TextView,
// заполняем его значениями,// и добавляем на экран.
//// Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            TextView tv = new TextView(getContext());
            tv.setText(city);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int index =i;
            tv.setOnClickListener(v->{
                showPortCoatOfArms(index);
            });
        }
    }
private void showPortCoatOfArms(int index){
    Intent intent=new Intent();
    intent.setClass(getActivity(), CoatOfArmsFragment.class);
    intent.putExtra(CoatOfArmsFragment.ARG_INDEX, index);
    startActivity(intent);
}

}