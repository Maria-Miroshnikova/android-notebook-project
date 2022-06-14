package com.learn.notebook_study_project.firebase_classes;

public class NoteWithId {

    Note note;
    String id;

    public NoteWithId() {}
    public NoteWithId(Note note, String id) {
        this.note = note;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Note getNote() {
        return note;
    }
}
