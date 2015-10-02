package com.dean.travltotibet.database;

import com.dean.greendao.Plan;
import com.dean.greendao.RoutePlan;

import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/29.
 */
public class RoutePlansJson {

    public ArrayList<RoutePlan> plans;

    public ArrayList<RoutePlan> getRoutePlan() {
        return plans;
    }

    public void setRoutePlan(ArrayList<RoutePlan> plans) {
        this.plans = plans;
    }
}
