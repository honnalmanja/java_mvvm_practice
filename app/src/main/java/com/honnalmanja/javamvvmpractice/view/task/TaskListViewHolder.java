package com.honnalmanja.javamvvmpractice.view.task;

import android.view.View;
import android.widget.CheckBox;

import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListViewHolder extends RecyclerView.ViewHolder {

    private CheckBox cbTask;

    public TaskListViewHolder(@NonNull View itemView) {
        super(itemView);
        cbTask = itemView.findViewById(R.id.task_item_cb);
    }

    public CheckBox getCbTask() {
        return cbTask;
    }

    public void setData(Task task){
        cbTask.setChecked(task.isTaskCompleted());
        cbTask.setText(task.getTaskDescription());
    }
}
