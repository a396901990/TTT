package com.dean.travltotibet.fragment;

import android.os.Bundle;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.model.GalleryInfo;
import com.dean.travltotibet.model.ScenicInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 12/17/15.
 */
public class InfoScenicFragment extends GalleryInfoFragment {

    private InfoActivity infoActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        infoActivity = (InfoActivity) getActivity();
    }

    public void getResult(final int actionType) {

        BmobQuery<ScenicInfo> query = new BmobQuery<>();
        query.order("-comment,-createdAt");
        query.addWhereContains("route", infoActivity.getRoute());
        query.addQueryKeys("objectId,scenicName,scenic_Pic");

        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(mAdapter.getItemCount());
        }

        // 设置每页数据个数
        query.setLimit(ITEM_LIMIT);

        query.findObjects(getActivity(), new FindListener<ScenicInfo>() {
            @Override
            public void onSuccess(List<ScenicInfo> list) {
                galleryInfos = new ArrayList<GalleryInfo>();
                for (ScenicInfo scenicInfo : list) {
                    GalleryInfo galleryInfo = new GalleryInfo();
                    galleryInfo.setName(scenicInfo.getScenicName());
                    galleryInfo.setUrl(scenicInfo.getScenic_Pic());
                    galleryInfo.setObjectId(scenicInfo.getObjectId());
                    galleryInfos.add(galleryInfo);
                }

                if (actionType == STATE_REFRESH) {
                    toDo(LOADING_SUCCESS, 0);
                } else {
                    toDo(LOADING_MORE_SUCCESS, 0);
                }
            }

            @Override
            public void onError(int i, String s) {

                if (actionType == STATE_REFRESH) {
                    toDo(LOADING_ERROR, 0);
                } else {
                    toDo(LOADING_MORE_ERROR, 0);
                }
            }
        });
    }

    @Override
    public String getType() {
        return AroundType.SCENIC;
    }
}
