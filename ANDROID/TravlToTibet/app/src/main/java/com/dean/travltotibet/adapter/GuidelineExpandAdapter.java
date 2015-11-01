package com.dean.travltotibet.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;

public class GuidelineExpandAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater = null;
    private List<GroupGuidelineEntity> groupList;

    /**
     * 构造方法
     */
    public GuidelineExpandAdapter(Context context) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 设置数据
     *
     * @param groupList
     */
    public void setData(List<GroupGuidelineEntity> groupList) {
        this.groupList = groupList;
        notifyDataSetChanged();
    }

    /**
     * 返回一级Item总数
     */
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return groupList.size();
    }

    /**
     * 返回二级Item总数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupList.get(groupPosition).getChildList() == null) {
            return 0;
        } else {
            return groupList.get(groupPosition).getChildList().size();
        }
    }

    /**
     * 获取一级Item内容
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    /**
     * 获取二级Item内容
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        GroupViewHolder holder = new GroupViewHolder();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.route_timeline_group_item, null);
        }

        ImageView indicatorImage = (ImageView) convertView.findViewById(R.id.title_indicator);
        indicatorImage.setImageResource(R.drawable.dark_orange_circle_blank);
        if (!isExpanded) {
            indicatorImage.setImageResource(R.drawable.dark_orange_circle);
        }

        holder.titleName = (TextView) convertView
                .findViewById(R.id.title_name);

        holder.titleName.setText(groupList.get(groupPosition).getTitleName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        ChildGuidelineEntity entity = (ChildGuidelineEntity) getChild(groupPosition,
                childPosition);
        if (convertView != null) {
            viewHolder = (ChildViewHolder) convertView.getTag();
        } else {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.route_timeline_child_item, null);

            // 标题海拔
            viewHolder.titleHeight = (TextView) convertView.findViewById(R.id.title_height);
            // 标题里程碑
            viewHolder.titleMileage = (TextView) convertView.findViewById(R.id.title_milestone);
            // 详细攻略内容
            viewHolder.contentDetail = (TextView) convertView.findViewById(R.id.content_detail);
        }

        viewHolder.contentDetail.setText(entity.getRouteDetail());
        viewHolder.titleHeight.setText(entity.getTitleHeight());
        viewHolder.titleMileage.setText(entity.getTitleMilestone());

        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }

    private class GroupViewHolder {
        TextView titleName;
    }

    private class ChildViewHolder {
        TextView titleHeight;
        TextView titleMileage;
        public TextView contentDetail;
    }

    /**
     * timeline list 一级Item实体类
     */
    public static class GroupGuidelineEntity {

        private String titleName;
        private String titleHeight;
        private String titleMilestone;
        /**
         * 二级Item数据列表 *
         */
        private List<ChildGuidelineEntity> childList;

        public String getTitleMilestone() {
            return titleMilestone;
        }

        public void setTitleMilestone(String titleMilestone) {
            this.titleMilestone = titleMilestone;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public String getTitleHeight() {
            return titleHeight;
        }

        public void setTitleHeight(String titleHeight) {
            this.titleHeight = titleHeight;
        }

        public List<ChildGuidelineEntity> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildGuidelineEntity> childList) {
            this.childList = childList;
        }


    }

    /**
     * timeline list 二级Item实体类
     */
    public static class ChildGuidelineEntity {
        /**
         * 详细内容 *
         */
        private String routeDetail;

        /**
         * 标题海拔
         */
        private String titleHeight;

        /**
         * 标题里程碑
         */
        private String titleMilestone;

        public String getRouteDetail() {
            return routeDetail;
        }

        public void setRouteDetail(String routeDetail) {
            this.routeDetail = routeDetail;
        }

        public String getTitleMilestone() {
            return titleMilestone;
        }

        public void setTitleMilestone(String titleMilestone) {
            this.titleMilestone = titleMilestone;
        }

        public String getTitleHeight() {
            return titleHeight;
        }

        public void setTitleHeight(String titleHeight) {
            this.titleHeight = titleHeight;
        }

    }
}
