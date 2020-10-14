package com.honnalmanja.javamvvmpractice.view.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private SignUpActivity activity;
    private SignUpViewModel viewModel;

    private TextInputEditText etEmail, etUserName, etAge, etPassword, etConfirmPassword;
    private AppCompatImageButton ibCancel, ibAccept;
    private AppCompatTextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        activity = this;

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        bindViews();

    }

    private void bindViews() {

        ibCancel = findViewById(R.id.tool_bar_back_btn);
        ibAccept = findViewById(R.id.tool_bar_more_options_btn);

        etEmail = findViewById(R.id.profile_email_et);
        etUserName = findViewById(R.id.profile_name_et);
        etAge = findViewById(R.id.profile_age_et);
        etPassword = findViewById(R.id.profile_password_et);
        etConfirmPassword = findViewById(R.id.profile_confirm_password_et);
    }
}