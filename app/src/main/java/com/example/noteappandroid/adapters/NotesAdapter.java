package com.example.noteappandroid.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteappandroid.R;
import com.example.noteappandroid.entities.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<Note> notes;

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_note,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote(notes.get(position));

    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText, subtitleText, dateTimeText;
        private LinearLayout llContainer;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.title);
            subtitleText = itemView.findViewById(R.id.subTitle);
            dateTimeText = itemView.findViewById(R.id.dateTimeText);
            llContainer = itemView.findViewById(R.id.llContainer);
        }

        private void setNote(Note note) {
            titleText.setText(note.getTitle());
            if (note.getSubTitle().trim().isEmpty()) {
                subtitleText.setVisibility(View.GONE);
            } else {
                subtitleText.setText(note.getSubTitle());
            }
            dateTimeText.setText(note.getDateTime());
            GradientDrawable gradientDrawable = (GradientDrawable) llContainer.getBackground();
            if(note.getBackgroundColor() != null){
                gradientDrawable.setColor(Color.parseColor(note.getBackgroundColor()));
            }else {
                gradientDrawable.setColor(Color.parseColor("#FFFFFFFF"));
            }

        }
    }
}
