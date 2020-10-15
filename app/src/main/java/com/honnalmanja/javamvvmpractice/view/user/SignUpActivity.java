package com.honnalmanja.javamvvmpractice.view.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Unit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.model.app.UserLiveData;
import com.honnalmanja.javamvvmpractice.view.task.TaskActivity;
import com.honnalmanja.javamvvmpractice.viewmodel.SignUpViewModel;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private SignUpActivity activity;
    private SignUpViewModel viewModel;

    private AppCompatEditText etEmail, etUserName, etAge, etPassword, etConfirmPassword;
    private AppCompatImageButton ibCancel, ibAccept;
    private AppCompatTextView tvTitle;
    private LinearLayoutCompat llcHolder;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        activity = this;

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        bindViews();

        compositeDisposable.add(
            RxView.clicks(ibAccept).throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Unit>() {
                        @Override
                        public void accept(Unit unit) throws Throwable {
                            postUserData();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Throwable {
                            Log.e(TAG,"throwable: " +throwable.getMessage());
                        }
                    })
        );

        viewModel.getSignUpSuccessLiveData().observe(this, new Observer<UserLiveData>() {
            @Override
            public void onChanged(UserLiveData userLiveData) {
                if(userLiveData.getStatusCode() == 200){
                    Intent intent = new Intent(activity, TaskActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(
                            llcHolder,
                            userLiveData.getMessage(),
                            BaseTransientBottomBar.LENGTH_LONG
                    ).show();
                }
            }
        });



    }

    private void bindViews() {

        llcHolder = findViewById(R.id.sign_up_holder);

        ibCancel = findViewById(R.id.tool_bar_back_btn);
        ibCancel.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_close_24, null)
        );
        ibCancel.setVisibility(View.VISIBLE);

        ibAccept = findViewById(R.id.tool_bar_more_options_btn);
        ibAccept.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_24, null)
        );
        ibAccept.setVisibility(View.VISIBLE);

        tvTitle = findViewById(R.id.tool_bar_title_tv);
        tvTitle.setText(getResources().getString(R.string.sign_up_title));

        etEmail = findViewById(R.id.profile_email_et);
        etUserName = findViewById(R.id.profile_name_et);
        etAge = findViewById(R.id.profile_age_et);
        etPassword = findViewById(R.id.profile_password_et);
        etConfirmPassword = findViewById(R.id.profile_confirm_password_et);
    }

    public void postUserData(){

        String email = etEmail.getText().toString();
        String userName = etUserName.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if(email.trim().isEmpty() || userName.trim().isEmpty() || password.trim().isEmpty()){
            Snackbar.make(llcHolder, "Fill necessary fields", BaseTransientBottomBar.LENGTH_LONG).show();
            return;
        }

        if(!password.trim().equalsIgnoreCase(confirmPassword.trim())){
            Snackbar.make(llcHolder, "Both passwords should be identical", BaseTransientBottomBar.LENGTH_LONG).show();
            return;
        }

        viewModel.postSignUpData(email, password, userName, age);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        viewModel.clear();
    }
}