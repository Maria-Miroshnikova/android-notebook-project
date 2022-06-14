package com.learn.notebook_study_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.learn.notebook_study_project.firebase_classes.Note;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;
import com.learn.notebook_study_project.view_models.EditNoteViewModel;

import java.util.Calendar;

public class EditNoteFragment extends Fragment {

    EditNoteViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel = ViewModelProviders.of(getActivity()).get(EditNoteViewModel.class);
        NoteWithId note = viewModel.getNote();

        // set note on screen
        EditText header = getView().findViewById(R.id.note_old_header_edit);
        header.setText(note.getNote().getHeader());
        EditText content = getView().findViewById(R.id.note_old_content_edit);
        content.setText(note.getNote().getContent());

        Button ok_btn = getView().findViewById(R.id.ok_edit_note_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note new_note = new Note();
                new_note.setHeader(header.getText().toString());
                new_note.setContent(content.getText().toString());
                new_note.setDateOfLastEdit(Calendar.getInstance().getTimeInMillis());
                viewModel.editNote(new_note);

                MainActivity.navController.navigate(R.id.action_editNoteFragment_to_notesPageFragment);
            }
        });

        Button delete_btn = getView().findViewById(R.id.delete_note_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.removeNote();

                MainActivity.navController.navigate(R.id.action_editNoteFragment_to_notesPageFragment);
            }
        });
    }
}