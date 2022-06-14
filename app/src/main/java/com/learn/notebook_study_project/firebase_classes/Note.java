package com.learn.notebook_study_project.firebase_classes;

import java.util.Date;

public class Note {

    private String header;
    private String content;
    private Long dateOfLastEdit;

    public Note() {}

    public Note(String header, String text)
    {
        this.header = header;
        this.content = text;
        this.dateOfLastEdit = new Long(0);
    }

    public Note(String header, String text, Long dateOfLastEdit)
    {
        this.header = header;
        this.content = text;
        this.dateOfLastEdit = dateOfLastEdit;
        if (dateOfLastEdit == null)
            this.dateOfLastEdit = new Long(0);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDateOfLastEdit() {
        if (dateOfLastEdit == null)
            dateOfLastEdit = new Long(0);
        return dateOfLastEdit;
    }

    public void setDateOfLastEdit(Long dateOfLastEdit) {
        this.dateOfLastEdit = dateOfLastEdit;
        if (dateOfLastEdit == null)
            this.dateOfLastEdit = new Long(0);
    }

    public Boolean equals(Note note)
    {
        if (!header.equals(note.getHeader()))
            return false;
        else if (!content.equals(note.getContent()))
            return false;
        else
            return true;
    }
}
