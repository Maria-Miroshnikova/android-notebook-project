package com.learn.notebook_study_project.notes_list_components;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.notebook_study_project.R;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;

import java.util.Date;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView date;
    TextView header;
    TextView content;
    CardView cardView;
   // View itemView;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        //this.itemView = itemView;
        cardView = itemView.findViewById(R.id.card_view);
        date = itemView.findViewById(R.id.note_date_txt_view);
        header = itemView.findViewById(R.id.note_header_txt_vw);
        content = itemView.findViewById(R.id.note_content_txt_vw);
    }

    public void bind(NoteWithId note, OnNoteItemClickListener itemClickListener)
    {
        Date date_ = new Date(note.getNote().getDateOfLastEdit());
        date.setText(date_.toString());
        header.setText(note.getNote().getHeader().toString());
        content.setText(note.getNote().getContent().toString());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClicked(note);
            }
        });
    }
}
