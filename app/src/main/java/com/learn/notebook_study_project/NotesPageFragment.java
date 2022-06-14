package com.learn.notebook_study_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learn.notebook_study_project.firebase_classes.NoteWithId;
import com.learn.notebook_study_project.models.DataState;
import com.learn.notebook_study_project.notes_list_components.NotesDataSource;
import com.learn.notebook_study_project.notes_list_components.NotesDiffUtilItemCallback;
import com.learn.notebook_study_project.notes_list_components.NotesListAdapter;
//import com.learn.notebook_study_project.other.NotesPageViewModelFactory;
import com.learn.notebook_study_project.notes_list_components.NotesListPagingAdapter;
import com.learn.notebook_study_project.notes_list_components.NotesRecyclerAdapter;
import com.learn.notebook_study_project.notes_list_components.OnNoteItemClickListener;
import com.learn.notebook_study_project.view_models.NotesPageViewModel;

import java.util.ArrayList;


public class NotesPageFragment extends Fragment {

    NotesPageViewModel viewModel;
 //   NotesListPagingAdapter adapter;
    NotesRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_page, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        /*// созданте вью-моделей
        AuthorisationPageViewModel authVM = ViewModelProviders.of(getActivity()).get(AuthorisationPageViewModel.class);
        String userAccountId = authVM.getUserWithLogin().getIdUserAccount();
        viewModel = ViewModelProviders.of(getActivity(), new NotesPageViewModelFactory(userAccountId)).get(NotesPageViewModel.class);*/

        viewModel = ViewModelProviders.of(getActivity()).get(NotesPageViewModel.class);
        viewModel.initialize();
        String userAccountId = viewModel.getUserAccountId();

        // ожидание загрузки данных
        LiveData<DataState> isInitialized = viewModel.getIsInitialized();
        isInitialized.observe(this, new Observer<DataState>() {
            @Override
            public void onChanged(DataState state) {
                if (state != DataState.NOT_INITIALIZED)
                    setScreenData(userAccountId);
            }
        });
    }

    // ПРОБЛЕМА
    // что, каждый раз прям заново устанавливать данные на экран? можно только список?
    // сколько живет листенер здесь, а в репо?
    private void setScreenData(String userAccountId)
    {
        // set greeting
        TextView greetingTextView = getView().findViewById(R.id.greeting_txt_vw);
        String greetingText =  getString(R.string.greeting_txt_vw);
        greetingText += userAccountId;
        greetingTextView.setText(greetingText);

        // *** PAGED LIST ***
       /* initAdapter();
        LiveData<PagedList<NoteWithId>> pagedNotesList = viewModel.getPagedNotes();
        pagedNotesList.observe(this, new Observer<PagedList<NoteWithId>>() {
            @Override
            public void onChanged(PagedList<NoteWithId> newPagedNptesList) {
                adapter.submitList(newPagedNptesList);
            }
        });*/
        // *** PAGED LIST ***

        initAdapter();
        // получаем список заметок пользователя
        LiveData<ArrayList<NoteWithId>> notesList = viewModel.getNotes();
        notesList.observe(this, new Observer<ArrayList<NoteWithId>>() {
            @Override
            public void onChanged(ArrayList<NoteWithId> notesList) {
                adapter.setData(notesList);

                // *** LIST SIMPLE ***
               /* ListView listView = getView().findViewById(R.id.notes_list_vw);
                NotesListAdapter adapter = new NotesListAdapter(getContext(), notesList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        viewModel.editNote(position);
                        MainActivity.navController.navigate(R.id.action_notesPageFragment_to_editNoteFragment);
                    }
                });*/
                // *** LIST SIMPLE ***
            }
        });

        // FAB button actions
        FloatingActionButton addNoteButton = getView().findViewById(R.id.add_note_fab_btn);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // переход на страницу создания
                MainActivity.navController.navigate(R.id.action_notesPageFragment_to_newNoteFragment);
            }
        });
    }

   /* void createNotesList(ArrayList<NoteWithId> notesList)
    {
        NotesDataSource dataSource = new NotesDataSource();

        // дизайн
        int count_pages = 10;
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(count_pages)
                .build();

        PagedList<NoteWithId> pagedList = new PagedList.Builder<>(dataSource, config)
                .build(); // executors?

        NotesListPagingAdapter adapter = new NotesListPagingAdapter(new NotesDiffUtilItemCallback());
        adapter.submitList(pagedList);

        RecyclerView recyclerView = getView().findViewById(R.id.notes_recycler_vw);

        // дизайн можно поменять
        int count_cols = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), count_cols);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initAdapter()
    {
        adapter = new NotesListPagingAdapter(new NotesDiffUtilItemCallback());
        RecyclerView recyclerView = getView().findViewById(R.id.notes_recycler_vw);

        // дизайн можно поменять
        int count_cols = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), count_cols);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }*/

   private void initAdapter()
   {
       adapter = new NotesRecyclerAdapter(new OnNoteItemClickListener() {
           @Override
           public void onItemClicked(NoteWithId noteWithId) {
               viewModel.editNote(noteWithId);
               MainActivity.navController.navigate(R.id.action_notesPageFragment_to_editNoteFragment);
           }
       });
       RecyclerView recyclerView = getView().findViewById(R.id.notes_recycler_vw);

       // дизайн можно поменять
       int count_cols = 2;
//       GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), count_cols);
       StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(count_cols, StaggeredGridLayoutManager.VERTICAL);
       //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(adapter);
   }
}

/*
<ListView
        android:id="@+id/notes_list_vw"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:dividerHeight="4pt"
                android:layout_below="@id/greeting_txt_vw"/>*/