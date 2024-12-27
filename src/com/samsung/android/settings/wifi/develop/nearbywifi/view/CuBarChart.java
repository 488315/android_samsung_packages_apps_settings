package com.samsung.android.settings.wifi.develop.nearbywifi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class CuBarChart extends Preference {
    public final String TAG;
    public int mBand;
    public TextView mBandInfo;
    public int mBarShapeResId;
    public ChartLinearLayout mChartLayout;
    public List mCuInfoList;
    public LinearLayout.LayoutParams mLayoutParams;

    public CuBarChart(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = "NearbyWifi.CuBarChart";
        init$24();
    }

    public final void init$24() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        this.mLayoutParams = layoutParams;
        layoutParams.weight = 1.0f;
        layoutParams.gravity = 80;
        setLayoutResource(R.layout.sec_wifi_development_nearbywifi_cu_bar_chart);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ChartLinearLayout chartLinearLayout =
                (ChartLinearLayout) preferenceViewHolder.itemView.findViewById(R.id.chart_layout);
        this.mChartLayout = chartLinearLayout;
        chartLinearLayout.setWillNotDraw(false);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.band_info);
        this.mBandInfo = textView;
        int i = this.mBand;
        if (i == 1) {
            textView.setText(R.string.wifi_development_nearbywifi_24g_cu_chart_title);
        } else if (i == 2) {
            textView.setText(R.string.wifi_development_nearbywifi_5g_cu_chart_title);
        } else if (i == 8) {
            textView.setText(R.string.wifi_development_nearbywifi_6g_cu_chart_title);
        }
        this.mBandInfo.setVisibility(8);
        updateChartItem();
    }

    public final void updateChartItem(List list) {
        if (this.mChartLayout == null) {
            SemLog.e(this.TAG, "mChartLayout is null, so cannot update chart items.");
        } else {
            this.mCuInfoList = list;
            updateChartItem();
        }
    }

    public CuBarChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "NearbyWifi.CuBarChart";
        init$24();
    }

    public final void updateChartItem() {
        this.mChartLayout.removeAllViews();
        CuBarView cuBarView = new CuBarView(getContext());
        cuBarView.isYaxis = true;
        cuBarView.setHeight(94);
        cuBarView.mCuBar.setVisibility(4);
        cuBarView.mChannel.setText(R.string.wifi_development_nearbywifi_ch);
        cuBarView.mChannel.setTextAlignment(6);
        cuBarView.mFreq.setText(R.string.wifi_development_nearbywifi_freq);
        cuBarView.mFreq.setTextAlignment(6);
        cuBarView.mCuValue.setText("100%");
        cuBarView.mCuValue.setTextAlignment(6);
        this.mChartLayout.addView(cuBarView);
        for (Repository.CuInfo cuInfo : this.mCuInfoList) {
            CuBarView cuBarView2 = new CuBarView(getContext());
            int i = cuInfo.channel;
            int i2 = cuInfo.cu;
            cuBarView2.setHeight(i2);
            cuBarView2.mChannel.setText(Integer.toString(i));
            cuBarView2.mFreq.setText(Integer.toString(cuInfo.frequency));
            cuBarView2.mCuValue.setText(Integer.toString(i2));
            cuBarView2.setGravity(80);
            cuBarView2.mCuBar.setBackgroundResource(this.mBarShapeResId);
            this.mChartLayout.addView(cuBarView2, this.mLayoutParams);
        }
        setVisible(this.mChartLayout.getChildCount() != 1);
    }

    public CuBarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "NearbyWifi.CuBarChart";
        init$24();
    }

    public CuBarChart(Context context) {
        super(context);
        this.TAG = "NearbyWifi.CuBarChart";
        init$24();
    }
}
