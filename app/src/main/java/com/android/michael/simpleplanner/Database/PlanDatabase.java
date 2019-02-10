package com.android.michael.simpleplanner.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Plan.class}, version = 1)
public abstract class PlanDatabase extends RoomDatabase {

    public abstract PlanDao planDao();

    private static volatile PlanDatabase INSTANCE;

    public static PlanDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (PlanDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlanDatabase.class, "plan_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}