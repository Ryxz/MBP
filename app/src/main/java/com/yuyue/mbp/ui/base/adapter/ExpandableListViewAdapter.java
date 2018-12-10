package com.yuyue.mbp.ui.base.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yuyue.mbp.R;
import com.yuyue.mbp.entity.BPMeasurement;
import com.yuyue.mbp.global.utils.CommonUtil;
import com.yuyue.mbp.global.utils.DateUtil;

import java.util.List;

/**
 * Created by Renyx on 2018/7/19
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupData;
    private List<List<BPMeasurement>> childData;

    public ExpandableListViewAdapter(Context context, List<String> groupData, List<List<BPMeasurement>> childData) {
        this.context = context;
        this.groupData = groupData;
        this.childData = childData;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        int count = 0;
        if (groupData != null) {
            count =groupData.size();
        }
        return count;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        if (childData != null) {
            count = childData.get(groupPosition).size();
        }
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_data_bp_top,null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mMeasureTimemonth = (TextView) convertView.findViewById(R.id.item_data_month);
            convertView.setTag(R.id.tag_top, groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag(R.id.tag_top);
        }
//        if (isExpanded) {
//
//        } else {
//
//        }
        groupViewHolder.mMeasureTimemonth.setText(groupData.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_data_bp_bottom, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.mMeasureTimeWeek = (TextView) convertView.findViewById(R.id.date_week);
            childViewHolder.mMeasureTime = (TextView) convertView.findViewById(R.id.date_time);
            childViewHolder.mSbp = (TextView) convertView.findViewById(R.id.item_data_sbp);
            childViewHolder.mDbp = (TextView) convertView.findViewById(R.id.item_data_dbp);
            childViewHolder.mPulse = (TextView) convertView.findViewById(R.id.item_data_pulse);
            childViewHolder.mView = (View) convertView.findViewById(R.id.item_data_daily_view);
            childViewHolder.mMeasureResultText = (TextView) convertView.findViewById(R.id.item_data_measure_text);
            childViewHolder.mMeasureTimeDay = (TextView) convertView.findViewById(R.id.data_day);

            convertView.setTag(R.id.tag_bottom, childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag(R.id.tag_bottom);
        }
        String level = CommonUtil.getPressureLevel(childData.get(groupPosition).get(childPosition).getSbp(), childData.get(groupPosition).get(childPosition).getDbp());

        childViewHolder.mMeasureTimeWeek.setText(DateUtil.formatCustomDate(childData.get(groupPosition).get(childPosition).getMeasureTime(), "E"));
        childViewHolder.mMeasureTime.setText(DateUtil.formatCustomDate(childData.get(groupPosition).get(childPosition).getMeasureTime(), "HH:mm"));
        childViewHolder.mSbp.setText(String.valueOf(childData.get(groupPosition).get(childPosition).getSbp()));
        childViewHolder.mDbp.setText(String.valueOf(childData.get(groupPosition).get(childPosition).getDbp()));
        childViewHolder.mPulse.setText(String.valueOf(childData.get(groupPosition).get(childPosition).getPulseRate()));
        childViewHolder.mMeasureTimeDay.setText(DateUtil.formatCustomDate(childData.get(groupPosition).get(childPosition).getMeasureTime(), "dd"));

        if (level == BPMeasurement.PRESSURE_LEVEL_SEVERE) {
            childViewHolder.mMeasureResultText.setText(CommonUtil.getLevelText(childData.get(groupPosition).get(childPosition).getLevel()));
            childViewHolder.mMeasureResultText.setTextColor(context.getResources().getColor(R.color.level_5));
            childViewHolder.mView.setBackgroundColor(context.getResources().getColor(R.color.level_5));
        } else if (level == BPMeasurement.PRESSURE_LEVEL_MODERATE) {
            childViewHolder.mMeasureResultText.setText(CommonUtil.getLevelText(childData.get(groupPosition).get(childPosition).getLevel()));
            childViewHolder.mMeasureResultText.setTextColor(context.getResources().getColor(R.color.level_4));
            childViewHolder.mView.setBackgroundColor(context.getResources().getColor(R.color.level_4));
        } else if (level == BPMeasurement.PRESSURE_LEVEL_LIGHT) {
            childViewHolder.mMeasureResultText.setText(CommonUtil.getLevelText(childData.get(groupPosition).get(childPosition).getLevel()));
            childViewHolder.mMeasureResultText.setTextColor(context.getResources().getColor(R.color.level_3));
            childViewHolder.mView.setBackgroundColor(context.getResources().getColor(R.color.level_3));
        } else if (level == BPMeasurement.PRESSURE_LEVEL_EDGE) {
            childViewHolder.mMeasureResultText.setText(CommonUtil.getLevelText(childData.get(groupPosition).get(childPosition).getLevel()));
            childViewHolder.mMeasureResultText.setTextColor(context.getResources().getColor(R.color.level_2));
            childViewHolder.mView.setBackgroundColor(context.getResources().getColor(R.color.level_2));
        } else if (level == BPMeasurement.PRESSURE_LEVEL_NORMAL) {
            childViewHolder.mMeasureResultText.setText(CommonUtil.getLevelText(childData.get(groupPosition).get(childPosition).getLevel()));
            childViewHolder.mMeasureResultText.setTextColor(context.getResources().getColor(R.color.level_1));
            childViewHolder.mView.setBackgroundColor(context.getResources().getColor(R.color.level_1));
        } else if (level == BPMeasurement.PRESSURE_LEVEL_IDEAL) {
            childViewHolder.mMeasureResultText.setText(CommonUtil.getLevelText(childData.get(groupPosition).get(childPosition).getLevel()));
            childViewHolder.mMeasureResultText.setTextColor(context.getResources().getColor(R.color.level_0));
            childViewHolder.mView.setBackgroundColor(context.getResources().getColor(R.color.level_0));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
    class GroupViewHolder {
        TextView mMeasureTimemonth;
    }

    class ChildViewHolder {
        TextView mMeasureTimeWeek;
        TextView mMeasureTime;
        TextView mSbp;
        TextView mDbp;
        TextView mPulse;
        TextView mMeasureResultText;
        View mView;
        TextView mMeasureTimeDay;
    }
}
