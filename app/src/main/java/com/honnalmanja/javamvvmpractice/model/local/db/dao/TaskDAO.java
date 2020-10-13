package com.honnalmanja.javamvvmpractice.model.local.db.dao;

import com.honnalmanja.javamvvmpractice.model.local.db.entity.Tasks;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface TaskDAO {

    @Insert
    long addTask(Tasks tasks);

    @Update
    void updateTask(Tasks tasks);

    @Delete
    void deleteTask(Tasks tasks);

    @Query("Select * from tasks")
    Flowable<List<Tasks>> getAllTasks();

    @Query("Select * from tasks where task_id==:taskID")
    Tasks getATask(long taskID);

}
