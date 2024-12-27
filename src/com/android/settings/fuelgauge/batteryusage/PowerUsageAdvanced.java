package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableResource;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.utils.AsyncLoaderCompat;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PowerUsageAdvanced extends PowerUsageBase {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    BatteryChartPreferenceController mBatteryChartPreferenceController;
    Optional<BatteryLevelData> mBatteryLevelData;
    public final AnonymousClass1 mBatteryObserver;
    BatteryTipsController mBatteryTipsController;
    BatteryUsageBreakdownController mBatteryUsageBreakdownController;
    public Map mBatteryUsageMap;
    public final Handler mHandler;
    Optional<AnomalyEventWrapper> mHighlightEventWrapper;
    BatteryHistoryPreference mHistPref;
    public long mResumeTimestamp;
    ScreenOnTimeController mScreenOnTimeController;
    final BatteryLevelDataLoaderCallbacks mBatteryLevelDataLoaderCallbacks =
            new BatteryLevelDataLoaderCallbacks();
    public boolean mIsChartDataLoaded = false;
    public final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batteryusage.PowerUsageAdvanced$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new BatteryChartPreferenceController(context, null, null));
            arrayList.add(new ScreenOnTimeController(context));
            arrayList.add(new BatteryUsageBreakdownController(context, null, null, null));
            arrayList.add(new BatteryTipsController(context));
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.power_usage_advanced;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* renamed from: -$$Nest$monBatteryDiffDataMapUpdate, reason: not valid java name */
    public static void m938$$Nest$monBatteryDiffDataMapUpdate(
            PowerUsageAdvanced powerUsageAdvanced, Map map) {
        BatteryDiffData batteryDiffData;
        if (!powerUsageAdvanced.isResumed() || powerUsageAdvanced.mBatteryLevelData == null) {
            return;
        }
        powerUsageAdvanced.mBatteryUsageMap =
                DataProcessor.generateBatteryUsageMap(
                        powerUsageAdvanced.getContext(),
                        map,
                        powerUsageAdvanced.mBatteryLevelData.orElse(null));
        Log.d(
                "AdvancedBatteryUsage",
                "onBatteryDiffDataMapUpdate: " + powerUsageAdvanced.mBatteryUsageMap);
        Map map2 = powerUsageAdvanced.mBatteryUsageMap;
        if (map2 != null
                && (batteryDiffData = (BatteryDiffData) ((Map) ((ArrayMap) map2).get(-1)).get(-1))
                        != null) {
            final int i = 0;
            batteryDiffData.mAppEntries.forEach(
                    new Consumer() { // from class:
                                     // com.android.settings.fuelgauge.batteryusage.DataProcessor$$ExternalSyntheticLambda14
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            BatteryDiffEntry batteryDiffEntry = (BatteryDiffEntry) obj;
                            switch (i) {
                                case 0:
                                    batteryDiffEntry.loadLabelAndIcon();
                                    break;
                                default:
                                    batteryDiffEntry.loadLabelAndIcon();
                                    break;
                            }
                        }
                    });
            final int i2 = 1;
            batteryDiffData.mSystemEntries.forEach(
                    new Consumer() { // from class:
                                     // com.android.settings.fuelgauge.batteryusage.DataProcessor$$ExternalSyntheticLambda14
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            BatteryDiffEntry batteryDiffEntry = (BatteryDiffEntry) obj;
                            switch (i2) {
                                case 0:
                                    batteryDiffEntry.loadLabelAndIcon();
                                    break;
                                default:
                                    batteryDiffEntry.loadLabelAndIcon();
                                    break;
                            }
                        }
                    });
        }
        powerUsageAdvanced.onSelectedSlotDataUpdated();
        powerUsageAdvanced.mExecutor.execute(
                new PowerUsageAdvanced$$ExternalSyntheticLambda0(powerUsageAdvanced, 1));
        Map map3 = powerUsageAdvanced.mBatteryUsageMap;
        BatteryDiffData batteryDiffData2 =
                map3 == null ? null : (BatteryDiffData) ((Map) ((ArrayMap) map3).get(-1)).get(-1);
        if (batteryDiffData2 != null) {
            Iterator it = batteryDiffData2.mAppEntries.iterator();
            long j = 0;
            while (it.hasNext()) {
                j += ((BatteryDiffEntry) it.next()).mForegroundUsageTimeInMs;
            }
            powerUsageAdvanced.mMetricsFeatureProvider.action(
                    powerUsageAdvanced.getContext(), 1816, (int) batteryDiffData2.mScreenOnTime);
            powerUsageAdvanced.mMetricsFeatureProvider.action(
                    powerUsageAdvanced.getContext(), 1817, (int) j);
        }
        if (powerUsageAdvanced.mBatteryChartPreferenceController == null
                || !powerUsageAdvanced.mBatteryLevelData.isEmpty()) {
            return;
        }
        Map map4 = powerUsageAdvanced.mBatteryUsageMap;
        BatteryDiffData batteryDiffData3 =
                map4 == null ? null : (BatteryDiffData) ((Map) ((ArrayMap) map4).get(-1)).get(-1);
        if (batteryDiffData3 == null
                || (batteryDiffData3.mAppEntries.isEmpty()
                        && batteryDiffData3.mSystemEntries.isEmpty())) {
            BatteryChartPreferenceController batteryChartPreferenceController =
                    powerUsageAdvanced.mBatteryChartPreferenceController;
            if (batteryChartPreferenceController.mDailyChartView == null
                    || batteryChartPreferenceController.mHourlyChartView == null) {
                return;
            }
            TextView textView = batteryChartPreferenceController.mChartSummaryTextView;
            if (textView != null) {
                textView.setVisibility(0);
            }
            batteryChartPreferenceController.mDailyChartView.setVisibility(8);
            batteryChartPreferenceController.mHourlyChartView.setVisibility(0);
            batteryChartPreferenceController.mHourlyChartView.setViewModel(null);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settings.fuelgauge.batteryusage.PowerUsageAdvanced$1] */
    public PowerUsageAdvanced() {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mBatteryObserver =
                new ContentObserver(
                        handler) { // from class:
                                   // com.android.settings.fuelgauge.batteryusage.PowerUsageAdvanced.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                "onBatteryContentChange: ", "AdvancedBatteryUsage", z);
                        PowerUsageAdvanced powerUsageAdvanced = PowerUsageAdvanced.this;
                        powerUsageAdvanced.mIsChartDataLoaded = false;
                        powerUsageAdvanced.restartBatteryStatsLoader(0);
                    }
                };
    }

    public static PowerAnomalyEvent getAnomalyEvent(
            PowerAnomalyEventList powerAnomalyEventList, Predicate<PowerAnomalyEvent> predicate) {
        if (powerAnomalyEventList == null
                || powerAnomalyEventList.getPowerAnomalyEventsCount() == 0) {
            return null;
        }
        PowerAnomalyEvent powerAnomalyEvent =
                (PowerAnomalyEvent)
                        powerAnomalyEventList.getPowerAnomalyEventsList().stream()
                                .filter(predicate)
                                .max(
                                        Comparator.comparing(
                                                new PowerUsageAdvanced$$ExternalSyntheticLambda3()))
                                .orElse(null);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("filterAnomalyEvent = "),
                powerAnomalyEvent != null ? powerAnomalyEvent.getEventId() : null,
                "AdvancedBatteryUsage");
        return powerAnomalyEvent;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        BatteryChartPreferenceController batteryChartPreferenceController;
        BatteryChartView batteryChartView;
        ArrayList arrayList = new ArrayList();
        this.mBatteryTipsController = new BatteryTipsController(context);
        this.mBatteryChartPreferenceController =
                new BatteryChartPreferenceController(
                        context, getSettingsLifecycle(), (SettingsActivity) getActivity());
        this.mScreenOnTimeController = new ScreenOnTimeController(context);
        this.mBatteryUsageBreakdownController =
                new BatteryUsageBreakdownController(
                        context, getSettingsLifecycle(), (SettingsActivity) getActivity(), this);
        arrayList.add(this.mBatteryTipsController);
        arrayList.add(this.mBatteryChartPreferenceController);
        arrayList.add(this.mScreenOnTimeController);
        arrayList.add(this.mBatteryUsageBreakdownController);
        BatteryHistoryPreference batteryHistoryPreference = this.mHistPref;
        if (batteryHistoryPreference != null
                && (batteryChartPreferenceController = this.mBatteryChartPreferenceController)
                        != null) {
            batteryHistoryPreference.mChartPreferenceController = batteryChartPreferenceController;
            BatteryChartView batteryChartView2 = batteryHistoryPreference.mDailyChartView;
            if (batteryChartView2 != null
                    && (batteryChartView = batteryHistoryPreference.mHourlyChartView) != null) {
                batteryChartPreferenceController.setBatteryChartView(
                        batteryChartView2, batteryChartView);
            }
        }
        this.mBatteryChartPreferenceController.mOnSelectedIndexUpdatedListener =
                new PowerUsageAdvanced$$ExternalSyntheticLambda1(this);
        onSelectedSlotDataUpdated();
        return arrayList;
    }

    public BatteryDiffEntry findRelatedBatteryDiffEntry(AnomalyEventWrapper anomalyEventWrapper) {
        Optional<BatteryLevelData> optional;
        if (anomalyEventWrapper != null
                && (optional = this.mBatteryLevelData) != null
                && !optional.isEmpty()) {
            BatteryLevelData batteryLevelData = this.mBatteryLevelData.get();
            if (anomalyEventWrapper.mHighlightSlotPair == null) {
                anomalyEventWrapper.mHighlightSlotPair =
                        anomalyEventWrapper.getHighlightSlotPair(batteryLevelData);
            }
            if (anomalyEventWrapper.mHighlightSlotPair != null
                    && anomalyEventWrapper.getAnomalyEntryKey() != null
                    && this.mBatteryUsageMap != null) {
                Pair highlightSlotPair =
                        anomalyEventWrapper.getHighlightSlotPair(this.mBatteryLevelData.get());
                BatteryDiffData batteryDiffData =
                        (BatteryDiffData)
                                ((Map)
                                                ((ArrayMap) this.mBatteryUsageMap)
                                                        .get(highlightSlotPair.first))
                                        .get(highlightSlotPair.second);
                String anomalyEntryKey = anomalyEventWrapper.getAnomalyEntryKey();
                if (batteryDiffData != null && anomalyEntryKey != null) {
                    for (BatteryDiffEntry batteryDiffEntry : batteryDiffData.mAppEntries) {
                        if (anomalyEntryKey.equals(batteryDiffEntry.mKey)) {
                            return batteryDiffEntry;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AdvancedBatteryUsage";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 51;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.power_usage_advanced;
    }

    public final boolean isAppsAnomalyEventFocused() {
        int i;
        int i2;
        BatteryChartPreferenceController batteryChartPreferenceController =
                this.mBatteryChartPreferenceController;
        return (batteryChartPreferenceController == null
                        || (i = batteryChartPreferenceController.mDailyHighlightSlotIndex) == -2
                        || i != batteryChartPreferenceController.mDailyChartIndex
                        || (i2 = batteryChartPreferenceController.mHourlyHighlightSlotIndex) == -2
                        || i2 != batteryChartPreferenceController.mHourlyChartIndex)
                ? false
                : true;
    }

    @Override // com.android.settings.fuelgauge.batteryusage.PowerUsageBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        BatteryChartPreferenceController batteryChartPreferenceController;
        BatteryChartView batteryChartView;
        super.onCreate(bundle);
        BatteryHistoryPreference batteryHistoryPreference =
                (BatteryHistoryPreference) findPreference("battery_chart");
        this.mHistPref = batteryHistoryPreference;
        if (batteryHistoryPreference != null
                && (batteryChartPreferenceController = this.mBatteryChartPreferenceController)
                        != null) {
            batteryHistoryPreference.mChartPreferenceController = batteryChartPreferenceController;
            BatteryChartView batteryChartView2 = batteryHistoryPreference.mDailyChartView;
            if (batteryChartView2 != null
                    && (batteryChartView = batteryHistoryPreference.mHourlyChartView) != null) {
                batteryChartPreferenceController.setBatteryChartView(
                        batteryChartView2, batteryChartView);
            }
        }
        AsyncTask.execute(new PowerUsageAdvanced$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        if (getActivity().isChangingConfigurations()) {
            BatteryEntry.sUidCache.clear();
        }
        this.mExecutor.shutdown();
    }

    public void onDisplayAnomalyEventUpdated(
            PowerAnomalyEvent powerAnomalyEvent, PowerAnomalyEvent powerAnomalyEvent2) {
        BatteryTipsController batteryTipsController = this.mBatteryTipsController;
        if (batteryTipsController == null
                || this.mBatteryChartPreferenceController == null
                || this.mBatteryUsageBreakdownController == null) {
            return;
        }
        boolean z = powerAnomalyEvent == powerAnomalyEvent2;
        AnomalyEventWrapper anomalyEventWrapper = null;
        batteryTipsController.setOnAnomalyConfirmListener(null);
        this.mBatteryTipsController.setOnAnomalyRejectListener(null);
        AnomalyEventWrapper anomalyEventWrapper2 =
                powerAnomalyEvent == null
                        ? null
                        : new AnomalyEventWrapper(getContext(), powerAnomalyEvent);
        if (anomalyEventWrapper2 != null) {
            anomalyEventWrapper2.mRelatedBatteryDiffEntry =
                    findRelatedBatteryDiffEntry(anomalyEventWrapper2);
        }
        this.mBatteryTipsController.handleBatteryTipsCardUpdated(anomalyEventWrapper2, z);
        Pair create = Pair.create(-2, -2);
        if (z) {
            anomalyEventWrapper = anomalyEventWrapper2;
        } else if (powerAnomalyEvent2 != null) {
            anomalyEventWrapper = new AnomalyEventWrapper(getContext(), powerAnomalyEvent2);
        }
        this.mHighlightEventWrapper = Optional.ofNullable(anomalyEventWrapper);
        Optional<BatteryLevelData> optional = this.mBatteryLevelData;
        if (optional != null && optional.isPresent() && this.mHighlightEventWrapper.isPresent()) {
            AnomalyEventWrapper anomalyEventWrapper3 = this.mHighlightEventWrapper.get();
            BatteryLevelData batteryLevelData = this.mBatteryLevelData.get();
            if (anomalyEventWrapper3.mHighlightSlotPair == null) {
                anomalyEventWrapper3.mHighlightSlotPair =
                        anomalyEventWrapper3.getHighlightSlotPair(batteryLevelData);
            }
            if (anomalyEventWrapper3.mHighlightSlotPair != null) {
                create =
                        this.mHighlightEventWrapper
                                .get()
                                .getHighlightSlotPair(this.mBatteryLevelData.get());
                if (z) {
                    this.mBatteryTipsController.setOnAnomalyConfirmListener(
                            new PowerUsageAdvanced$$ExternalSyntheticLambda1(this));
                }
            }
        }
        BatteryChartPreferenceController batteryChartPreferenceController =
                this.mBatteryChartPreferenceController;
        int intValue = ((Integer) create.first).intValue();
        int intValue2 = ((Integer) create.second).intValue();
        batteryChartPreferenceController.mDailyHighlightSlotIndex = intValue;
        batteryChartPreferenceController.mHourlyHighlightSlotIndex = intValue2;
        batteryChartPreferenceController.refreshUi();
        PowerUsageAdvanced$$ExternalSyntheticLambda1 powerUsageAdvanced$$ExternalSyntheticLambda1 =
                batteryChartPreferenceController.mOnSelectedIndexUpdatedListener;
        if (powerUsageAdvanced$$ExternalSyntheticLambda1 != null) {
            powerUsageAdvanced$$ExternalSyntheticLambda1.f$0.onSelectedSlotDataUpdated();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mIsChartDataLoaded = false;
        if (DatabaseUtils.BATTERY_CONTENT_URI != null) {
            getContext().getContentResolver().unregisterContentObserver(this.mBatteryObserver);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mResumeTimestamp = System.currentTimeMillis();
        Uri uri = DatabaseUtils.BATTERY_CONTENT_URI;
        if (uri != null) {
            getContext()
                    .getContentResolver()
                    .registerContentObserver(uri, true, this.mBatteryObserver);
        }
    }

    public final void onSelectedSlotDataUpdated() {
        BatteryChartPreferenceController batteryChartPreferenceController =
                this.mBatteryChartPreferenceController;
        if (batteryChartPreferenceController == null
                || this.mScreenOnTimeController == null
                || this.mBatteryUsageBreakdownController == null
                || this.mBatteryUsageMap == null) {
            return;
        }
        int i = batteryChartPreferenceController.mDailyChartIndex;
        int i2 = batteryChartPreferenceController.mHourlyChartIndex;
        String slotInformation = batteryChartPreferenceController.getSlotInformation(false);
        String slotInformation2 = this.mBatteryChartPreferenceController.getSlotInformation(true);
        BatteryDiffData batteryDiffData =
                (BatteryDiffData)
                        ((Map) ((ArrayMap) this.mBatteryUsageMap).get(Integer.valueOf(i)))
                                .get(Integer.valueOf(i2));
        this.mScreenOnTimeController.handleScreenOnTimeUpdated(
                Long.valueOf(batteryDiffData != null ? batteryDiffData.mScreenOnTime : 0L),
                slotInformation,
                slotInformation2);
        if (isAppsAnomalyEventFocused()) {
            this.mBatteryTipsController.acceptTipsCard();
        }
        BatteryUsageBreakdownController batteryUsageBreakdownController =
                this.mBatteryUsageBreakdownController;
        Map map = this.mBatteryUsageMap;
        BatteryDiffData batteryDiffData2 =
                map == null ? null : (BatteryDiffData) ((Map) ((ArrayMap) map).get(-1)).get(-1);
        batteryUsageBreakdownController.handleBatteryUsageUpdated(
                batteryDiffData,
                slotInformation,
                slotInformation2,
                batteryDiffData2 == null
                        || (batteryDiffData2.mAppEntries.isEmpty()
                                && batteryDiffData2.mSystemEntries.isEmpty()),
                isAppsAnomalyEventFocused(),
                this.mHighlightEventWrapper);
        Log.d(
                "AdvancedBatteryUsage",
                String.format(
                        "Battery usage list shows in %d millis",
                        Long.valueOf(System.currentTimeMillis() - this.mResumeTimestamp)));
    }

    @Override // com.android.settings.fuelgauge.batteryusage.PowerUsageBase
    public final void restartBatteryStatsLoader(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("refresh_type", i);
        if (this.mIsChartDataLoaded) {
            return;
        }
        this.mIsChartDataLoaded = true;
        this.mBatteryLevelData = null;
        this.mBatteryUsageMap = null;
        this.mHighlightEventWrapper = null;
        restartLoader(3, bundle, this.mBatteryLevelDataLoaderCallbacks);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatteryLevelDataLoaderCallbacks implements LoaderManager.LoaderCallbacks {
        public BatteryLevelDataLoaderCallbacks() {}

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            return new AsyncLoaderCompat(
                    PowerUsageAdvanced.this.getContext().getApplicationContext()) { // from class:
                // com.android.settings.fuelgauge.batteryusage.PowerUsageAdvanced.BatteryLevelDataLoaderCallbacks.1
                /* JADX WARN: Removed duplicated region for block: B:29:0x00ff A[LOOP:0: B:27:0x00f9->B:29:0x00ff, LOOP_END] */
                /* JADX WARN: Removed duplicated region for block: B:32:0x011b  */
                /* JADX WARN: Removed duplicated region for block: B:44:0x015f  */
                /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
                @Override // androidx.loader.content.AsyncTaskLoader
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object loadInBackground() {
                    /*
                        Method dump skipped, instructions count: 359
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.fuelgauge.batteryusage.PowerUsageAdvanced.BatteryLevelDataLoaderCallbacks.AnonymousClass1.loadInBackground():java.lang.Object");
                }

                @Override // com.android.settingslib.utils.AsyncLoaderCompat
                public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
            };
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            Object obj2;
            BatteryLevelData batteryLevelData = (BatteryLevelData) obj;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    PowerUsageAdvanced.SEARCH_INDEX_DATA_PROVIDER;
            PowerUsageAdvanced powerUsageAdvanced = PowerUsageAdvanced.this;
            if (powerUsageAdvanced.isResumed()) {
                powerUsageAdvanced.mBatteryLevelData = Optional.ofNullable(batteryLevelData);
                BatteryChartPreferenceController batteryChartPreferenceController =
                        powerUsageAdvanced.mBatteryChartPreferenceController;
                if (batteryChartPreferenceController != null) {
                    Log.d(
                            "BatteryChartPreferenceController",
                            "onBatteryLevelDataUpdate: " + batteryLevelData);
                    batteryChartPreferenceController.mMetricsFeatureProvider.action(
                            batteryChartPreferenceController.mPrefContext,
                            VolteConstants.ErrorCode.FAILED_TO_GO_READY,
                            BatteryChartPreferenceController.getTotalHours(batteryLevelData));
                    if (batteryLevelData == null) {
                        batteryChartPreferenceController.mDailyChartIndex = -1;
                        batteryChartPreferenceController.mHourlyChartIndex = -1;
                        batteryChartPreferenceController.mDailyViewModel = null;
                        batteryChartPreferenceController.mHourlyViewModels = null;
                        batteryChartPreferenceController.refreshUi();
                    } else {
                        BatteryLevelData.PeriodBatteryLevelData periodBatteryLevelData =
                                batteryLevelData.mDailyBatteryLevels;
                        batteryChartPreferenceController.mDailyViewModel =
                                new BatteryChartViewModel(
                                        periodBatteryLevelData.mLevels,
                                        periodBatteryLevelData.mTimestamps,
                                        BatteryChartViewModel.AxisLabelPosition
                                                .CENTER_OF_TRAPEZOIDS,
                                        batteryChartPreferenceController
                                                .mDailyChartLabelTextGenerator);
                        batteryChartPreferenceController.mHourlyViewModels = new ArrayList();
                        Iterator it =
                                ((ArrayList) batteryLevelData.mHourlyBatteryLevelsPerDay)
                                        .iterator();
                        while (it.hasNext()) {
                            BatteryLevelData.PeriodBatteryLevelData periodBatteryLevelData2 =
                                    (BatteryLevelData.PeriodBatteryLevelData) it.next();
                            List list = batteryChartPreferenceController.mHourlyViewModels;
                            List list2 = periodBatteryLevelData2.mLevels;
                            List list3 = periodBatteryLevelData2.mTimestamps;
                            BatteryChartViewModel.AxisLabelPosition axisLabelPosition =
                                    BatteryChartViewModel.AxisLabelPosition.BETWEEN_TRAPEZOIDS;
                            BatteryChartPreferenceController.HourlyChartLabelTextGenerator
                                    hourlyChartLabelTextGenerator =
                                            batteryChartPreferenceController
                                                    .mHourlyChartLabelTextGenerator;
                            hourlyChartLabelTextGenerator.getClass();
                            BatteryLevelData.PeriodBatteryLevelData periodBatteryLevelData3 =
                                    (BatteryLevelData.PeriodBatteryLevelData)
                                            ((ArrayList)
                                                            batteryLevelData
                                                                    .mHourlyBatteryLevelsPerDay)
                                                    .get(0);
                            hourlyChartLabelTextGenerator.mIsStartTimestamp =
                                    periodBatteryLevelData3.mIsStartTimestamp;
                            hourlyChartLabelTextGenerator.mFistTimestamp =
                                    ((Long) periodBatteryLevelData3.mTimestamps.get(0)).longValue();
                            List list4 = batteryLevelData.mHourlyBatteryLevelsPerDay;
                            if (list4 != null) {
                                ArrayList arrayList = (ArrayList) list4;
                                if (!arrayList.isEmpty()) {
                                    obj2 =
                                            AlertController$$ExternalSyntheticOutline0.m(
                                                    1, arrayList);
                                    List list5 =
                                            ((BatteryLevelData.PeriodBatteryLevelData) obj2)
                                                    .mTimestamps;
                                    hourlyChartLabelTextGenerator.mLatestTimestamp =
                                            ((Long)
                                                            ((list5 != null || list5.isEmpty())
                                                                    ? null
                                                                    : PrioritySet$$ExternalSyntheticOutline0
                                                                            .m(1, list5)))
                                                    .longValue();
                                    ((ArrayList) list)
                                            .add(
                                                    new BatteryChartViewModel(
                                                            list2,
                                                            list3,
                                                            axisLabelPosition,
                                                            hourlyChartLabelTextGenerator));
                                }
                            }
                            obj2 = null;
                            List list52 =
                                    ((BatteryLevelData.PeriodBatteryLevelData) obj2).mTimestamps;
                            hourlyChartLabelTextGenerator.mLatestTimestamp =
                                    ((Long)
                                                    ((list52 != null || list52.isEmpty())
                                                            ? null
                                                            : PrioritySet$$ExternalSyntheticOutline0
                                                                    .m(1, list52)))
                                            .longValue();
                            ((ArrayList) list)
                                    .add(
                                            new BatteryChartViewModel(
                                                    list2,
                                                    list3,
                                                    axisLabelPosition,
                                                    hourlyChartLabelTextGenerator));
                        }
                        batteryChartPreferenceController.refreshUi();
                    }
                    Log.d(
                            "AdvancedBatteryUsage",
                            String.format(
                                    "Battery chart shows in %d millis",
                                    Long.valueOf(
                                            System.currentTimeMillis()
                                                    - powerUsageAdvanced.mResumeTimestamp)));
                }
            }
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {}
    }

    @Override // com.android.settings.fuelgauge.batteryusage.PowerUsageBase
    public final void refreshUi(int i) {}
}
