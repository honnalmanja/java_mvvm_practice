package com.honnalmanja.javamvvmpractice.view.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Unit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.viewmodel.AddTaskViewModel;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link AddTaskDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskDialogFragment extends DialogFragment {

    private static final String USER_TOKEN = "user_token";

    private AppCompatImageButton ibCancel, ibAccept;
    private AppCompatEditText etAddTask;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AddTaskViewModel viewModel;

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userToken String userToken to create task with user loggedIN.
     * @return A new instance of fragment AddTaskDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskDialogFragment newInstance(String userToken) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString(USER_TOKEN, userToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
        if (getArguments() != null) {
            viewModel.setUserID(getArguments().getString(USER_TOKEN));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);

        compositeDisposable.add(
                RxView.clicks(ibAccept).throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Unit>() {
                            @Override
                            public void accept(Unit unit) throws Throwable {
                                postTask();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {

                            }
                        })
        );

        compositeDisposable.add(
                RxView.clicks(ibCancel).throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Unit>() {
                            @Override
                            public void accept(Unit unit) throws Throwable {
                                dismiss();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {

                            }
                        })
        );


    }

    public void postTask(){

        String taskDescription = etAddTask.getText().toString();

        if(taskDescription.trim().isEmpty()){
            etAddTask.setError("Task description cannot be null");
            return;
        }

        viewModel.postTaskDescription(taskDescription);

    }

    private void bindViews(View view) {

        ibCancel = view.findViewById(R.id.tool_bar_back_btn);
        ibCancel.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_close_24, null)
        );
        ibCancel.setVisibility(View.VISIBLE);

        ibAccept = view.findViewById(R.id.tool_bar_more_options_btn);
        ibAccept.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_24, null)
        );
        ibAccept.setVisibility(View.VISIBLE);

        AppCompatTextView tvTitle = view.findViewById(R.id.tool_bar_title_tv);
        tvTitle.setText(getResources().getString(R.string.add_task_title));

        etAddTask = view.findViewById(R.id.add_task_et);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.clear();
        compositeDisposable.clear();
    }
}