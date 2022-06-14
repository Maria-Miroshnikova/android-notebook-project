package com.learn.notebook_study_project.view_models;

import androidx.lifecycle.ViewModel;

import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;
import com.learn.notebook_study_project.firebase_classes.UserAccount;
import com.learn.notebook_study_project.models.DataSource;
import com.learn.notebook_study_project.models.Repository;

import java.util.ArrayList;

public class EditNoteViewModel extends ViewModel {

    Repository repository;
    NoteWithId note;

    {
        repository = Repository.getInstance();
    }

    public EditNoteViewModel() {
    }

    public NoteWithId getNote()
    {
        note = repository.getNoteToEdit();
        return  note;
    }

    public void editNote(Note note)
    {
        repository.editNote(this.note.getId(), note);
    }

    public void removeNote()
    {
        repository.removeNote(this.note.getId());
    }
}
