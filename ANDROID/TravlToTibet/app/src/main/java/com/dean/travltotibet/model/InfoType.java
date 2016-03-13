package com.dean.travltotibet.model;

import android.content.Context;

import com.dean.travltotibet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeanGuo on 9/28/15.
 */
public enum InfoType {
    BUDGET,         // 预算
    MEDICINE,       // 药品
    EQUIP,          // 装备
    OUTDOOR,        // 户外装备
    CREDENTIALS,    // 证件
    PERSONAL,       // 个人用品
    OTHER,          // 其他
    ATTENTION;      // 注意事项

    public static final Map<InfoType, String> INFO_TEXT = new HashMap<InfoType, String>();
    public static final Map<InfoType, String> INFO_COLUMN = new HashMap<InfoType, String>();
    public static final Map<InfoType, Integer> INFO_IMAGE = new HashMap<InfoType, Integer>();
    public static final Map<InfoType, Integer> INFO_COLOR = new HashMap<InfoType, Integer>();

    public static final ArrayList<InfoType> INFO_TYPES = new ArrayList<InfoType>();

    static {

        INFO_TYPES.add(InfoType.BUDGET);
        INFO_TYPES.add(InfoType.MEDICINE);
        INFO_TYPES.add(InfoType.EQUIP);
        INFO_TYPES.add(InfoType.ATTENTION);
        INFO_TYPES.add(InfoType.OUTDOOR);
        INFO_TYPES.add(InfoType.PERSONAL);
        INFO_TYPES.add(InfoType.CREDENTIALS);
        INFO_TYPES.add(InfoType.OTHER);

        INFO_TEXT.put(InfoType.BUDGET, "预算");
        INFO_TEXT.put(InfoType.MEDICINE, "药品");
        INFO_TEXT.put(InfoType.EQUIP, "装备");
        INFO_TEXT.put(InfoType.OUTDOOR, "户外装备");
        INFO_TEXT.put(InfoType.CREDENTIALS, "证件");
        INFO_TEXT.put(InfoType.PERSONAL, "个人物品");
        INFO_TEXT.put(InfoType.OTHER, "其他");
        INFO_TEXT.put(InfoType.ATTENTION, "注意事项");

        INFO_COLUMN.put(InfoType.BUDGET, "budget");
        INFO_COLUMN.put(InfoType.MEDICINE, "medicine");
        INFO_COLUMN.put(InfoType.EQUIP, "equip");
        INFO_COLUMN.put(InfoType.OUTDOOR, "outdoor");
        INFO_COLUMN.put(InfoType.CREDENTIALS, "credential");
        INFO_COLUMN.put(InfoType.PERSONAL, "personal");
        INFO_COLUMN.put(InfoType.OTHER, "other");
        INFO_COLUMN.put(InfoType.ATTENTION, "attention");

        INFO_IMAGE.put(InfoType.BUDGET, R.drawable.icon_budget);
        INFO_IMAGE.put(InfoType.MEDICINE, R.drawable.icon_medicine);
        INFO_IMAGE.put(InfoType.EQUIP, R.drawable.icon_equip);
        INFO_IMAGE.put(InfoType.OUTDOOR, R.drawable.icon_outdoor);
        INFO_IMAGE.put(InfoType.CREDENTIALS, R.drawable.icon_card);
        INFO_IMAGE.put(InfoType.PERSONAL, R.drawable.icon_person);
        INFO_IMAGE.put(InfoType.OTHER, R.drawable.icon_other);
        INFO_IMAGE.put(InfoType.ATTENTION, R.drawable.icon_attention);

        INFO_COLOR.put(InfoType.BUDGET, R.color.light_orange);
        INFO_COLOR.put(InfoType.MEDICINE, R.color.light_red);
        INFO_COLOR.put(InfoType.EQUIP, R.color.light_blue);
        INFO_COLOR.put(InfoType.ATTENTION, R.color.dark_orange);
        INFO_COLOR.put(InfoType.OUTDOOR, R.color.gray);
        INFO_COLOR.put(InfoType.CREDENTIALS, R.color.light_green);
        INFO_COLOR.put(InfoType.PERSONAL, R.color.orange_red);
        INFO_COLOR.put(InfoType.OTHER, R.color.teal);

    }

    public static String getInfoResult(InfoType type, PrepareInfo prepareInfo, Context mContent) {
        switch (type) {
            case BUDGET:
                return prepareInfo.getBudget().getFileUrl(mContent);
            case MEDICINE:
                return prepareInfo.getMedicine().getFileUrl(mContent);
            case EQUIP:
                return prepareInfo.getEquip().getFileUrl(mContent);
            case OUTDOOR:
                return prepareInfo.getOutdoor().getFileUrl(mContent);
            case CREDENTIALS:
                return prepareInfo.getCredential().getFileUrl(mContent);
            case PERSONAL:
                return prepareInfo.getPersonal().getFileUrl(mContent);
            case OTHER:
                return prepareInfo.getOther().getFileUrl(mContent);
            case ATTENTION:
                return prepareInfo.getAttention().getFileUrl(mContent);
        }
        return null;
    }
}
