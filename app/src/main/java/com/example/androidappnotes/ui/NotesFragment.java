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


import com.example.androidappnotes.MainActivity;
import com.example.androidappnotes.Navigation;
import com.example.androidappnotes.NoteFragment;
import com.example.androidappnotes.R;
import com.example.androidappnotes.data.NoteData;
import com.example.androidappnotes.data.NoteSourceInterf;
import com.example.androidappnotes.data.NotesSourceInterfFirebaseImpl;
import com.example.androidappnotes.data.NoteSourceResponse;
import com.example.androidappnotes.observer.Observer;
import com.example.androidappnotes.observer.Publisher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.UUID;

public class NotesFragment extends Fragment {

    private static final int MY_DEFAULT_DURATION = 1000;
    private NoteSourceInterf data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private FloatingActionButton fab;


    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
        publisher.subscribe(new Observer() {
            @Override
            public void updateNoteData(NoteData noteData) {
                data.addNoteData(noteData);
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        setHasOptionsMenu(true);
        initRecyclerView(view);
        data = new NotesSourceInterfFirebaseImpl().init(new NoteSourceResponse() {
            @Override
            public void initialized(NoteSourceInterf noteData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab_add_note);
        fab.setOnClickListener(v -> {
            NoteData note = new NoteData(String.valueOf(System.currentTimeMillis()), "354354564563456", new Date());
            note.setId(UUID.randomUUID().toString());
            publisher.notifySingle(note);
        });
    }


    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.context_menu, menu);
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_2, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }


    private boolean onItemSelected(int menuItem) {
        switch (menuItem) {
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

        adapter = new NotesAdapter(this);
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
