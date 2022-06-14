package com.learn.notebook_study_project.notes_list_components;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.learn.notebook_study_project.firebase_classes.NoteWithId;
import com.learn.notebook_study_project.models.Repository;

import java.util.List;

public class NotesDataSource extends PositionalDataSource<NoteWithId> {

    //private final Repository repository;

    public NotesDataSource()
    {
        //repository = Repository.getInstance();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<NoteWithId> callback) {
        //List<NoteWithId> result =  .getData(params.requestedStartPosition, params.requestedLoadSize);
        //callback.onResult(result, 0);
        return;
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<NoteWithId> callback) {
        //List<NoteWithId> result =  .getData(params.startPosition, params.loadSize);
        //callback.onResult(result, 0);
        return;
    }
}
