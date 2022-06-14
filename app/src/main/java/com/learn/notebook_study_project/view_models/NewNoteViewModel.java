package com.learn.notebook_study_project.view_models;

import androidx.lifecycle.ViewModel;

import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.models.DataSource;
import com.learn.notebook_study_project.models.Repository;

import java.util.Calendar;

public class NewNoteViewModel extends ViewModel {

    Note note;
    Repository repository;

    {
        repository = Repository.getInstance();
    }

   /* public NewNoteViewModel(String userAccountId)
    {
        this.userAccountId = userAccountId;
    }*/

    public NewNoteViewModel() {
    }

    // ???
    // где лучше образовывать Note - в VM или в V?
    // дожидаться добавления в базу или пофиг?
    public void addNote(String header, String content)
    {
        // TODO:
        // потом улучшить дату!
        note = new Note(header, content, Calendar.getInstance().getTimeInMillis());
        repository.addNote(note);/*, new DataSource.AddNodeCallback() {
            @Override
            public void onAdded() {

            }
        });*/
    }
}
