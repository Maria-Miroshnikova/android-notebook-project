package com.learn.notebook_study_project;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.learn.notebook_study_project.firebase_classes.User;
import com.learn.notebook_study_project.models.FirebaseDbModel;
import com.learn.notebook_study_project.view_models.AuthorisationPageViewModel;

import java.util.Date;

public class AuthorisationPageFragment extends Fragment {

    private AuthorisationPageViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authorization_page, container, false);
    }

 /*   @Override
    public void onStart() {
        super.onStart();
        FirebaseDbModel db = new FirebaseDbModel();
       // db.addUser("check");
       // Query userWithLogin = db.getUsers().orderByChild("login").equalTo("check");
        //userWithLogin.addListenerForSingleValueEvent(new ValueEventListener() {
        db.getUsers().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EditText editor = getView().findViewById(R.id.login_editor);
                long n = snapshot.getChildrenCount();
                editor.setText(Long.toString(n));
                n = 0;
                for (DataSnapshot childSnapshot : snapshot.getChildren())
                {
                    User user = childSnapshot.getValue(User.class);
                    //user.setLogin(childSnapshot.child("idUserAccount").getValue().toString());
                    editor.setText(user.getIdUserAccount());
                    //editor.setText(Long.toString(n));
                    ++n;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("bad db", "loadPost:onCancelled", error.toException());
            }
        });
        // всё сука работает ебейше
        // и query, и просто слушатель на ссылке
        // и перебор детей снэпшота
        // и child(...), и get(class)
        // всё робит
        // НО! нельзя дебажить?????
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart() {
        super.onStart();
        viewModel = ViewModelProviders.of(getActivity()).get(AuthorisationPageViewModel.class);

        EditText editor = getView().findViewById(R.id.login_editor);

        LiveData<Boolean> isFoundLogin = viewModel.getIsFoundLogin();
        isFoundLogin.observe(this, new Observer<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(Boolean isFound) {
                if (viewModel.getIsFirstDataGetting())
                {
                    return;
                }
                if (isFound)
                {
                    MainActivity.navController.navigate(R.id.action_authorisationPageFragment_to_notesPageFragment);
                }
                else
                {
                    // уведомление о неудаче
                    EditText editor = getView().findViewById(R.id.login_editor);
                    editor.setText("Not found!");
                }
            }
        });

        Button buttonSingIn = getView().findViewById(R.id.sign_in_btn);
        buttonSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginEditor = getView().findViewById(R.id.login_editor);
                viewModel.trySignIn(loginEditor.getText().toString());
            }
        });
    }

    // проблемы: если повернуть экран, обнуляет!! + изначально пишет "not found"
    // --------- решено костылем: переменная isFirstGettingData
    // ---------------- ЭТО НЕ КОСТЫЛЬ это MutableLiveData<DataState>!
}