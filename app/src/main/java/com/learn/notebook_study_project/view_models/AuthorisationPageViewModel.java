package com.learn.notebook_study_project.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.notebook_study_project.firebase_classes.User;
import com.learn.notebook_study_project.firebase_classes.UserAccount;
import com.learn.notebook_study_project.models.DataSource;
import com.learn.notebook_study_project.models.Repository;

public class AuthorisationPageViewModel extends ViewModel {
    String login;
    User userWithLogin;
    Boolean isFirstDataGetting; // нужен для первого запуска, чтобы данные из модели были стартовые, а не попыточные
    MutableLiveData<Boolean> isFoundLogin;

    // ???
    // нужна ссылка на модель! По идее он должен
    // создаваться единственным, подобно ViewModelProvider? Потом....
    Repository repository;

    {
        isFirstDataGetting = true;
        login = new String();
        isFoundLogin = new MutableLiveData<>(false);
        userWithLogin = new User();
        repository = Repository.getInstance();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getIsFirstDataGetting() { return isFirstDataGetting; }

    public boolean trySignIn(String login)
    {
        setLogin(login);
        isFirstDataGetting = false;
        repository.trySignIn(login, new DataSource.SignInCallback() {
            @Override
            public void onSignedIn(User user) {
                isFoundLogin.setValue(true);
                 userWithLogin = user;
            }

            @Override
            public void onLoginNotFound() {
                isFoundLogin.setValue(false);
            }
        });

        // изменять livedata может только класс, в котором она - поле.
        // как мне организовать, чтобы репозиторий уведомлял ВьюМодель о том, что нашел?
        // -- callback
        return true;
    }

    public User getUserWithLogin() {
        return userWithLogin;
    }

    public LiveData<Boolean> getIsFoundLogin() {
        return isFoundLogin;
    }
}
