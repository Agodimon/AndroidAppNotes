package com.example.androidappnotes.ui;


import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import com.example.androidappnotes.MainActivity;
import com.example.androidappnotes.Navigation;
import com.example.androidappnotes.NoteFragment;
import com.example.androidappnotes.R;
import com.example.androidappnotes.data.NoteData;
import com.example.androidappnotes.data.NoteSource;
import com.example.androidappnotes.data.NoteSourceFirebaseImpl;
import com.example.androidappnotes.data.NoteSourceResponse;
import com.example.androidappnotes.observer.Observer;
import com.example.androidappnotes.observer.Publisher;

public class NotesJournalFragment extends Fragment {

    private static final int MY_DEFAULT_DURATION = 1000;
    private NoteSource data;
    private NotesJournalAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;



    public static  NotesJournalFragment newInstance() {
        return new NotesJournalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_journal_fragment, container, false);
        setHasOptionsMenu(true);
        initRecyclerView(view);
        data = new NoteSourceFirebaseImpl().init(new NoteSourceResponse() {
            @Override
            public void initialized(NoteSource noteData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;
    }



    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }


    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contex_menu_clear, menu);
    }


    @Override
    public void onCreateContextMenu(@NonNull @NotNull ContextMenu menu, @NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.contnex_menu_add_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }


    private boolean onItemSelected(int menuItem) {
        switch (menuItem) {
            case R.id.action_add:
                navigation.addFragment(NoteFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(NoteData noteData) {
                        data.addNoteData(noteData);
                        adapter.notifyItemInserted(0);
                        recyclerView.scrollToPosition(0);
                    }
                });
                return true;
            case R.id.action_update:
                int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(NoteFragment.newInstance(data.getNoteData(updatePosition)), true);
                publisher.subscribe(noteData -> {
                    data.updateNoteData(updatePosition, noteData);
                    adapter.notifyItemChanged(updatePosition);
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteNoteData(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.action_clear:
                data.clearNoteData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }


    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.notes_recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesJournalAdapter(this);
        adapter.setOnItemClickListener((view1, position) -> {
            navigation.addFragment(NoteFragment.newInstance(data.getNoteData(position)),
                    true);
            publisher.subscribe(note1 -> {
              adapter.notifyItemChanged(position);
            });
        });

        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setChangeDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

    }

}





