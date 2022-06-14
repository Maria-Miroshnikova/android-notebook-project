package com.learn.notebook_study_project.notes_list_components;

import com.learn.notebook_study_project.firebase_classes.NoteWithId;

public interface OnNoteItemClickListener {
    public void onItemClicked(NoteWithId noteWithId);
}
