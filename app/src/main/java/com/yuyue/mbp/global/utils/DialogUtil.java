package com.yuyue.mbp.global.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.yuyue.mbp.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Renyx on 2018/6/19
 * 对话框通用方法
 */
public class DialogUtil {
    private static final Calendar mCalendar = Calendar.getInstance();

    private OnDateSetListener mOnDateSetListener;
    private View.OnClickListener mOnClickListener;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public DialogUtil(Context mContext) {
        this(mContext, null);
    }

    public DialogUtil(Context mContext, OnDateSetListener mOnDateSetListener) {
        this.mContext = mContext;
        this.mOnDateSetListener = mOnDateSetListener;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 显示日期选择对话框
     * @param isStart 是否为开始日期对话框
     */
    public void showDateDialog(final boolean isStart, final Date initDate) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (mOnDateSetListener != null) {
                    mOnDateSetListener.onDateSet(mCalendar.getTime(), isStart);
                }
            }
        };
        mCalendar.setTime(initDate);

        new DatePickerDialog(mContext, listener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

//    /**
//     * 显示确认删除对话框
//     * @param titleId
//     */
//    public void showDeleteConfirmDialog(int titleId) {
//        View dialogView = mLayoutInflater.inflate(R.layout.delete_dialog, null);
//        final Dialog mDialog = new Dialog(mContext);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.setContentView(dialogView);
//
//        TextView mTitle = (TextView) dialogView.findViewById(R.id.tv_title);
//        mTitle.setText(titleId);
//
//        Button mOk = (Button) dialogView.findViewById(R.id.btn_ok);
//        mOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnClickListener.onClick(v);
//                mDialog.dismiss();
//            }
//        });
//
//        Button mCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
//        mCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//            }
//        });
//
//        mDialog.show();
//    }

    public void showAlertDialog(int titleId, int messageId,
                                DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancelClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(messageId).setTitle(titleId)
                .setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(R.string.ok, onOkClickListener);
        if (onCancelClickListener == null) {
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            builder.setNegativeButton(R.string.cancel, onCancelClickListener);
        }
        builder.create().show();
    }

    public void setOnDateSetListener(OnDateSetListener mOnDateSetListener) {
        this.mOnDateSetListener = mOnDateSetListener;
    }

    public void setOnOkClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnDateSetListener {
        public void onDateSet(Date date, boolean isStart);
    }
}
