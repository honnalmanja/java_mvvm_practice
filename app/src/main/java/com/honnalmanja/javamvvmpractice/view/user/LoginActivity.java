package com.honnalmanja.javamvvmpractice.view.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.model.app.ServerResponse;
import com.honnalmanja.javamvvmpractice.utils.CommonUtils;
import com.honnalmanja.javamvvmpractice.viewmodel.LoginViewModel;
import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private LoginViewModel viewModel;
    private LoginActivity activity;
    private AppCompatEditText etEmail, etPassword;
    private AppCompatButton btnLogin;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        bindView();

        compositeDisposable.add(
                RxView.clicks(btnLogin).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Throwable {
                        onLoginButtonClick();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.e(TAG, "Error in Login Click");
                    }
                })
        );

        viewModel.getLoginSuccessLiveData().observe(this, new Observer<ServerResponse>() {
            @Override
            public void onChanged(ServerResponse serverResponse) {
                if(serverResponse.isSuccess()){
                    finish();
                } else {
                    Snackbar.make(
                        scrollView,
                        serverResponse.getMessage(),
                        BaseTransientBottomBar.LENGTH_LONG
                    ).show();
                }
            }
        });

    }

    private void bindView() {

        etEmail = findViewById(R.id.login_email_et);
        etPassword = findViewById(R.id.login_password_et);
        btnLogin = findViewById(R.id.login_btn);

        scrollView = findViewById(R.id.login_sv);

    }

    public void onLoginButtonClick(){


        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(email.trim().isEmpty()){
            etEmail.setError(getString(R.string.enter_email_error));
            etEmail.requestFocus();
            return;
        }

        if(!CommonUtils.isValid(email)){
            etEmail.setError(getString(R.string.enter_valid_email_error));
            etEmail.requestFocus();
            return;
        }

        if(password.trim().isEmpty()){
            etPassword.setError(getString(R.string.enter_password_error));
            return;
        }

        viewModel.postLoginRequest(email, password);


    }

    public void openSignUp(View view){
        startActivity(new Intent(activity, SignUpActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.clear();
        compositeDisposable.clear();
    }
}