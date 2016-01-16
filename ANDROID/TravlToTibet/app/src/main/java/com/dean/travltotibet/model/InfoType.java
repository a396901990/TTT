package com.dean.travltotibet.model;

import com.dean.greendao.PrepareInfo;
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
    CLOTHING,       // 衣物
    OUTDOOR,        // 户外装备
    CREDENTIALS,    // 证件
    PERSONAL,       // 个人用品
    OTHER,          // 其他
    ATTENTION;      // 注意事项

    public static final Map<InfoType, String> INFO_TEXT = new HashMap<InfoType, String>();
    public static final Map<InfoType, Integer> INFO_IMAGE = new HashMap<InfoType, Integer>();
    public static final Map<InfoType, Integer> INFO_COLOR = new HashMap<InfoType, Integer>();

    public static final ArrayList<InfoType> INFO_TYPES = new ArrayList<InfoType>();

    static {

        INFO_TYPES.add(InfoType.BUDGET);
        INFO_TYPES.add(InfoType.MEDICINE);
        INFO_TYPES.add(InfoType.EQUIP);
        INFO_TYPES.add(InfoType.CLOTHING);
        INFO_TYPES.add(InfoType.OUTDOOR);
        INFO_TYPES.add(InfoType.CREDENTIALS);
        INFO_TYPES.add(InfoType.PERSONAL);
        INFO_TYPES.add(InfoType.OTHER);
//        INFO_TYPES.add(InfoType.ATTENTION);

        INFO_TEXT.put(InfoType.BUDGET, "预算");
        INFO_TEXT.put(InfoType.MEDICINE, "药品");
        INFO_TEXT.put(InfoType.EQUIP, "装备");
        INFO_TEXT.put(InfoType.CLOTHING, "衣物");
        INFO_TEXT.put(InfoType.OUTDOOR, "户外装备");
        INFO_TEXT.put(InfoType.CREDENTIALS, "证件");
        INFO_TEXT.put(InfoType.PERSONAL, "个人物品");
        INFO_TEXT.put(InfoType.OTHER, "其他");
        INFO_TEXT.put(InfoType.ATTENTION, "注意事项");

        INFO_IMAGE.put(InfoType.BUDGET, R.drawable.icon_budget_color);
        INFO_IMAGE.put(InfoType.MEDICINE, R.drawable.icon_medicine_color);
        INFO_IMAGE.put(InfoType.EQUIP, R.drawable.icon_equip_color);
        INFO_IMAGE.put(InfoType.CLOTHING, R.drawable.icon_clothing_color);
        INFO_IMAGE.put(InfoType.OUTDOOR, R.drawable.icon_outdoor_color);
        INFO_IMAGE.put(InfoType.CREDENTIALS, R.drawable.icon_card_color);
        INFO_IMAGE.put(InfoType.PERSONAL, R.drawable.icon_person_color);
        INFO_IMAGE.put(InfoType.OTHER, R.drawable.icon_other_color);
        INFO_IMAGE.put(InfoType.ATTENTION, R.drawable.ic_ab_new_content);

        INFO_COLOR.put(InfoType.BUDGET, R.color.light_blue);
        INFO_COLOR.put(InfoType.MEDICINE, R.color.brown);
        INFO_COLOR.put(InfoType.EQUIP, R.color.dark_green);
        INFO_COLOR.put(InfoType.CLOTHING, R.color.sky_blue);
        INFO_COLOR.put(InfoType.OUTDOOR, R.color.gray);
        INFO_COLOR.put(InfoType.CREDENTIALS, R.color.light_green);
        INFO_COLOR.put(InfoType.PERSONAL, R.color.orange_red);
        INFO_COLOR.put(InfoType.OTHER, R.color.teal);
        INFO_COLOR.put(InfoType.ATTENTION, R.color.red);

    }

    public static String getInfoResult(InfoType type, PrepareInfo prepareInfo) {
        switch (type) {
            case BUDGET:
                return prepareInfo.getBudget();
            case MEDICINE:
                return prepareInfo.getMedicine();
            case EQUIP:
                return prepareInfo.getEquip();
            case CLOTHING:
                return prepareInfo.getClothing();
            case OUTDOOR:
                return prepareInfo.getOutdoor();
            case CREDENTIALS:
                return prepareInfo.getCredential();
            case PERSONAL:
                return prepareInfo.getPersonal();
            case OTHER:
                return prepareInfo.getOther();
            case ATTENTION:
                return prepareInfo.getAttention();
        }
        return null;
    }
}
