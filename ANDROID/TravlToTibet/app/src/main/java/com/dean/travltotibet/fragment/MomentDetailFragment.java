package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.MomentDetailActivity;
import com.dean.travltotibet.model.ImageFile;
import com.dean.travltotibet.model.Moment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.like.LikeButton;
import com.dean.travltotibet.ui.like.OnLikeListener;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.PicassoTools;
import com.squareup.picasso.MemoryPolicy;

import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 5/23/16.
 */
public class MomentDetailFragment extends Fragment {

    private View root;

    private Moment moment;

    private MomentDetailActivity momentDetailActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.moment_detail_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        momentDetailActivity = (MomentDetailActivity) getActivity();
        moment = momentDetailActivity.getMoment();

        initContentContent();
    }

    private void initContentContent() {
        if (moment == null) {
            return;
        }

        // content
        TextView mContentText = (TextView) root.findViewById(R.id.content_text);
        if (!TextUtils.isEmpty(moment.getContent())) {
            mContentText.setVisibility(View.VISIBLE);
            mContentText.setText(moment.getContent());
        } else {
            mContentText.setVisibility(View.GONE);
        }

        // user name
        TextView mUserName = (TextView) root.findViewById(R.id.user_name);
        mUserName.setText(moment.getUserName());
        if (UserInfo.MALE.equals(moment.getUserGender())) {
            mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
        } else {
            mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
        }

        // publish time
        TextView mPublishTime = (TextView) root.findViewById(R.id.publish_time);
        String createTime = DateUtil.getTimeGap(moment.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        mPublishTime.setText(createTime);

        // user icon
        CircleImageView mUserIcon = (CircleImageView) root.findViewById(R.id.user_icon);
        if (!TextUtils.isEmpty(moment.getUserIcon())) {
            PicassoTools.getPicasso()
                    .load(moment.getUserIcon())
                    .resizeDimen(R.dimen.profile_icon_size, R.dimen.profile_icon_size)
                    .centerInside()
                    .error(R.drawable.gray_profile)
                    .config(Bitmap.Config.RGB_565)
                    .into(mUserIcon);
        } else {
            mUserIcon.setImageResource(R.drawable.gray_profile);
        }

        // share image
        ImageView mShareImage = (ImageView) root.findViewById(R.id.share_image);
        mShareImage.setVisibility(View.GONE);
        ImageFile imageFile = moment.getImageFile();
        if (imageFile != null) {
            mShareImage.setVisibility(View.VISIBLE);
            PicassoTools.getPicasso()
                    .load(imageFile.getImage1().getFileUrl(getActivity()))
                    .placeholder(R.color.less_light_gray)
                    .error(R.color.light_gray)
                    .into(mShareImage);
        }

        // watch
        TextView mWatch = (TextView) root.findViewById(R.id.watch);
        if (moment.getWatch() != null) {
            mWatch.setText(moment.getWatch().intValue() + "");
        }

        // location
        TextView mLocationText = (TextView) root.findViewById(R.id.location_text);
        if (!TextUtils.isEmpty(moment.getLocation())) {
            mLocationText.setVisibility(View.VISIBLE);
            mLocationText.setText(moment.getLocation());
        } else {
            mLocationText.setVisibility(View.GONE);
        }
    }

    public void likeAction(final Moment moment) {

        final int watch = moment.getWatch().intValue();

        moment.increment("watch");
        moment.update(getActivity(), new UpdateListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });

        moment.setWatch(watch + 1);

        TextView mWatch = (TextView) root.findViewById(R.id.watch);
        mWatch.setText(String.valueOf(moment.getWatch()));

        SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        sharedPreferences.edit().putString(moment.getObjectId(), moment.getObjectId()).commit();
    }

    public void unlikeAction(final Moment moment) {
        final int watch = moment.getWatch().intValue();

        moment.increment("watch", -1);
        moment.update(getActivity(), new UpdateListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });

        moment.setWatch(watch - 1);

        TextView mWatch = (TextView) root.findViewById(R.id.watch);
        mWatch.setText(String.valueOf(moment.getWatch()));

        SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        sharedPreferences.edit().remove(moment.getObjectId()).commit();
    }
}
