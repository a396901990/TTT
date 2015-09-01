package com.dean.travltotibet.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dean.travltotibet.R;

public class TimelineExpandAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater = null;
    private List<GroupTimelineEntity> groupList;

    /**
     * 构造方法
     */
    public TimelineExpandAdapter(Context context,
                                 List<GroupTimelineEntity> group_list) {
        this.groupList = group_list;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        // TODO Auto-generated method stub
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
            convertView = inflater.inflate(R.layout.group_status_item, null);
        }
        holder.titleName = (TextView) convertView
                .findViewById(R.id.title_name);
        holder.titleMarkName = (TextView) convertView
                .findViewById(R.id.title_mark_name);

        holder.titleName.setText(groupList.get(groupPosition).getTitleName());
        holder.titleMarkName.setText(groupList.get(groupPosition).getTitleMarkName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        ChildTimelineEntity entity = (ChildTimelineEntity) getChild(groupPosition,
                childPosition);
        if (convertView != null) {
            viewHolder = (ChildViewHolder) convertView.getTag();
        } else {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.child_status_item, null);
            viewHolder.contentDetail = (TextView) convertView
                    .findViewById(R.id.content_name);
        }
        viewHolder.contentDetail.setText(entity.getRouteDetail());

        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }

    private class GroupViewHolder {
        TextView titleMarkName;
        TextView titleName;
    }

    private class ChildViewHolder {
        public TextView contentDetail;
    }

    /**
     * timeline list 一级Item实体类
     */
    public static class GroupTimelineEntity {
        private String titleName;
        private String titleMarkName;
        /**
         * 二级Item数据列表 *
         */
        private List<ChildTimelineEntity> childList;

        public String getTitleName() {
            return titleName;
        }

        public String getTitleMarkName() {
            return titleMarkName;
        }

        public void setTitleMarkName(String titleMarkName) {
            this.titleMarkName = titleMarkName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public List<ChildTimelineEntity> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildTimelineEntity> childList) {
            this.childList = childList;
        }

    }

    /**
     * timeline list 二级Item实体类
     */
    public static class ChildTimelineEntity {
        /**
         * 详细内容 *
         */
        private String routeDetail;

        public String getRouteDetail() {
            return routeDetail;
        }

        public void setRouteDetail(String routeDetail) {
            this.routeDetail = routeDetail;
        }

    }
}
