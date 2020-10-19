package com.honnalmanja.javamvvmpractice.model.local.db;

import com.honnalmanja.javamvvmpractice.model.local.db.dao.TaskDAO;
import com.honnalmanja.javamvvmpractice.model.local.db.entity.Tasks;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Tasks.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDAO getTaskDAO();

}
