package com.learn.notebook_study_project.models;

import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;
import com.learn.notebook_study_project.firebase_classes.User;
import com.learn.notebook_study_project.firebase_classes.UserAccount;

import java.util.ArrayList;

public interface DataSource {

    interface SignInCallback {
        void onSignedIn(User user);
        void onLoginNotFound();
    }

    interface GetAccountCallback{
        void onFound(UserAccount userAccount);
        void onNotFound();
    }

    interface GetNotesCallback{
        void onFound(ArrayList<NoteWithId> notes);
    }

    interface AddNodeCallback{
        void onAdded();
    }

    void trySignIn(String login, SignInCallback callback);

    void getUserAccount(GetAccountCallback callback);

    void getNotes(UserAccount userAccount, GetNotesCallback callback);
}
