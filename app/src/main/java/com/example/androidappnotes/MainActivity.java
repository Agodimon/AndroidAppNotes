package com.example.androidappnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getOrientation(savedInstanceState);
        readSettings();
        initView();
    }

    private void getOrientation(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fl_notes_container, new NotesFragment());
            fragmentTransaction.commit();
        } else {

        }
    }
    private void initView() {
        initButtonMain();
        initButtonFavorite();
        initButtonSettings();
        initButtonBack();
    }
    private void initButtonBack() {
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (Settings.isBackAsRemove){
                    Fragment fragment = getVisibleFragment(fragmentManager);
                    if (fragment != null) {
                        fragmentManager.beginTransaction().remove(fragment).commit();
                    }
                } else {
                    fragmentManager.popBackStack();
                }
            }
        });
    }

    private void initButtonSettings() {
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new SettingsFragment());
            }
        });
    }

    private void initButtonFavorite() {
        Button buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new FavoriteFragment());
            }
        });
    }
    private void initButtonMain() {
        Button buttonMain = findViewById(R.id.buttonMain);
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new MainFragment());
            }
        });
    }

    private Fragment getVisibleFragment(FragmentManager fragmentManager){
        List<Fragment> fragments = fragmentManager.getFragments();
        int countFragments = fragments.size();
        for(int i = countFragments - 1; i >= 0; i--){
            Fragment fragment = fragments.get(i);
            if(fragment.isVisible())
                return fragment;
        }
        return null;
    }

    private void addFragment(Fragment fragment){
//Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
// Открыть транзакцию
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
// Удалить видимый фрагмент
        if (Settings.isDeleteBeforeAdd){
            Fragment fragmentToRemove = getVisibleFragment(fragmentManager);
            if (fragmentToRemove != null) {
                fragmentTransaction.remove(fragmentToRemove);
            }
        }
        // Добавить фрагмент
        if (Settings.isAddFragment) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }
// Добавить транзакцию в бэкстек
        if (Settings.isBackStack){fragmentTransaction.addToBackStack(null);
        }
// Закрыть транзакцию
        fragmentTransaction.commit();
    }
    // Чтение настроек
    private void readSettings(){
// Специальный класс для хранения настроек
        SharedPreferences sharedPref =
                getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
// Считываем значения настроек
        Settings.isBackStack =
                sharedPref.getBoolean(Settings.IS_BACK_STACK_USED, false);
        Settings.isAddFragment =
                sharedPref.getBoolean(Settings.IS_ADD_FRAGMENT_USED, true);
        Settings.isBackAsRemove =
                sharedPref.getBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, true);
        Settings.isDeleteBeforeAdd =
                sharedPref.getBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, false);
    }

}