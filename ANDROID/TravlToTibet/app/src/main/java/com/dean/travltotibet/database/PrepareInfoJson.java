package com.dean.travltotibet.database;

import com.dean.greendao.GeocodeOld;
import com.dean.greendao.PrepareInfo;

import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/29.
 */
public class PrepareInfoJson {

    public ArrayList<PrepareInfo> getPrepareInfos() {
        return prepareInfos;
    }

    public void setPrepareInfos(ArrayList<PrepareInfo> prepareInfos) {
        this.prepareInfos = prepareInfos;
    }

    public ArrayList<PrepareInfo> prepareInfos;

}
