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
import java.util.Calendar;
import java.util.Objects;
import android.content.Context;
import com.example.androidappnotes.NoteData;
import com.example.androidappnotes.R;


public class ListNoteFragment extends Fragment {

    private boolean isLandscape;
    private NoteData[] notes;
    private NoteData currentNote;


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        Configuration configuration = getResources().getConfiguration();
        isLandscape=configuration.orientation==Configuration.ORIENTATION_LANDSCAPE;

    }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_list_of_notes, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            initNotes();
            RecyclerView recyclerView = view.findViewById(R.id.notes_recycler_view);
            initRecyclerView(recyclerView, notes);
        }

        private void initNotes() {
            notes = new NoteData[]{
                    new NoteData(getString(R.string.first_note_title),
                            getString(R.string.first_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(requireContext(), R.color.navajo_white)),
                    new NoteData(getString(R.string.second_note_title),
                            getString(R.string.second_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.hot_pink)),
                    new NoteData(getString(R.string.third_note_title),
                            getString(R.string.third_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.plum)),
                    new NoteData(getString(R.string.fourth_note_title),
                            getString(R.string.fourth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.powder_blue)),
                    new NoteData(getString(R.string.fifth_note_title),
                            getString(R.string.fifth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.yellow_green)),
                    new NoteData(getString(R.string.sixth_note_title),
                            getString(R.string.sixth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.peru)),
                    new NoteData(getString(R.string.seventh_note_title),
                            getString(R.string.seventh_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.pale_green)),
                    new NoteData(getString(R.string.eighth_note_title),
                            getString(R.string.eighth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.light_sky_blue)),
                    new NoteData(getString(R.string.ninth_note_title),
                            getString(R.string.ninth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.dark_salmon)),
                    new NoteData(getString(R.string.tenth_note_title),
                            getString(R.string.tenth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.olive)),
                    new NoteData(getString(R.string.eleventh_note_title),
                            getString(R.string.eleventh_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.medium_slate_blue)),
                    new NoteData(getString(R.string.twelfth_note_title),
                            getString(R.string.twelfth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.dark_turquoise)),
                    new NoteData(getString(R.string.tenth_note_title),
                            getString(R.string.tenth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.olive)),
                    new NoteData(getString(R.string.eleventh_note_title),
                            getString(R.string.eleventh_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.medium_slate_blue)),
                    new NoteData(getString(R.string.twelfth_note_title),
                            getString(R.string.twelfth_note_content),
                            Calendar.getInstance(),
                            ContextCompat.getColor(getContext(), R.color.dark_turquoise)),

            };
        }

        private void initRecyclerView(RecyclerView recyclerView, NoteData[] notes) {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            NotesAdapter adapter = new NotesAdapter(notes);
            adapter.setOnItemClickListener((position, note) -> {
                currentNote = note;
                showNote(currentNote);
            });
            recyclerView.setAdapter(adapter);
            DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                    LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.separator)));
            recyclerView.addItemDecoration(itemDecoration);
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            outState.putParcelable(NoteFragment.CURRENT_NOTE, currentNote);
            super.onSaveInstanceState(outState);
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
