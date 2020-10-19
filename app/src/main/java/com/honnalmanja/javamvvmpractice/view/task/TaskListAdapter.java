package com.honnalmanja.javamvvmpractice.view.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListViewHolder> {

    private List<Task> taskList;
    private TaskClickListener taskClickListener;

    public TaskListAdapter(List<Task> taskList, TaskClickListener taskClickListener) {
        this.taskClickListener = taskClickListener;
        this.taskList = taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void removeItem(int position){
        this.taskList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Task task, int position) {
        this.taskList.add(position, task);
        notifyItemInserted(position);
    }

    public void updateItem(Task task, int position){
        this.taskList.set(position, task);
        notifyItemChanged(position, task);
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_row, parent, false);
        return new TaskListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        holder.setData(taskList.get(position));
        holder.itemView.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            taskList.get(adapterPosition).setTaskCompleted(
                    !(taskList.get(adapterPosition).isTaskCompleted())
            );
            taskClickListener.onTaskClicked(taskList.get(adapterPosition), adapterPosition);
        });

        holder.getCbTask().setOnCheckedChangeListener((compoundButton, b) -> {
            int adapterPosition = holder.getAdapterPosition();
            taskList.get(adapterPosition).setTaskCompleted(
                    !(taskList.get(adapterPosition).isTaskCompleted())
            );
            taskClickListener.onTaskClicked(taskList.get(adapterPosition), adapterPosition);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
