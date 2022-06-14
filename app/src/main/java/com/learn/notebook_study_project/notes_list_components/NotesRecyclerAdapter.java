package com.learn.notebook_study_project.notes_list_components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.notebook_study_project.R;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;

import java.util.ArrayList;
import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    List<NoteWithId> notes;
    OnNoteItemClickListener itemClickListener;

    public NotesRecyclerAdapter(OnNoteItemClickListener itemClickListener) {
        notes = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<NoteWithId> newNotes)
    {
        NotesDiffUtilCallback diffUtil = new NotesDiffUtilCallback(notes, newNotes);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffUtil);
        notes.clear();
        notes.addAll(newNotes);
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_preview_card_in_list, parent, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(view);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(notes.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
