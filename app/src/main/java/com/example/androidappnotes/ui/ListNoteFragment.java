package com.example.androidappnotes.ui;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;
import android.content.Context;
import com.example.androidappnotes.NoteData;
import com.example.androidappnotes.NoteSource;
import com.example.androidappnotes.R;
import com.example.androidappnotes.observer.Publisher;

import static com.example.androidappnotes.ui.NoteFragment.CURRENT_DATA;
import static com.example.androidappnotes.ui.NoteFragment.CURRENT_NOTE;


public class ListNoteFragment extends Fragment {

    private boolean isLandscape;
    private NoteData[] notes;


    private com.example.androidappnotes.NoteData currentNote;
    private NoteSource data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private com.example.androidappnotes.ui.Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;

    public ListNoteFragment() {
    }

    public static ListNoteFragment newInstance() {
        return new ListNoteFragment();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        com.example.androidappnotes.MainActivity activity = (com.example.androidappnotes.MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (data == null) {
            data = new NoteSource(getResources()).init();
        }
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_list_of_notes, container, false);

        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            recyclerView = view.findViewById(R.id.notes_recycler_view);
            initRecyclerView(recyclerView, data);
            setHasOptionsMenu(true);
        }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    private void initRecyclerView(RecyclerView recyclerView, NoteSource data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        if (moveToLastPosition) {
            recyclerView.smoothScrollToPosition(data.size() - 1);
            moveToLastPosition = false;
        }

        adapter = new NotesAdapter(data, this);
        adapter.setOnItemClickListener((position, note) -> {
            navigation.addFragment(NoteFragment.newInstance(data.getNote(position)),
                    true);
            publisher.subscribe(note1 -> {
                data.changeNote(position, note1);
                adapter.notifyItemChanged(position);
            });
        });

        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration
                (requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull
                (ContextCompat.getDrawable(getContext(), R.drawable.separator)));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        outState.putParcelable(CURRENT_DATA, data);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelable(CURRENT_DATA);
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = data.getNote(0);
        }
    }

        private void showNote(NoteData currentNote) {
            if (isLandscape) {
                showLandNote(currentNote);
            } else {
                showPortNote(currentNote);
            }
        }

        private void showLandNote(NoteData currentNote) {
            NoteFragment fragment = NoteFragment.newInstance(currentNote);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.note_layout, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }

        private void showPortNote(NoteData currentNote) {
            NoteFragment fragment = NoteFragment.newInstance(currentNote);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("list_fragment");
            fragmentTransaction.replace(R.id.list_of_notes_fragment_container, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
