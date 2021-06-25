package com.example.androidappnotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidappnotes.NoteData;
import com.example.androidappnotes.R;

import java.text.SimpleDateFormat;
import java.util.Locale;




public class NotesAdapter extends RecyclerView.Adapter<com.example.androidappnotes.ui.NotesAdapter.ViewHolder> {
    private final NoteData[] noteData;
    private MyClickListener myClickListener;

    public NotesAdapter(NoteData[] noteData) {
        this.noteData = noteData;
    }

    public void setOnItemClickListener(MyClickListener itemClickListener) {
        myClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public com.example.androidappnotes.ui.NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.androidappnotes.ui.NotesAdapter.ViewHolder holder, int position) {
        holder.getItemLayout().setBackgroundColor(noteData[position].getColor());
        holder.getTitleTextView().setText(noteData[position].getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy",
                Locale.getDefault());
        holder.getDateTextView().setText(formatter.format(noteData[position].getCreationDate().getTime()));
    }

    @Override
    public int getItemCount() {
        return noteData.length;
    }

    public interface MyClickListener {
        void onItemClick(int position, NoteData noteData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final LinearLayout itemLayout;
        private final TextView titleTextView;
        private final TextView dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            itemLayout = itemView.findViewById(R.id.element_of_recycler_view);
            titleTextView = itemView.findViewById(R.id.first_tv_of_item);
            dateTextView = itemView.findViewById(R.id.second_tv_of_item);
            itemLayout.setOnClickListener(v -> {
                int position = getAdapterPosition();
                myClickListener.onItemClick(position, noteData[position]);
            });
        }

        public LinearLayout getItemLayout() {
            return itemLayout;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getDateTextView() {
            return dateTextView;
        }

        public CardView getCardView() {
            return cardView;
        }
    }
}
