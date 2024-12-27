package com.samsung.android.settings.wifi.develop.homewifi;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.net.wifi.WifiScanner;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.BssidInfo;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;
import com.samsung.android.settings.wifi.develop.nearbywifi.model.SsidInfo;
import com.samsung.android.util.SemLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SignalMeasureFragment extends Fragment {
    public BssidListAdapter mBssidListAdapter;
    public View mProgressView;
    public RecyclerView mRecyclerView;
    public RssiChart mRssiChartView;
    public PartialScanner mScanner;
    public TextView mWeakAreaTextView;
    public ArrayList mBssidList = new ArrayList();
    public final MyHandler mHandler = new MyHandler(this);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MyHandler extends Handler {
        public final WeakReference mRef;

        public MyHandler(SignalMeasureFragment signalMeasureFragment) {
            this.mRef = new WeakReference(signalMeasureFragment);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SignalMeasureFragment signalMeasureFragment = (SignalMeasureFragment) this.mRef.get();
            if (signalMeasureFragment == null) {
                return;
            }
            int i = message.what;
            if (i == 1) {
                signalMeasureFragment.startScan();
                return;
            }
            if (i != 2) {
                return;
            }
            List list = (List) message.obj;
            SemLog.i("HomeWifi.SignalMeasureFragment", "Scan updated=" + list.size());
            Repository repository = Repository.LazyHolder.INSTANCE;
            repository.update(list);
            ArrayList arrayList = repository.ssidList;
            final int i2 = 0;
            signalMeasureFragment.mBssidList.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.SignalMeasureFragment$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) obj;
                            switch (i2) {
                                case 0:
                                    extendedBssidInfo.numOfMissingUpdate++;
                                    break;
                                default:
                                    if (extendedBssidInfo.numOfMissingUpdate >= 3) {
                                        extendedBssidInfo.rssi = -99;
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Iterator it2 = ((SsidInfo) it.next()).bssids.iterator();
                while (it2.hasNext()) {
                    final BssidInfo bssidInfo = (BssidInfo) it2.next();
                    signalMeasureFragment.mBssidList.stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.settings.wifi.develop.homewifi.SignalMeasureFragment$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return ((ExtendedBssidInfo) obj)
                                                    .bssid.equals(BssidInfo.this.bssid);
                                        }
                                    })
                            .findFirst()
                            .ifPresent(
                                    new Consumer() { // from class:
                                                     // com.samsung.android.settings.wifi.develop.homewifi.SignalMeasureFragment$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            ExtendedBssidInfo extendedBssidInfo =
                                                    (ExtendedBssidInfo) obj;
                                            extendedBssidInfo.rssi = BssidInfo.this.rssi;
                                            extendedBssidInfo.numOfMissingUpdate = 0;
                                        }
                                    });
                }
            }
            final int i3 = 1;
            signalMeasureFragment.mBssidList.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.SignalMeasureFragment$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) obj;
                            switch (i3) {
                                case 0:
                                    extendedBssidInfo.numOfMissingUpdate++;
                                    break;
                                default:
                                    if (extendedBssidInfo.numOfMissingUpdate >= 3) {
                                        extendedBssidInfo.rssi = -99;
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
            final BssidListAdapter bssidListAdapter = signalMeasureFragment.mBssidListAdapter;
            ArrayList arrayList2 = signalMeasureFragment.mBssidList;
            bssidListAdapter.getClass();
            HashSet hashSet = new HashSet();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                BssidInfo bssidInfo2 = (BssidInfo) it3.next();
                Integer num = (Integer) bssidListAdapter.mSearchHelper.get(bssidInfo2.bssid);
                if (num != null) {
                    ((BssidListAdapter.Data) bssidListAdapter.mData.get(num.intValue())).bssid =
                            bssidInfo2;
                    hashSet.add(num);
                }
            }
            hashSet.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.BssidListAdapter$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            BssidListAdapter.this.notifyItemChanged(((Integer) obj).intValue());
                        }
                    });
            signalMeasureFragment.mRssiChartView.addEntry(signalMeasureFragment.mBssidList);
            if (signalMeasureFragment.mRssiChartView.isThereWeakArea()) {
                signalMeasureFragment.mWeakAreaTextView.setText(
                        "Weak signal area has been detected!");
                signalMeasureFragment.mWeakAreaTextView.setTypeface(null, 1);
                signalMeasureFragment.mWeakAreaTextView.setTextColor(
                        Color.rgb(IKnoxCustomManager.Stub.TRANSACTION_setFavoriteApp, 91, 91));
                signalMeasureFragment.mProgressView.setVisibility(8);
            }
        }
    }

    public final int getPrimaryTextColor() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return EmergencyPhoneWidget.BG_COLOR;
        }
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.textColorPrimary, typedValue, true);
        TypedArray obtainStyledAttributes =
                activity.obtainStyledAttributes(
                        typedValue.data, new int[] {R.attr.textColorPrimary});
        int color = obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.recycle();
        return color;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        SemLog.d("HomeWifi.SignalMeasureFragment", "onCreateView");
        View inflate =
                layoutInflater.inflate(
                        com.android.settings.R.layout
                                .sec_wifi_development_homewifi_signal_measure_fragment,
                        (ViewGroup) null);
        final RssiChart rssiChart =
                (RssiChart) inflate.findViewById(com.android.settings.R.id.rssi_chart);
        this.mRssiChartView = rssiChart;
        ArrayList arrayList = this.mBssidList;
        int primaryTextColor = getPrimaryTextColor();
        rssiChart.getClass();
        rssiChart.mNumOfDataSets = arrayList.size();
        rssiChart.mColors.clear();
        int i = 0;
        for (int i2 = 0; i2 < rssiChart.mNumOfDataSets; i2++) {
            rssiChart.mColors.add(
                    Integer.valueOf(Color.parseColor(RssiChart.COLOR_PALLETS[i2 % 7])));
        }
        Paint paint = new Paint();
        rssiChart.mWeakAreaPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        rssiChart.mWeakAreaPaint.setColor(Color.parseColor("#79EC2436"));
        rssiChart.mDescription.mEnabled = false;
        rssiChart.mTouchEnabled = true;
        rssiChart.mDragXEnabled = true;
        rssiChart.mDragYEnabled = true;
        rssiChart.mScaleXEnabled = false;
        rssiChart.mScaleYEnabled = false;
        rssiChart.mDrawGridBackground = false;
        rssiChart.mLegend.mEnabled = false;
        XAxis xAxis = rssiChart.mXAxis;
        xAxis.mEnabled = true;
        xAxis.mTextColor = primaryTextColor;
        xAxis.mDrawGridLines = true;
        xAxis.mAvoidFirstLastClipping = true;
        xAxis.setAxisMinimum(0.0f);
        xAxis.setAxisMaximum(60.0f);
        xAxis.mPosition = XAxis.XAxisPosition.BOTTOM;
        xAxis.mDrawLabels = false;
        YAxis yAxis = rssiChart.mAxisRight;
        yAxis.mEnabled = true;
        yAxis.mTextColor = primaryTextColor;
        yAxis.setAxisMaximum(-30.0f);
        yAxis.setAxisMinimum(-80.0f);
        yAxis.mSpacePercentTop = 20.0f;
        yAxis.mDrawGridLines = false;
        ViewPortHandler viewPortHandler = rssiChart.mViewPortHandler;
        YAxis.AxisDependency axisDependency = YAxis.AxisDependency.RIGHT;
        rssiChart.mAxisRendererRight =
                new RssiChart.ColoredYAxisRenderer(
                        viewPortHandler,
                        yAxis,
                        rssiChart.getTransformer(axisDependency),
                        primaryTextColor);
        YAxis yAxis2 = rssiChart.mAxisLeft;
        yAxis2.mEnabled = false;
        yAxis2.mDrawLabels = false;
        yAxis2.mDrawGridLines = false;
        int parseColor = Color.parseColor("#d93e36");
        LimitLine limitLine = new LimitLine();
        limitLine.mLimit = 0.0f;
        limitLine.mLineWidth = 2.0f;
        limitLine.mLineColor =
                Color.rgb(IKnoxCustomManager.Stub.TRANSACTION_setFavoriteApp, 91, 91);
        limitLine.mTextStyle = Paint.Style.FILL_AND_STROKE;
        limitLine.mDashPathEffect = null;
        limitLine.mLabelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP;
        limitLine.mLimit = -70.0f;
        limitLine.mLabel = "Weak signal level";
        limitLine.mLineColor = parseColor;
        limitLine.mTextColor = parseColor;
        float f = 1.5f;
        limitLine.mLineWidth = Utils.convertDpToPixel(1.5f);
        limitLine.mDashPathEffect = new DashPathEffect(new float[] {5.0f, 10.0f}, 0.0f);
        limitLine.mLabelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM;
        limitLine.mXOffset = Utils.convertDpToPixel(5.0f);
        ((ArrayList) yAxis.mLimitLines).add(limitLine);
        if (((ArrayList) yAxis.mLimitLines).size() > 6) {
            Log.e(
                    "MPAndroiChart",
                    "Warning! You have more than 6 LimitLines on your axis, do you really want"
                        + " that?");
        }
        rssiChart.mWeakAreas.clear();
        rssiChart.mInWeakAreaNow = false;
        ArrayList arrayList2 = new ArrayList();
        int i3 = 0;
        while (i3 < rssiChart.mNumOfDataSets) {
            LineDataSet lineDataSet = new LineDataSet(null, null);
            lineDataSet.mAxisDependency = axisDependency;
            lineDataSet.setColor(((Integer) rssiChart.mColors.get(i3)).intValue());
            lineDataSet.setLineWidth(f);
            lineDataSet.mDrawCircles = false;
            lineDataSet.mDrawValues = false;
            lineDataSet.mDrawHorizontalHighlightIndicator = false;
            lineDataSet.mDrawVerticalHighlightIndicator = false;
            lineDataSet.mDrawFilled = true;
            lineDataSet.mFillFormatter =
                    new IFillFormatter() { // from class:
                                           // com.samsung.android.settings.wifi.develop.homewifi.RssiChart$$ExternalSyntheticLambda0
                        @Override // com.github.mikephil.charting.formatter.IFillFormatter
                        public final float getFillLinePosition(
                                LineDataSet lineDataSet2, LineDataProvider lineDataProvider) {
                            String[] strArr = RssiChart.COLOR_PALLETS;
                            return RssiChart.this.mAxisRight.mAxisMinimum;
                        }
                    };
            lineDataSet.mFillDrawable =
                    new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[] {
                                (((Integer) rssiChart.mColors.get(i3)).intValue() & 16777215)
                                        | 1610612736,
                                16777215
                            });
            arrayList2.add(lineDataSet);
            i3++;
            f = 1.5f;
        }
        rssiChart.setData(new LineData(arrayList2));
        rssiChart.addEntry(arrayList);
        this.mRecyclerView =
                (RecyclerView) inflate.findViewById(com.android.settings.R.id.wifi_scan_recycler);
        if (this.mBssidListAdapter == null) {
            this.mBssidListAdapter = new BssidListAdapter(this.mBssidList);
        }
        BssidListAdapter bssidListAdapter = this.mBssidListAdapter;
        int primaryTextColor2 = getPrimaryTextColor();
        bssidListAdapter.mPrimaryTextColor = primaryTextColor2;
        bssidListAdapter.mPrimaryTextColorR = (16711680 & primaryTextColor2) >> 16;
        bssidListAdapter.mPrimaryTextColorG = (65280 & primaryTextColor2) >> 8;
        bssidListAdapter.mPrimaryTextColorB = primaryTextColor2 & 255;
        SemLog.d(
                "HomeWifi.BssidListAdapter",
                "PrimaryTextColor: r="
                        + bssidListAdapter.mPrimaryTextColorR
                        + " g="
                        + bssidListAdapter.mPrimaryTextColorG
                        + " b="
                        + bssidListAdapter.mPrimaryTextColorB);
        BssidListAdapter bssidListAdapter2 = this.mBssidListAdapter;
        ArrayList arrayList3 = this.mRssiChartView.mColors;
        bssidListAdapter2.getClass();
        if (arrayList3.size() >= bssidListAdapter2.mData.size()) {
            SemLog.w(
                    "HomeWifi.BssidListAdapter",
                    "setBssidColors: list size is mismatched!"
                            + arrayList3.size()
                            + " >= "
                            + bssidListAdapter2.mData.size());
        } else {
            Iterator it = bssidListAdapter2.mData.iterator();
            while (it.hasNext()) {
                BssidListAdapter.Data data = (BssidListAdapter.Data) it.next();
                if (data.ssid.length() <= 0) {
                    data.bssidColor = ((Integer) arrayList3.get(i)).intValue();
                    i++;
                }
            }
        }
        this.mRecyclerView.setAdapter(this.mBssidListAdapter);
        this.mRecyclerView.setItemAnimator(null);
        this.mWeakAreaTextView =
                (TextView) inflate.findViewById(com.android.settings.R.id.weak_area_text);
        this.mProgressView = inflate.findViewById(com.android.settings.R.id.progressbar);
        PartialScanner partialScanner = new PartialScanner();
        this.mScanner = partialScanner;
        Context context = getContext();
        ArrayList arrayList4 = this.mBssidList;
        partialScanner.mReceiver = new SignalMeasureFragment$$ExternalSyntheticLambda4(this);
        partialScanner.mWifiScanner = (WifiScanner) context.getSystemService("wifiscanner");
        final HashSet hashSet = new HashSet();
        arrayList4.forEach(
                new Consumer() { // from class:
                                 // com.samsung.android.settings.wifi.develop.homewifi.PartialScanner$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        hashSet.add(Integer.valueOf(((ExtendedBssidInfo) obj).freq));
                    }
                });
        partialScanner.mTargetFrequencies.clear();
        partialScanner.mTargetFrequencies.addAll(hashSet);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SemLog.d("HomeWifi.SignalMeasureFragment", "stopScan");
        this.mHandler.removeMessages(1);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        startScan();
    }

    public final void startScan() {
        PartialScanner partialScanner = this.mScanner;
        if (partialScanner.mReceiver != null
                && partialScanner.mWifiScanner != null
                && partialScanner.mTargetFrequencies.size() != 0) {
            WifiScanner.ScanSettings scanSettings = new WifiScanner.ScanSettings();
            scanSettings.type = 2;
            int i = 0;
            scanSettings.band = 0;
            scanSettings.channels =
                    new WifiScanner.ChannelSpec[partialScanner.mTargetFrequencies.size()];
            Iterator it = partialScanner.mTargetFrequencies.iterator();
            while (it.hasNext()) {
                scanSettings.channels[i] =
                        new WifiScanner.ChannelSpec(((Integer) it.next()).intValue());
                i++;
            }
            SemLog.i(
                    "HomeWifi.PartialScanner",
                    "Partial scan requested: " + partialScanner.mTargetFrequencies);
            partialScanner.mWifiScanner.startScan(
                    scanSettings, partialScanner.mPartialScanListener);
        }
        this.mHandler.removeMessages(1);
        MyHandler myHandler = this.mHandler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(1), 1000L);
    }
}
