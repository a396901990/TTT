package com.dean.travltotibet.fragment;


import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.Moment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 5/24/16.
 */
public class MomentPublishFragment extends MomentShowBaseFragment {

    @Override
    protected void getMoments(final int actionType) {
        moments = new ArrayList<>();

        BmobQuery<Moment> query = new BmobQuery<>();
        query.addWhereRelatedTo("Moment", new BmobPointer(TTTApplication.getUserInfo()));
        query.order("-createdAt");
        query.include("imageFile");

        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(mAdapter.getCount());
        }

        // 设置每页数据个数
        query.setLimit(limit);

        query.findObjects(getActivity(), new FindListener<Moment>() {
            @Override
            public void onSuccess(List<Moment> list) {
                moments = (ArrayList<Moment>) list;

                if (list.size() == 0 && actionType == STATE_MORE) {
                    loadMoreListView.onNoMoreDate();
                } else {
                    if (actionType == STATE_REFRESH) {
                        toDo(LOADING_SUCCESS, 0);
                    } else {
                        toDo(LOADING_MORE_SUCCESS, 0);
                    }
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
    public void onRefresh() {
        if (TTTApplication.getUserInfo() == null) {
            finishRefresh();
            return;
        }
        super.onRefresh();
    }

    @Override
    public boolean isPersonal() {
        return true;
    }

}
