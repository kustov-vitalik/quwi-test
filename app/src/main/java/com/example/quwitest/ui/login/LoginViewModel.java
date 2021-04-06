package com.example.quwitest.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quwitest.R;
import com.example.quwitest.data.LoginRepository;
import com.example.quwitest.data.local.sharedpreferences.TokenStorage;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.disposables.CompositeDisposable;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> loggedIn;

    private final LoginRepository loginRepository;
    private final TokenStorage tokenStorage;

    @Inject
    public LoginViewModel(LoginRepository loginRepository, TokenStorage tokenStorage) {
        this.loginRepository = loginRepository;
        this.tokenStorage = tokenStorage;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public LiveData<Boolean> getLoggedIn() {
        if (loggedIn == null) {
            loggedIn = new MutableLiveData<>();
            checkIsLoggedIn();
        }
        return loggedIn;
    }

    private void checkIsLoggedIn() {
        if (!tokenStorage.hasToken()) {
            loggedIn.setValue(false);
            return;
        }

        disposable.add(
                loginRepository.getCurrentUser()
                        .subscribe(userInfo -> loggedIn.setValue(true), throwable -> loggedIn.setValue(false))
        );

    }

    public void login(String username, String password) {
        disposable.add(
                loginRepository.login(username, password)
                        .subscribe((authResponse, throwable) -> {
                            if (throwable != null) {
                                Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
                            }
                            if (authResponse != null && authResponse.getToken() != null && !authResponse.getToken().isEmpty()) {
                                tokenStorage.storeToken(authResponse.getToken());
                                loginResult.setValue(new LoginResult(new LoggedInUserView(username)));
                            } else if (throwable != null) {
                                loginResult.setValue(new LoginResult(R.string.login_failed));
                            }
                        })
        );
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    @Override
    protected void onCleared() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onCleared();
    }
}