package com.dean.travltotibet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ImagePickerActivity;
import com.dean.travltotibet.activity.RoadInfoDetailActivity;
import com.dean.travltotibet.adapter.ImagePickAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.model.RoadInfo;
import com.dean.travltotibet.ui.HorizontalItemDecoration;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by DeanGuo on 4/9/16.
 */
public class RoadInfoDetailFragment extends BaseRefreshFragment {

    private View root;

    private RoadInfo roadInfo;

    private RoadInfoDetailActivity roadInfoDetailActivity;

    private ImagePickAdapter imagePickAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.road_info_detail_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        roadInfoDetailActivity = (RoadInfoDetailActivity) getActivity();
        roadInfo = roadInfoDetailActivity.getRoadInfo();
        onRefresh();
    }

    private void initImageContent() {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.picker_image_list_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new HorizontalItemDecoration(ScreenUtil.dip2px(getActivity(), 2)));
        recyclerView.setHasFixedSize(true);
        imagePickAdapter = new ImagePickAdapter(getActivity());
        imagePickAdapter.setAddImageListener(new ImagePickAdapter.AddImageListener() {
            @Override
            public void onAddImage() {
                Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
                intent.putExtra(IntentExtra.INTENT_IMAGE_SELECTED, imagePickAdapter.getData().size());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(imagePickAdapter);

        if (roadInfo.getImageFile() == null) {
            recyclerView.setVisibility(View.GONE);
            return;
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            imagePickAdapter.setImageFile(roadInfo.getImageFile());
            imagePickAdapter.addData(roadInfo.getImageFile().getImageUrls(getActivity()));
        }
    }

   // title
    private void initTitleContent() {
        TextView title = (TextView) root.findViewById(R.id.title_text);
        title.setText(roadInfo.getTitle());
        setCopyView(title);
    }

    // 内容
    private void initContentContent() {

        TextView time = (TextView) root.findViewById(R.id.publish_time);
        Date publishDate = DateUtil.parse(roadInfo.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        String publishTime = DateUtil.formatDate(publishDate, Constants.YYYY_MM_DD);
        time.setText(publishTime);

        TextView content = (TextView) root.findViewById(R.id.content_text);
        content.setText(roadInfo.getContent());
        setCopyView(content);
    }

    private void setCopyView (final TextView textView) {
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                cmb.setText(textView.getText().toString());
                Toast.makeText(getActivity(), getString(R.string.copy_success), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();
        toDo(ON_LOADING, 800);
    }

    @Override
    public void onLoading() {
        super.onLoading();
        BmobQuery<RoadInfo> query = new BmobQuery<RoadInfo>();
        query.include("imageFile");
        query.getObject(getActivity(), roadInfo.getObjectId(), new GetListener<RoadInfo>() {

            @Override
            public void onSuccess(RoadInfo object) {
                roadInfo = object;
                initTitleContent();
                initContentContent();
                initImageContent();
            }

            @Override
            public void onFailure(int code, String arg0) {
            }
        });
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();
    }

    @Override
    public void LoadingError() {
        super.LoadingError();
    }
}
