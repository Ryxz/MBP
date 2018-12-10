package com.yuyue.mbp.global.utils;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.yuyue.mbp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renyx on 2018/7/26
 */
public class ChartUtil {

    public static void showChart(Context context, LineChart lineChart, List<String> xDataList, List<Entry> yDataList
                                ,String curveLable,String unitName) {
        lineChart.setData(setLineData(context,xDataList,yDataList,curveLable));
        //折线图上添加边框
        lineChart.setDrawBorders(true);
        //曲线描述—标题
//        lineChart.setDescriptionPosition(10f,-1);
//        lineChart.setDescription(title);
//        lineChart.setDescriptionTextSize(16f);
//        lineChart.setDescriptionColor(context.getApplicationContext().getResources().getColor(R.color.normal_text));
        //没有数据时显示
        lineChart.setNoDataTextDescription("暂无数据");
        //是否显示表格颜色
        lineChart.setDrawGridBackground(false);
        //禁止绘制图标边框的线
        lineChart.setDrawBorders(false);
        //是否启动触摸相应
        lineChart.setTouchEnabled(true);
        //拖拽
        lineChart.setDragEnabled(true);
        //缩放
        lineChart.setScaleEnabled(true);

        lineChart.setBackgroundColor(context.getApplicationContext().getResources().getColor(R.color.white));
        //图例对象
        Legend mlegend = lineChart.getLegend();

        mlegend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        //图例设置为方块
        mlegend.setForm(Legend.LegendForm.SQUARE);
        mlegend.setFormSize(8f);

        mlegend.setTextColor(context.getApplicationContext().getResources().getColor(R.color.normal_text));
        mlegend.setTextSize(8f);
        mlegend.setEnabled(true);
        //隐藏右侧y轴
        lineChart.getAxisRight().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        //显示x轴上的刻度值
        xAxis.setDrawLabels(true);
        //x轴的数据显示在报表的下方
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置不从x轴发出纵向直线
        xAxis.setDrawGridLines(false);
        //执行动画x轴
        lineChart.animateX(2500);

    }
    public static void showChart(Context context, LineChart lineChart, List<String> xDataList, List<Entry> y1DataList,
                                 List<Entry> y2DataList,String curveLable1,String curveLable2,String unitName) {
        lineChart.setData(setLineData(context,xDataList,y1DataList,y2DataList,curveLable1,curveLable2));
        lineChart.setDrawBorders(true);
//        lineChart.setDescription(title);
//        lineChart.setDescriptionTextSize(16f);
//        lineChart.setDescriptionColor(context.getApplicationContext().getResources().getColor(R.color.normal_text));
        lineChart.setNoDataTextDescription("暂无数据");
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        lineChart.setBackgroundColor(context.getApplicationContext().getResources().getColor(R.color.white));
        Legend mlegend = lineChart.getLegend();

        mlegend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        mlegend.setForm(Legend.LegendForm.SQUARE);
        mlegend.setFormSize(8f);

        mlegend.setTextColor(context.getApplicationContext().getResources().getColor(R.color.normal_text));
        mlegend.setTextSize(8f);
        mlegend.setEnabled(true);
        lineChart.getAxisRight().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        lineChart.animateX(2500);

    }

    private static LineData setLineData(Context context,List<String> xDataList,List<Entry> yDataList,
                                         String curveLable) {
        //LineDataSet一条曲线数据对象
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        //y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yDataList,curveLable);
        //不显示坐标点的数据
        lineDataSet.setDrawValues(false);
        //显示坐标点的的小圆点
        lineDataSet.setDrawCircles(true);
        //显示小圆点大小
        lineDataSet.setCircleSize(4f);
        //定位线
        lineDataSet.setHighlightEnabled(true);
        //线宽
        lineDataSet.setLineWidth(2.0f);
        //显示颜色
        lineDataSet.setColor(context.getApplicationContext().getResources().getColor(R.color.level_5));
        //圆点的颜色
        lineDataSet.setCircleColor(context.getApplicationContext().getResources().getColor(R.color.level_5));
        //设置坐标点的颜色
        lineDataSet.setFillColor(context.getApplicationContext().getResources().getColor(R.color.level_5));
        //设置坐标点为空心圆环
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setFillAlpha(65);
        //坐标轴在左侧
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //设置每条曲线图例标签名
//        lineDataSet.setLabel("Systolic");
        lineDataSet.setValueTextSize(14f);
        //曲线弧度（0.05f-1f,默认0.2f）
        lineDataSet.setCubicIntensity(0.2f);
        //设置为曲线显示
        lineDataSet.setDrawCubic(true);
        lineDataSets.add(lineDataSet);

        LineData lineData = new LineData(xDataList,lineDataSets);

        return lineData;
    }

    private static LineData setLineData(Context context,List<String> xDataList,List<Entry> y1DataList,
                                        List<Entry> y2DataList, String curveLable1,String curveLable2) {
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        LineDataSet lineDataSet1 = new LineDataSet(y1DataList,curveLable1);
        LineDataSet lineDataSet2 = new LineDataSet(y2DataList,curveLable2);
        lineDataSet1.setDrawValues(false);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setCircleSize(4f);
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet1.setLineWidth(2.0f);
        lineDataSet1.setColor(context.getApplicationContext().getResources().getColor(R.color.level_3));
        lineDataSet1.setCircleColor(context.getApplicationContext().getResources().getColor(R.color.level_3));
        lineDataSet1.setFillColor(context.getApplicationContext().getResources().getColor(R.color.level_3));
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setFillAlpha(65);
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setValueTextSize(14f);
        lineDataSet1.setCubicIntensity(0.2f);

        lineDataSet2.setDrawValues(false);
        lineDataSet2.setDrawCircles(true);
        lineDataSet2.setCircleSize(4f);
        lineDataSet2.setHighlightEnabled(true);
        lineDataSet2.setLineWidth(2.0f);
        lineDataSet2.setColor(context.getApplicationContext().getResources().getColor(R.color.level_1));
        lineDataSet2.setCircleColor(context.getApplicationContext().getResources().getColor(R.color.level_1));
        lineDataSet2.setFillColor(context.getApplicationContext().getResources().getColor(R.color.level_1));
        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.setFillAlpha(65);
        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
//        lineDataSet1.setLabel("Systolic");
        lineDataSet1.setValueTextSize(14f);
        lineDataSet1.setCubicIntensity(0.2f);

        //设置为曲线显示
        lineDataSet1.setDrawCubic(true);
        lineDataSet2.setDrawCubic(true);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);


        LineData lineData = new LineData(xDataList,lineDataSets);

        return lineData;
    }


    /**
     * 点击弹出数据框
     * （未添加）
     */
//    class CustomMakerView extends MarkerView {
//        /**
//         * Constructor. Sets up the MarkerView with a custom layout resource.
//         *
//         * @param context
//         * @param layoutResource the layout resource to use for the MarkerView
//         */
//        public CustomMakerView(Context context, int layoutResource) {
//            super(context, layoutResource);
//        }
//
//        @Override
//        public void refreshContent(Entry e, Highlight highlight) {
//
//        }
//
//        @Override
//        public int getXOffset(float xpos) {
//            return 0;
//        }
//
//        @Override
//        public int getYOffset(float ypos) {
//            return 0;
//        }
//    }
}
