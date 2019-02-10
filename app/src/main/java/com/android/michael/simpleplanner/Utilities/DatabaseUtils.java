package com.android.michael.simpleplanner.Utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.michael.simpleplanner.Database.Plan;
import com.android.michael.simpleplanner.Database.PlanDatabase;

public class DatabaseUtils {

    private Context context;

    public DatabaseUtils(Context context) {
        this.context = context;
    }

    public void insertPlan(Plan plan) {

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

    public Plan getLastPlan() {

        final Plan[] lastPlan = {new Plan()};

        new AsyncTask<Plan, Plan, Plan>() {

            @Override
            protected Plan doInBackground(Plan... plans) {
                Plan plan = PlanDatabase.getDatabase(context).planDao().getLastPlan();
                return plan;
            }

            @Override
            protected void onPostExecute(Plan plan) {
                super.onPostExecute(plan);
                lastPlan[0] = plan;
                Toast.makeText(context, "Last Saved Number: " + plan.getPlanNumber(), Toast.LENGTH_LONG).show();

            }
        }.execute();
        return lastPlan[0];
    }
}