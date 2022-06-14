package com.learn.notebook_study_project.notes_list_components;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;

import java.util.List;

// а можно static, чтобы всегда одно и то же возвращал
public class NotesDiffUtilItemCallback extends DiffUtil.ItemCallback<NoteWithId> {

    @Override
    public boolean areItemsTheSame(@NonNull NoteWithId oldItem, @NonNull NoteWithId newItem) {
        String oldId = oldItem.getId();
        String newId = newItem.getId();
        return  (oldId.equals(newId));
    }

    @Override
    public boolean areContentsTheSame(@NonNull NoteWithId oldItem, @NonNull NoteWithId newItem) {
        Note oldNote = oldItem.getNote();
        Note newNote = newItem.getNote();
        return oldNote.equals(newNote);
    }
}
