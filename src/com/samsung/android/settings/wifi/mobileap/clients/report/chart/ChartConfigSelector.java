package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.drawable.Drawable;

import com.android.settings.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ChartConfigSelector {
    public static AbsRoundBarRenderer getBarChartRenderer(
            Context context,
            CategoryViewConstants$CategoryType categoryViewConstants$CategoryType,
            BarChart barChart) {
        int ordinal = categoryViewConstants$CategoryType.ordinal();
        if (ordinal == 8) {
            DataBarRenderer dataBarRenderer =
                    new DataBarRenderer(
                            context, barChart, barChart.mAnimator, barChart.mViewPortHandler);
            dataBarRenderer.mSectionEntrySize =
                    dataBarRenderer.mSectionEntryCount * dataBarRenderer.mEntrySize;
            dataBarRenderer.mMissingIndex = -1;
            dataBarRenderer.mRenderPaint.setColor(
                    context.getResources()
                            .getColor(R.color.dw_data_type_basic_color, context.getTheme()));
            dataBarRenderer.mRadius = 40.0f;
            return dataBarRenderer;
        }
        if (ordinal != 9) {
            return null;
        }
        DataBarRendererMonthly dataBarRendererMonthly =
                new DataBarRendererMonthly(
                        context, barChart, barChart.mAnimator, barChart.mViewPortHandler);
        dataBarRendererMonthly.mSectionEntrySize =
                dataBarRendererMonthly.mSectionEntryCount * dataBarRendererMonthly.mEntrySize;
        dataBarRendererMonthly.mMissingIndex = -1;
        dataBarRendererMonthly.mRenderPaint.setColor(
                WifiApSettingsUtils.convertToColorRGB("#FF42A0FE"));
        dataBarRendererMonthly.mRadius = 40.0f;
        return dataBarRendererMonthly;
    }

    public static AbsXAxisRenderer getXAxisRenderer(
            Context context,
            CategoryViewConstants$CategoryType categoryViewConstants$CategoryType,
            BarChart barChart) {
        YAxis.AxisDependency axisDependency = YAxis.AxisDependency.RIGHT;
        int ordinal = categoryViewConstants$CategoryType.ordinal();
        if (ordinal != 3 && ordinal != 4) {
            if (ordinal != 5) {
                return new WeeklyXAxisRenderer(
                        context,
                        barChart.mXAxis,
                        barChart.getTransformer(axisDependency),
                        barChart.mViewPortHandler);
            }
            WeeklyExtendedXAxisRenderer weeklyExtendedXAxisRenderer =
                    new WeeklyExtendedXAxisRenderer(
                            context,
                            barChart.mXAxis,
                            barChart.getTransformer(axisDependency),
                            barChart.mViewPortHandler);
            weeklyExtendedXAxisRenderer.mOffsetLabelPosition =
                    context.getResources().getDimension(R.dimen.weekly_chart_x_label_width) / 2.0f;
            weeklyExtendedXAxisRenderer.mLabelMarginTop =
                    context.getResources().getDimension(R.dimen.dashboard_chart_x_label_margin_top);
            weeklyExtendedXAxisRenderer.mExtraLabel = ApnSettings.MVNO_NONE;
            return weeklyExtendedXAxisRenderer;
        }
        WeeklyIconXAxisRenderer weeklyIconXAxisRenderer =
                new WeeklyIconXAxisRenderer(
                        context,
                        barChart.mXAxis,
                        barChart.getTransformer(YAxis.AxisDependency.LEFT),
                        barChart.mViewPortHandler);
        Drawable drawable =
                weeklyIconXAxisRenderer
                        .mContext
                        .getResources()
                        .getDrawable(
                                R.drawable.ic_wifi_ap_dw_ic_done,
                                weeklyIconXAxisRenderer.mContext.getTheme());
        int color =
                context.getResources()
                        .getColor(
                                R.color.dw_status_good_color,
                                weeklyIconXAxisRenderer.mContext.getTheme());
        BlendMode blendMode = BlendMode.SRC_IN;
        drawable.setColorFilter(new BlendModeColorFilter(color, blendMode));
        weeklyIconXAxisRenderer
                .mContext
                .getResources()
                .getDrawable(
                        R.drawable.ic_wifi_ap_dw_ic_failed,
                        weeklyIconXAxisRenderer.mContext.getTheme())
                .setColorFilter(
                        new BlendModeColorFilter(
                                context.getResources()
                                        .getColor(
                                                R.color.dw_status_bad_color,
                                                weeklyIconXAxisRenderer.mContext.getTheme()),
                                blendMode));
        int dimension =
                (int)
                        context.getResources()
                                .getDimension(R.dimen.goal_weekly_result_chart_ox_icon_size);
        weeklyIconXAxisRenderer.mIconSize = dimension;
        weeklyIconXAxisRenderer.mIconMargin =
                (dimension / 2.0f)
                        + context.getResources()
                                .getDimension(R.dimen.goal_weekly_result_xlabel_image_offset);
        weeklyIconXAxisRenderer.mIconList = new ArrayList();
        return weeklyIconXAxisRenderer;
    }
}
