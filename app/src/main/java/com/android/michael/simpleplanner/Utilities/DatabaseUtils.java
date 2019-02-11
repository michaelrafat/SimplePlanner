package com.android.michael.simpleplanner.Utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.michael.simpleplanner.Database.Plan;
import com.android.michael.simpleplanner.Database.PlanDatabase;

public class DatabaseUtils {

    public DatabaseUtils() {
    }

    public static void insertPlan(Plan plan, final Context context) {

        new AsyncTask<Plan, Plan, Void>() {
            @Override
            protected Void doInBackground(Plan... plans) {
                PlanDatabase.getDatabase(context).planDao().insertPlan(plans[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, "Number Saved!", Toast.LENGTH_LONG).show();
            }
        }.execute(plan);
    }

    public static Plan getLastPlan(final Context context) {

        final Plan[] lastPlan = {new Plan()};

        new AsyncTask<Plan, Plan, Plan>() {

            @Override
            protected Plan doInBackground(Plan... plans) {
                return PlanDatabase.getDatabase(context).planDao().getLastPlan();
            }

            @Override
            protected void onPostExecute(Plan plan) {
                super.onPostExecute(plan);
                lastPlan[0] = plan;

                if (plan != null) {
                    Toast.makeText(context, "Last Saved Number: " + plan.getPlanNumber(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Empty Database... No numbers found!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

        return lastPlan[0];
    }
}