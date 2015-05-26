package com.dean.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 用来为GreenDao框架生成Dao文件
 */
public class MyGenerator {
    public static final String DAO_PATH = "../app/src/main/java";
    public static final String PACKAGE_NAME = "com.dean.greendao";
    public static final int DATA_VERSION_CODE = 1;

    public static void main(String[] args) throws Exception {

        // The "root" model class to which you can add entities to.
        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);
        addLocation(schema);
        //生成Dao文件路径
        new DaoGenerator().generateAll(schema, DAO_PATH);

    }

    private static void addLocation(Schema schema) {
        Entity location = schema.addEntity("Location");
        location.addIdProperty();
        location.addStringProperty("name").notNull();
        location.addStringProperty("height").notNull();
        location.addStringProperty("mileage").notNull();
    }

}
