package com.android.michael.simpleplanner.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface PlanDao {

    @Query("SELECT * FROM `plan` ORDER BY uid DESC LIMIT 1")
    Plan getLastPlan();

    @Insert
    void insertPlan(Plan plan);
}