package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.model.ImageFile;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.tagview.Tag;
import com.dean.travltotibet.ui.tagview.TagView;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.PicassoTools;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 3/8/16.
 */
public class TeamRequestListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<TeamRequest> mData;

    public TeamRequestListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private boolean isPersonal = false;

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TeamRequestViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_show_request_list_item, parent, false);
            holder = new TeamRequestViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (TeamRequestViewHolder) convertView.getTag();
        }

        final TeamRequest request = mData.get(position);

        // 审核未通过和等待审核需要显示提示框
        if (TeamRequest.NO_PASS_STATUS.equals(request.getStatus())) {
            holder.mWarningView.setVisibility(View.VISIBLE);
            holder.mWarningText.setText(mContext.getString(R.string.warning_no_pass));
        } else if (TeamRequest.WAIT_STATUS.equals(request.getStatus())) {
            holder.mWarningView.setVisibility(View.VISIBLE);
            holder.mWarningText.setText(mContext.getString(R.string.warning_wait_pass));
        } else {
            holder.mWarningView.setVisibility(View.GONE);
        }

        // content
        holder.mContentText.setText(request.getContent());

        // destination, type, date
        setTag(holder, request);

        // user name
        holder.mUserName.setText(request.getUserName());
        if (UserInfo.MALE.equals(request.getUserGender())) {
            holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
        } else {
            holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
        }

        // user icon
        if (!TextUtils.isEmpty(request.getUserIcon())) {
            PicassoTools.getPicasso()
                    .load(request.getUserIcon())
                    .resizeDimen(R.dimen.profile_icon_size, R.dimen.profile_icon_size)
                    .centerInside()
                    .error(R.drawable.gray_profile)
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.mUserIcon);
        } else {
            holder.mUserIcon.setImageResource(R.drawable.gray_profile);
        }

        holder.mWatch.setText(request.getWatch() + "");

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, TeamShowRequestDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, request);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, isPersonal);
                ((Activity)mContext).startActivityForResult(intent, BaseActivity.UPDATE_REQUEST);
            }
        });

        // 设置图片视图
        setUpImageContent(holder, position);

        return convertView;
    }

    private void setTag(TeamRequestViewHolder holder, TeamRequest request) {

        ArrayList<Tag> teamFilterTags = new ArrayList<>();

        Tag tagDest = new Tag(request.getDestination());
        tagDest.tagTextSize = 10f;
        tagDest.layoutColor = TTTApplication.getMyColor(R.color.route_color);

        Tag tagDate = new Tag(request.getDate());
        tagDate.tagTextSize = 10f;
        tagDate.layoutColor = TTTApplication.getMyColor(R.color.month_color);

        Tag tagType = new Tag(request.getType());
        tagType.tagTextSize = 10f;
        tagType.layoutColor = TTTApplication.getMyColor(R.color.type_color);

        teamFilterTags.add(tagDest);
        teamFilterTags.add(tagType);
        teamFilterTags.add(tagDate);
        holder.tagView.addTags(teamFilterTags);
    }

    private void setUpImageContent(TeamRequestViewHolder holder, int position) {
        final TeamRequest request = mData.get(position);
        resetImage(holder);

        ImageFile imageFile = request.getImageFile();
        if (imageFile != null) {
            holder.imageContent.setVisibility(View.VISIBLE);
            // image 1
            ImageView image1 = (ImageView) holder.imageContent.findViewById(R.id.image_view_1);
            ImageFile.setThumbnailImage(mContext, imageFile.getThumbnail1(), image1);
//            ImageFile.setImagePreview(mContext, imageFile, image1, position);

            // image 2
            ImageView image2 = (ImageView) holder.imageContent.findViewById(R.id.image_view_2);
            ImageFile.setThumbnailImage(mContext, imageFile.getThumbnail2(), image2);
//            ImageFile.setImagePreview(mContext, imageFile, image2, position);

            // image 3
            ImageView image3 = (ImageView) holder.imageContent.findViewById(R.id.image_view_3);
            ImageFile.setThumbnailImage(mContext, imageFile.getThumbnail3(), image3);
//            ImageFile.setImagePreview(mContext, imageFile, image3, position);
        } else {
            holder.imageContent.setVisibility(View.GONE);
        }

    }

    public void resetImage(TeamRequestViewHolder holder) {
        holder.imageContent.findViewById(R.id.image_view_1).setVisibility(View.INVISIBLE);
        holder.imageContent.findViewById(R.id.image_view_2).setVisibility(View.INVISIBLE);
        holder.imageContent.findViewById(R.id.image_view_3).setVisibility(View.INVISIBLE);
    }

    public void setData(ArrayList<TeamRequest> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<TeamRequest> addDatas) {
        mData.addAll(addDatas);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mData == null) {
            return;
        } else {
            mData = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    public class TeamRequestViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView mContentText;

        private TextView mUserName;
        private CircleImageView mUserIcon;

        private TextView mWatch;
        private View mWarningView;
        private TextView mWarningText;

        private View imageContent;

        private TagView tagView;

        public TeamRequestViewHolder(View itemView) {
            mContentText = (TextView) itemView.findViewById(R.id.message_text);

            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mUserIcon = (CircleImageView) itemView.findViewById(R.id.user_icon);

            mWatch = (TextView) itemView.findViewById(R.id.watch);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);

            mWarningView = itemView.findViewById(R.id.warning_view);
            mWarningText = (TextView) itemView.findViewById(R.id.warning_text);

            imageContent = itemView.findViewById(R.id.image_content);

            tagView = (TagView) itemView.findViewById(R.id.tags_content);
            tagView.setTagMargin(3f);
            tagView.setLineMargin(3f);
            tagView.setTextPaddingTop(1f);
            tagView.setTexPaddingBottom(1f);
            tagView.setTextPaddingLeft(4f);
            tagView.setTextPaddingRight(4f);
        }
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

}
