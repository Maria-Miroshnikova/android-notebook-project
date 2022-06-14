package com.learn.notebook_study_project.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.firebase_classes.User;

public class FirebaseDbModel {

    private static FirebaseDbModel instance;

    private FirebaseDatabase database;
    private DatabaseReference root;
    private DatabaseReference users;
    private DatabaseReference userAccounts;
    private DatabaseReference notes;

    private String db_address = "https://notebook-study-project-default-rtdb.firebaseio.com/";
    private String users_address = "users";
    private String user_accounts_address = "user_accounts";
    private String notes_address = "notes";

    private String default_idNote_value = "empty";

    private FirebaseDbModel()
    {
        database = FirebaseDatabase.getInstance();
        root = database.getReference();
        users = database.getReference(users_address);
        userAccounts = database.getReference(user_accounts_address);
        notes = database.getReference(notes_address);
    }

    public static FirebaseDbModel getInstance() {
        if (instance == null)
            instance = new FirebaseDbModel();
        return instance;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public DatabaseReference getUsers() {
        return users;
    }

    public DatabaseReference getUserAccounts() {
        return userAccounts;
    }

    public DatabaseReference getNotes() {
        return notes;
    }

    public DatabaseReference getRoot() {
        return root;
    }

    public User addUser(String login)
    {
        String newUserId = users.push().getKey();
        String newUserAccountId = addUserAccount();
        User user = new User(login, newUserAccountId);
        users.child(newUserId).setValue(user);
        return user;
    }

    private String addUserAccount()
    {
        return userAccounts.push().getKey();
    }

    public void addNote(String idUserAccount, Note note)
    {
        String newNoteId = notes.push().getKey();
        notes.child(newNoteId).setValue(note);
        userAccounts.child(idUserAccount).child(newNoteId).setValue(default_idNote_value);
    }

    public void removeNote(String idUserAccount, String idNote)
    {
        userAccounts.child(idUserAccount).child(idNote).removeValue();
        notes.child(idNote).removeValue();
    }

    public void editNote(String idNote, Note note)
    {
        notes.child(idNote).setValue(note);
    }
}
