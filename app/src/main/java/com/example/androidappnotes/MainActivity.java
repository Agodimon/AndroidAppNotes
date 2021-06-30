package com.example.androidappnotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidappnotes.observer.Publisher;
import com.example.androidappnotes.ui.ListNoteFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private com.example.androidappnotes.ui.Navigation navigation;
    private Publisher publisher = new Publisher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new com.example.androidappnotes.ui.Navigation(getSupportFragmentManager());
        initToolbar();
        getNavigation().addFragment(ListNoteFragment.newInstance(), false);
    }



//        @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
//        MenuItem search = menu.findItem(R.id.menu_search);
//        SearchView searchView = (SearchView) search.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Snackbar.make(findViewById(R.id.drawer_layout), query, Snackbar.LENGTH_SHORT).show();
//                return true;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//        });
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch(id){
//            case R.id.menu_sort:
//                Toast.makeText(this, getResources().getString(R.string.menu_sort), Toast.LENGTH_LONG).show();
//                return true;
//            case R.id.menu_add_photo:
//                Toast.makeText(this, R.string.menu_add_photo, Toast.LENGTH_LONG).show();
//                return true;
//            case R.id.menu_send:
//                Toast.makeText(this, getResources().getString(R.string.menu_send), Toast.LENGTH_LONG).show();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private boolean checkLandScapeOrientation() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public com.example.androidappnotes.ui.Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

}