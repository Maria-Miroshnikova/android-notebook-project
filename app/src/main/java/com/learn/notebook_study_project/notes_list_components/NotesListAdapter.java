package com.learn.notebook_study_project.notes_list_components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.learn.notebook_study_project.R;
import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;

import java.util.ArrayList;
import java.util.Date;

public class NotesListAdapter extends BaseAdapter {
    Context context;
    ArrayList<NoteWithId> notes;
    LayoutInflater lInflater;

    public NotesListAdapter(Context context, ArrayList<NoteWithId> notes)
    {
        this.context = context;
        this.notes = notes;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.note_preview_in_list, parent, false);
        }
        Note note =  notes.get(position).getNote();

        TextView header = view.findViewById(R.id.note_header_txt_vw);
        header.setText(note.getHeader());

        TextView content = view.findViewById(R.id.note_content_txt_vw);
        content.setText(note.getContent());

        Date date = new Date(note.getDateOfLastEdit());

        TextView date_txt_vw = view.findViewById(R.id.note_date_txt_view);
        date_txt_vw.setText(date.toString());

        return view;
    }
}
