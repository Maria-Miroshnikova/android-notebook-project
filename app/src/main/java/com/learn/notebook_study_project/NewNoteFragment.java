package com.learn.notebook_study_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

//import com.learn.notebook_study_project.other.NewNoteViewModelFactory;
//import com.learn.notebook_study_project.other.NotesPageViewModelFactory;
import com.learn.notebook_study_project.view_models.AuthorisationPageViewModel;
import com.learn.notebook_study_project.view_models.NewNoteViewModel;
import com.learn.notebook_study_project.view_models.NotesPageViewModel;

public class NewNoteFragment extends Fragment {

    NewNoteViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_note, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModel = ViewModelProviders.of(getActivity()).get(NewNoteViewModel.class);

        Button button = getView().findViewById(R.id.ok_add_note_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText header = getView().findViewById(R.id.note_header_edit);
                EditText content = getView().findViewById(R.id.note_content_edit);
                viewModel.addNote(header.getText().toString(), content.getText().toString());

                MainActivity.navController.navigate(R.id.action_newNoteFragment_to_notesPageFragment);
            }
        });
    }
}