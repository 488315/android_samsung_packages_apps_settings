package com.android.settings.datausage;

import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkPolicy;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.DataUnit;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.datausage.lib.NetworkCycleChartData;
import com.android.settings.datausage.lib.NetworkUsageData;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.UsageGraph;
import com.android.settings.widget.UsageView;

import com.samsung.android.settings.datausage.DataUsageFeatureProviderImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChartDataUsagePreference extends Preference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long RESOLUTION = DataUnit.MEBIBYTES.toBytes(1) / 2;
    public final int mBGLineColor;
    public final DataUsageFeatureProviderImpl mDataUsageFeatureProvider;
    public long mEnd;
    public final int mLimitColor;
    public NetworkCycleChartData mNetworkCycleChartData;
    public NetworkPolicy mPolicy;
    public final Resources mResources;
    public long mStart;
    public final int mWarningColor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class DataUsageSummaryNode {
        public final int mDataUsagePercentage;
        public final long mEndTime;
        public boolean mIsFromMultiNode = false;
        public final long mStartTime;

        public DataUsageSummaryNode(long j, long j2, int i) {
            this.mStartTime = j;
            this.mEndTime = j2;
            this.mDataUsagePercentage = i;
        }
    }

    public ChartDataUsagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mResources = context.getResources();
        setSelectable(false);
        this.mLimitColor =
                context.getResources().getColor(R.color.sec_data_usage_graph_limit_line_color);
        this.mWarningColor =
                context.getResources().getColor(R.color.sec_data_usage_graph_warning_line_color);
        this.mBGLineColor =
                context.getResources().getColor(R.color.sec_data_usage_graph_bottom_line_color);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mDataUsageFeatureProvider = featureFactoryImpl.getDataUsageFeatureProvider();
        setLayoutResource(R.layout.sec_data_usage_graph);
    }

    public void calcPoints(UsageView usageView, List<NetworkUsageData> list) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.put(0, 0);
        long currentTimeMillis = System.currentTimeMillis();
        long j = 0;
        for (NetworkUsageData networkUsageData : list) {
            long j2 = networkUsageData.startTime;
            if (j2 > currentTimeMillis) {
                break;
            }
            j += networkUsageData.usage;
            int i = (int) (((j2 - this.mStart) + 1) / 60000);
            long j3 = RESOLUTION;
            sparseIntArray.put(i, (int) (j / j3));
            sparseIntArray.put(
                    (int) ((networkUsageData.endTime - this.mStart) / 60000), (int) (j / j3));
            currentTimeMillis = currentTimeMillis;
        }
        if (sparseIntArray.size() > 1) {
            UsageGraph usageGraph = usageView.mUsageGraph;
            SparseIntArray sparseIntArray2 = usageGraph.mPaths;
            SparseIntArray sparseIntArray3 = usageGraph.mLocalPaths;
            long currentTimeMillis2 = System.currentTimeMillis();
            int size = sparseIntArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                sparseIntArray2.put(sparseIntArray.keyAt(i2), sparseIntArray.valueAt(i2));
            }
            sparseIntArray2.put(sparseIntArray.keyAt(sparseIntArray.size() - 1) + 1, -1);
            usageGraph.calculateLocalPaths(sparseIntArray2, sparseIntArray3);
            usageGraph.postInvalidate();
            BatteryUtils.logRuntime(currentTimeMillis2, "UsageGraph", "addPathAndUpdate");
        }
    }

    public List<DataUsageSummaryNode> getDensedStatsData(List<NetworkUsageData> list) {
        ArrayList arrayList = new ArrayList();
        long max =
                Math.max(
                        1L,
                        list.stream()
                                .mapToLong(
                                        new ChartDataUsagePreference$$ExternalSyntheticLambda4(2))
                                .sum());
        long j = 0;
        for (NetworkUsageData networkUsageData : list) {
            j += networkUsageData.usage;
            arrayList.add(
                    new DataUsageSummaryNode(
                            networkUsageData.startTime,
                            networkUsageData.endTime,
                            (int) ((100 * j) / max)));
        }
        Map map =
                (Map)
                        arrayList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                new ChartDataUsagePreference$$ExternalSyntheticLambda1()));
        final ArrayList arrayList2 = new ArrayList();
        map.forEach(
                new BiConsumer() { // from class:
                                   // com.android.settings.datausage.ChartDataUsagePreference$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ChartDataUsagePreference chartDataUsagePreference =
                                ChartDataUsagePreference.this;
                        List list2 = arrayList2;
                        List list3 = (List) obj2;
                        int i = ChartDataUsagePreference.$r8$clinit;
                        chartDataUsagePreference.getClass();
                        ChartDataUsagePreference.DataUsageSummaryNode dataUsageSummaryNode =
                                new ChartDataUsagePreference.DataUsageSummaryNode(
                                        list3.stream()
                                                .mapToLong(
                                                        new ChartDataUsagePreference$$ExternalSyntheticLambda4(
                                                                0))
                                                .min()
                                                .getAsLong(),
                                        list3.stream()
                                                .mapToLong(
                                                        new ChartDataUsagePreference$$ExternalSyntheticLambda4(
                                                                1))
                                                .max()
                                                .getAsLong(),
                                        ((Integer) obj).intValue());
                        if (list3.size() > 1) {
                            dataUsageSummaryNode.mIsFromMultiNode = true;
                        }
                        list2.add(dataUsageSummaryNode);
                    }
                });
        return (List)
                arrayList2.stream()
                        .sorted(
                                Comparator.comparingInt(
                                        new ChartDataUsagePreference$$ExternalSyntheticLambda3()))
                        .collect(Collectors.toList());
    }

    public final CharSequence getLabel(int i, int i2, long j) {
        Formatter.BytesResult formatBytes =
                this.mDataUsageFeatureProvider.formatBytes(getContext().getResources(), j, 1);
        return new SpannableStringBuilder()
                .append(
                        TextUtils.expandTemplate(
                                getContext().getText(i), formatBytes.value, formatBytes.units),
                        new ForegroundColorSpan(i2),
                        0);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        super.onBindViewHolder(preferenceViewHolder);
        UsageView usageView =
                (UsageView) preferenceViewHolder.itemView.requireViewById(R.id.data_usage);
        NetworkCycleChartData networkCycleChartData = this.mNetworkCycleChartData;
        long j = networkCycleChartData != null ? networkCycleChartData.total.usage : 0L;
        NetworkPolicy networkPolicy = this.mPolicy;
        int max =
                (int)
                        (Math.max(
                                        j,
                                        networkPolicy != null
                                                ? Math.max(
                                                        networkPolicy.limitBytes,
                                                        networkPolicy.warningBytes)
                                                : 0L)
                                / RESOLUTION);
        UsageGraph usageGraph = usageView.mUsageGraph;
        usageGraph.mPaths.clear();
        usageGraph.mLocalPaths.clear();
        usageGraph.mProjectedPaths.clear();
        usageGraph.mLocalProjectedPaths.clear();
        int i2 = (int) ((this.mEnd - this.mStart) / 60000);
        UsageGraph usageGraph2 = usageView.mUsageGraph;
        usageGraph2.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        usageGraph2.mMaxX = i2;
        float f = max;
        usageGraph2.mMaxY = f;
        usageGraph2.calculateLocalPaths(usageGraph2.mPaths, usageGraph2.mLocalPaths);
        usageGraph2.calculateLocalPaths(
                usageGraph2.mProjectedPaths, usageGraph2.mLocalProjectedPaths);
        usageGraph2.postInvalidate();
        BatteryUtils.logRuntime(currentTimeMillis, "UsageGraph", "setMax");
        NetworkCycleChartData networkCycleChartData2 = this.mNetworkCycleChartData;
        if (networkCycleChartData2 != null) {
            calcPoints(usageView, networkCycleChartData2.dailyUsage);
            List<NetworkUsageData> list = this.mNetworkCycleChartData.dailyUsage;
            Context context = getContext();
            StringBuilder sb = new StringBuilder();
            sb.append(
                    this.mResources.getString(
                            R.string.data_usage_chart_brief_content_description,
                            DateUtils.formatDateTime(context, this.mStart, 16),
                            DateUtils.formatDateTime(context, this.mEnd, 16)));
            if (list.isEmpty()) {
                sb.append(
                        this.mResources.getString(
                                R.string.data_usage_chart_no_data_content_description));
                usageView.setContentDescription(sb);
            } else {
                for (DataUsageSummaryNode dataUsageSummaryNode : getDensedStatsData(list)) {
                    int i3 = dataUsageSummaryNode.mDataUsagePercentage;
                    sb.append(
                            String.format(
                                    "; %s, %d%%",
                                    (!dataUsageSummaryNode.mIsFromMultiNode || i3 == 100)
                                            ? DateUtils.formatDateTime(
                                                    context, dataUsageSummaryNode.mStartTime, 16)
                                            : DateUtils.formatDateRange(
                                                    context,
                                                    dataUsageSummaryNode.mStartTime,
                                                    dataUsageSummaryNode.mEndTime,
                                                    16),
                                    Integer.valueOf(i3)));
                }
                usageView.setContentDescription(sb);
            }
        }
        Context context2 = getContext();
        long j2 = this.mStart;
        CharSequence formatDateRange = Utils.formatDateRange(context2, j2, j2);
        int i4 = 0;
        Context context3 = getContext();
        long j3 = this.mEnd;
        usageView.setBottomLabels(
                new CharSequence[] {formatDateRange, Utils.formatDateRange(context3, j3, j3)});
        NetworkPolicy networkPolicy2 = this.mPolicy;
        CharSequence[] charSequenceArr = new CharSequence[3];
        if (networkPolicy2 == null) {
            int i5 = this.mBGLineColor;
            UsageGraph usageGraph3 = usageView.mUsageGraph;
            usageGraph3.mMiddleDividerTint = i5;
            usageGraph3.mTopDividerTint = i5;
            return;
        }
        long j4 = networkPolicy2.limitBytes;
        if (j4 != -1) {
            i = this.mLimitColor;
            charSequenceArr[2] = getLabel(R.string.data_usage_sweep_limit, i, j4);
        } else {
            i = 0;
        }
        long j5 = networkPolicy2.warningBytes;
        if (j5 != -1) {
            int i6 = (int) (j5 / RESOLUTION);
            usageView.setDividerLoc(i6);
            float f2 = i6 / f;
            View findViewById = usageView.findViewById(R.id.space1);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.weight = 1.0f - f2;
            findViewById.setLayoutParams(layoutParams);
            View findViewById2 = usageView.findViewById(R.id.space2);
            LinearLayout.LayoutParams layoutParams2 =
                    (LinearLayout.LayoutParams) findViewById2.getLayoutParams();
            layoutParams2.weight = f2;
            findViewById2.setLayoutParams(layoutParams2);
            i4 = this.mWarningColor;
            charSequenceArr[1] =
                    getLabel(R.string.data_usage_sweep_warning, i4, networkPolicy2.warningBytes);
        } else {
            usageView.setDividerLoc(50);
        }
        usageView.setSideLabels(charSequenceArr);
        UsageGraph usageGraph4 = usageView.mUsageGraph;
        usageGraph4.mMiddleDividerTint = i4;
        usageGraph4.mTopDividerTint = i;
    }
}
