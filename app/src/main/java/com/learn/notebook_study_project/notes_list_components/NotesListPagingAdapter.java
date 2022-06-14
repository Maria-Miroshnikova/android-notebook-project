package com.learn.notebook_study_project.notes_list_components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.learn.notebook_study_project.R;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;

public class NotesListPagingAdapter extends PagedListAdapter<NoteWithId, NoteViewHolder> {

    public NotesListPagingAdapter(DiffUtil.ItemCallback<NoteWithId> diffUtilCallback) {
        super(diffUtilCallback);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_preview_in_list, parent, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(view);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        //holder.bind(getItem(position));
    }
}
