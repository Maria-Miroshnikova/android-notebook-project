package com.learn.notebook_study_project.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.learn.notebook_study_project.firebase_classes.NoteWithId;
import com.learn.notebook_study_project.firebase_classes.UserAccount;
import com.learn.notebook_study_project.models.DataSource;
import com.learn.notebook_study_project.models.DataState;
import com.learn.notebook_study_project.models.Repository;

import java.util.ArrayList;

public class NotesPageViewModel extends ViewModel {

    // ???
    // должен ли быть миллион коллбэков на все случаи жизни?
    // есть ли единый класс или способ их собрать или правило?
    interface NotesCollectedCallback {
        void onCollected();
    }

    /*MutableLiveData<PagedList<NoteWithId>> pagedNotes;
    int count_pages = 10;     // дизайн
    PagedList.Config config;

    public LiveData<PagedList<NoteWithId>> getPagedNotes() {
        return pagedNotes;
    }*/

    UserAccount userAccount;
    String userAccountId;
    MutableLiveData<ArrayList<NoteWithId>> notes;
    Repository repository;

    MutableLiveData<DataState> isInitialized;

    {
        isInitialized = new MutableLiveData<>(DataState.NOT_INITIALIZED);
        repository = Repository.getInstance();
        notes = new MutableLiveData<>();
        userAccountId = repository.getUserAccountId();
       /* config = new PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(count_pages)
            .build();*/
    }

    public NotesPageViewModel() {
        initialize();
    }

    // ???
    // очень запутанная сейчас схема с аккаунтом
    // инициализируется тут, а листенер в репо и коллбэк всегда возвращается сюда, хотя мы как бы не
    // инициализируемся, когда просто новая записка приходит
    public void initialize()
    {
        repository.getUserAccount(new DataSource.GetAccountCallback() {
            @Override
            public void onFound(@NonNull UserAccount userAccountFound) {
                userAccount = userAccountFound;

                // ???
                // не слишком сложная структура?? workManager?
                updateNotes(new NotesCollectedCallback() {
                    @Override
                    public void onCollected() {
                        if (isInitialized.getValue() == DataState.NOT_INITIALIZED)
                            isInitialized.setValue(DataState.INITIALIZED);
                        else
                            isInitialized.setValue(DataState.UPDATED);
                    }
                });
            }

            @Override
            public void onNotFound() {
                // some error?
            }
        });
    }

    public LiveData<DataState> getIsInitialized()
    {
        return isInitialized;
    }

    public LiveData<ArrayList<NoteWithId>> getNotes()
    {
        return notes;
    }

    public String getUserAccountId() { return userAccountId; }

    private void updateNotes(NotesCollectedCallback callback)
    {
        repository.getNotes(userAccount, new DataSource.GetNotesCallback() {
            @Override
            public void onFound(@NonNull ArrayList<NoteWithId> notesFound) {
                notes.setValue(notesFound);
                callback.onCollected();
            }
        });
    }

    public void editNote(int position)
    {
        repository.setNoteToEdit(notes.getValue().get(position));
    }
    public void editNote(NoteWithId note) {
        repository.setNoteToEdit(note);
    }
}
