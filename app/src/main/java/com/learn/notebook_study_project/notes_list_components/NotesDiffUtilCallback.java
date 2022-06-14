package com.learn.notebook_study_project.notes_list_components;

import androidx.recyclerview.widget.DiffUtil;

import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;

import java.util.List;

public class NotesDiffUtilCallback extends DiffUtil.Callback {

    List<NoteWithId> oldList;
    List<NoteWithId> newList;

    public NotesDiffUtilCallback(List<NoteWithId> oldList, List<NoteWithId> newList)
    {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        String oldId = oldList.get(oldItemPosition).getId();
        String newId = newList.get(newItemPosition).getId();
        return oldId.equals(newId);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Note oldNote = oldList.get(oldItemPosition).getNote();
        Note newNote = newList.get(newItemPosition).getNote();
        return oldNote.equals(newNote);
    }
}
