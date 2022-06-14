package com.learn.notebook_study_project.models;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;
import com.learn.notebook_study_project.firebase_classes.User;
import com.learn.notebook_study_project.firebase_classes.UserAccount;
//import com.learn.notebook_study_project.other.NotesPageViewModelFactory;

import java.util.ArrayList;
import java.util.Comparator;

public class Repository implements DataSource {

    private static Repository instance;

    private Repository()
    {
        userAccountId = new String();
        noteToEdit = new NoteWithId();
    }

    public static Repository getInstance()
    {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    String userAccountId;
    NoteWithId noteToEdit;

    public String getUserAccountId() {
        return userAccountId;
    }

    public NoteWithId getNoteToEdit() {
        return noteToEdit;
    }

    public void setNoteToEdit(NoteWithId note)
    {
        this.noteToEdit = note;
    }

    @Override
    public void trySignIn(String login, SignInCallback callback)
    {
        DatabaseReference users = FirebaseDbModel.getInstance().getUsers();
        Query usersWithLogin = users.orderByChild("login").equalTo(login);
        usersWithLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() == 1)
                {
                    for (DataSnapshot userSnapshot : snapshot.getChildren())
                    {
                        User user = userSnapshot.getValue(User.class);

                        userAccountId = user.getIdUserAccount();

                        callback.onSignedIn(user);
                    }
                }
                else
                    callback.onLoginNotFound();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getNotes(UserAccount userAccount, GetNotesCallback callback)
    {
        ArrayList<String> notesId = userAccount.getIdNotesList();
        ArrayList<NoteWithId> notesList = new ArrayList<>();
        DatabaseReference notes = FirebaseDbModel.getInstance().getNotes();
        notes.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int idx = 0; idx < notesId.size(); ++idx)
                {
                    String noteId = notesId.get(idx);
                    for (DataSnapshot noteSnapshot : snapshot.getChildren())
                    {
                        String key = noteSnapshot.getKey();
                        if (!noteId.equals(key))
                            continue;
                        Note note = noteSnapshot.getValue(Note.class);
                        notesList.add(new NoteWithId(note, key));
                        break;
                    }
                }

                // упорядочиваем в порядке убывания по дате
                notesList.sort(new Comparator<NoteWithId>() {
                    @Override
                    public int compare(NoteWithId o1, NoteWithId o2) {
                        Long time1 = o1.getNote().getDateOfLastEdit();
                        Long time2 = o2.getNote().getDateOfLastEdit();
                        if (time1 < time2)
                            return 1;
                        else if (time1 > time2)
                            return -1;
                        return 0;
                    }
                });
                callback.onFound(notesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUserAccount(GetAccountCallback callback)
    {
        // ???
        // 1. нет проверки на то, что такое акк есть (полагаемся на надежность базы??)
        // 2. акк каждый раз создается заново вместе со всем своим списком!
        //      может, список - и ладно, а вот его доп инфа?

        DatabaseReference userAccounts = FirebaseDbModel.getInstance().getUserAccounts();
        Query userAccountWithId = userAccounts.child(userAccountId);// orderByKey().equalTo(userAccountId); // не удается приминить одновременно!
        userAccountWithId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount userAccount = new UserAccount(); //= userAccountSnapshot.getValue(UserAccount.class);
                // достаём весь список заметок и добавляем
                for (DataSnapshot noteSnapshot : snapshot.getChildren())
                {
                    userAccount.addNote(noteSnapshot.getKey());
                }
                callback.onFound(userAccount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addNote(Note note)//, AddNodeCallback callback)
    {
        FirebaseDbModel.getInstance().addNote(userAccountId, note);
        //callback.onAdded();
    }

    public void editNote(String idNote, Note note)
    {
        FirebaseDbModel.getInstance().editNote(idNote, note);
    }

    public void removeNote(String idNote)
    {
        FirebaseDbModel.getInstance().removeNote(userAccountId, idNote);
    }
}