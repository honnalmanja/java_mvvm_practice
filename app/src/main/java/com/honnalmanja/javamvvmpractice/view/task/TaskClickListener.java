package com.honnalmanja.javamvvmpractice.view.task;

import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;

public interface TaskClickListener {

    void onTaskClicked(Task task, int position);

    void onLongTaskClicked(Task task, int position);
}
