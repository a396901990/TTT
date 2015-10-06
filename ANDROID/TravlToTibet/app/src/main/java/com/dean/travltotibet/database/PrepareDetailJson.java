package com.dean.travltotibet.database;

import com.dean.greendao.PrepareDetail;
import com.dean.greendao.PrepareInfo;

import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/29.
 */
public class PrepareDetailJson {

    public ArrayList<PrepareDetail> getPrepareDetails() {
        return prepareDetails;
    }

    public void setPrepareDetails(ArrayList<PrepareDetail> prepareDetails) {
        this.prepareDetails = prepareDetails;
    }

    public ArrayList<PrepareDetail> prepareDetails;

}
