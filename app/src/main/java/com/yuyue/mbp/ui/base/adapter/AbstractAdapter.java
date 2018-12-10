package com.yuyue.mbp.ui.base.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renyx on 2018/7/2
 */
public abstract class AbstractAdapter <T> extends BaseAdapter {
    private static final String TAG = AbstractAdapter.class.getSimpleName();
    private List<T> data;
    private LayoutInflater mInflater;
    private int itemLayoutId;
    private Constructor<? extends BaseViewHolder> constructor;

    public AbstractAdapter(Context mContext, Class<? extends BaseViewHolder> clazz, int itemLayoutId) {
        data = new ArrayList<T>();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemLayoutId = itemLayoutId;
        try {
            constructor = clazz.getConstructor(View.class);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
    public AbstractAdapter(Context context, List<T> data){
        this.data = data;
        this.mInflater = LayoutInflater.from(context);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemLayoutId = itemLayoutId;
        this.mInflater = LayoutInflater.from(context);
    }
    protected AbstractAdapter() {
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(itemLayoutId, null);
            try {
                holder = constructor.newInstance(convertView);
                convertView.setTag(holder);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }

        return convertView;
    }
    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}

