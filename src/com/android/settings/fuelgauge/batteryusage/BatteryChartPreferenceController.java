package com.android.settings.fuelgauge.batteryusage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

import com.google.common.base.Objects;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryChartPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnCreate,
                OnDestroy,
                OnSaveInstanceState,
                OnResume {
    public final SettingsActivity mActivity;
    public View mBatteryChartViewGroup;
    TextView mChartSummaryTextView;
    int mDailyChartIndex;
    final DailyChartLabelTextGenerator mDailyChartLabelTextGenerator;
    BatteryChartView mDailyChartView;
    int mDailyHighlightSlotIndex;
    public BatteryChartViewModel mDailyViewModel;
    public final Handler mHandler;
    public final AnonymousClass1 mHourlyChartFadeInAdapter;
    public final AnonymousClass1 mHourlyChartFadeOutAdapter;
    int mHourlyChartIndex;
    final HourlyChartLabelTextGenerator mHourlyChartLabelTextGenerator;
    BatteryChartView mHourlyChartView;
    int mHourlyHighlightSlotIndex;
    public List mHourlyViewModels;
    public boolean mIs24HourFormat;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public PowerUsageAdvanced$$ExternalSyntheticLambda1 mOnSelectedIndexUpdatedListener;
    Context mPrefContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class BaseLabelTextGenerator {
        public BaseLabelTextGenerator() {}

        private static String generateBatteryLevelText(Integer num) {
            return Utils.formatPercentage(num.intValue());
        }

        public String generateContentDescription(int i, List list) {
            return generateFullText(i, list);
        }

        public abstract String generateFullText(int i, List list);

        public final String generateSlotBatteryLevelText(int i, List list) {
            return BatteryChartPreferenceController.this.mPrefContext.getString(
                    R.string.battery_level_percentage,
                    generateBatteryLevelText((Integer) list.get(i == -1 ? 0 : i)),
                    generateBatteryLevelText(
                            (Integer) list.get(i == -1 ? list.size() - 1 : i + 1)));
        }

        public abstract String generateText(int i, List list);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DailyChartLabelTextGenerator extends BaseLabelTextGenerator {
        public DailyChartLabelTextGenerator() {
            super();
        }

        @Override // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController.BaseLabelTextGenerator
        public final String generateFullText(int i, List list) {
            Context context =
                    ((AbstractPreferenceController) BatteryChartPreferenceController.this).mContext;
            return DateFormat.format(
                            DateFormat.getBestDateTimePattern(
                                    ConvertUtils.getLocale(context), "EEEE"),
                            ((Long) list.get(i)).longValue())
                    .toString();
        }

        @Override // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController.BaseLabelTextGenerator
        public final String generateText(int i, List list) {
            Context context =
                    ((AbstractPreferenceController) BatteryChartPreferenceController.this).mContext;
            return DateFormat.format(
                            DateFormat.getBestDateTimePattern(
                                    ConvertUtils.getLocale(context), ImsProfile.TIMER_NAME_E),
                            ((Long) list.get(i)).longValue())
                    .toString();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HourlyChartLabelTextGenerator extends BaseLabelTextGenerator {
        public long mFistTimestamp;
        public boolean mIsStartTimestamp;
        public long mLatestTimestamp;

        public HourlyChartLabelTextGenerator() {
            super();
        }

        @Override // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController.BaseLabelTextGenerator
        public final String generateContentDescription(int i, List list) {
            return i == list.size() + (-1)
                    ? generateText(i, list)
                    : ((AbstractPreferenceController) BatteryChartPreferenceController.this)
                            .mContext.getString(
                                    R.string.battery_usage_timestamps_content_description,
                                    generateText(i, list),
                                    generateText(i + 1, list));
        }

        @Override // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController.BaseLabelTextGenerator
        public final String generateFullText(int i, List list) {
            return i == list.size() + (-1)
                    ? generateText(i, list)
                    : ((AbstractPreferenceController) BatteryChartPreferenceController.this)
                            .mContext.getString(
                                    R.string.battery_usage_timestamps_hyphen,
                                    generateText(i, list),
                                    generateText(i + 1, list));
        }

        @Override // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController.BaseLabelTextGenerator
        public final String generateText(int i, List list) {
            boolean equal = Objects.equal(list.get(i), Long.valueOf(this.mLatestTimestamp));
            BatteryChartPreferenceController batteryChartPreferenceController =
                    BatteryChartPreferenceController.this;
            if (equal) {
                return ((AbstractPreferenceController) batteryChartPreferenceController)
                        .mContext.getString(R.string.battery_usage_chart_label_now);
            }
            Long l = (Long) list.get(i);
            long longValue = l.longValue();
            boolean z = false;
            if (Objects.equal(l, Long.valueOf(this.mFistTimestamp))) {
                if (this.mIsStartTimestamp) {
                    z = true;
                } else {
                    Calendar sharpHourCalendar = TimestampUtils.getSharpHourCalendar(longValue);
                    sharpHourCalendar.add(11, sharpHourCalendar.get(11) % 2 == 0 ? 0 : -1);
                    longValue = sharpHourCalendar.getTimeInMillis();
                }
            }
            return DateFormat.format(
                            DateFormat.getBestDateTimePattern(
                                    ConvertUtils.getLocale(
                                            ((AbstractPreferenceController)
                                                            batteryChartPreferenceController)
                                                    .mContext),
                                    batteryChartPreferenceController.mIs24HourFormat
                                            ? "HHm"
                                            : z ? "hma" : "ha"),
                            longValue)
                    .toString();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController$1] */
    public BatteryChartPreferenceController(
            Context context, Lifecycle lifecycle, SettingsActivity settingsActivity) {
        super(context);
        this.mDailyChartIndex = -1;
        this.mHourlyChartIndex = -1;
        this.mDailyHighlightSlotIndex = -2;
        this.mHourlyHighlightSlotIndex = -2;
        this.mHandler = new Handler(Looper.getMainLooper());
        final int i = 0;
        this.mHourlyChartFadeInAdapter =
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController.1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        super.onAnimationCancel(animator);
                        BatteryChartView batteryChartView =
                                BatteryChartPreferenceController.this.mHourlyChartView;
                        if (batteryChartView != null) {
                            batteryChartView.setVisibility(i);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        BatteryChartView batteryChartView =
                                BatteryChartPreferenceController.this.mHourlyChartView;
                        if (batteryChartView != null) {
                            batteryChartView.setVisibility(i);
                        }
                    }
                };
        final int i2 = 8;
        this.mHourlyChartFadeOutAdapter =
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController.1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        super.onAnimationCancel(animator);
                        BatteryChartView batteryChartView =
                                BatteryChartPreferenceController.this.mHourlyChartView;
                        if (batteryChartView != null) {
                            batteryChartView.setVisibility(i2);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        BatteryChartView batteryChartView =
                                BatteryChartPreferenceController.this.mHourlyChartView;
                        if (batteryChartView != null) {
                            batteryChartView.setVisibility(i2);
                        }
                    }
                };
        this.mDailyChartLabelTextGenerator = new DailyChartLabelTextGenerator();
        this.mHourlyChartLabelTextGenerator = new HourlyChartLabelTextGenerator();
        this.mActivity = settingsActivity;
        this.mIs24HourFormat = DateFormat.is24HourFormat(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00cb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry
            getAppBatteryUsageData(android.content.Context r11, int r12, java.lang.String r13) {
        /*
            r0 = 0
            if (r13 != 0) goto L4
            return r0
        L4:
            long r1 = java.lang.System.currentTimeMillis()
            java.util.Calendar r4 = java.util.Calendar.getInstance()
            long r7 = com.android.settings.fuelgauge.batteryusage.DatabaseUtils.getLastFullChargeTime(r11)
            r5 = 0
            r3 = r11
            java.util.Map r3 = com.android.settings.fuelgauge.batteryusage.DatabaseUtils.getHistoryMapSinceLatestRecordBeforeQueryTimestamp(r3, r4, r5, r7)
            if (r3 == 0) goto Lab
            boolean r4 = r3.isEmpty()
            if (r4 == 0) goto L21
            goto Lab
        L21:
            int r4 = r3.size()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.Long r1 = com.android.settings.fuelgauge.BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0.m(r1)
            java.lang.Object[] r1 = new java.lang.Object[]{r4, r1}
            java.lang.String r2 = "getBatterySinceLastFullChargeUsageData() size=%d time=%d/ms"
            java.lang.String r1 = java.lang.String.format(r2, r1)
            java.lang.String r2 = "BatteryChartPreferenceController"
            android.util.Log.d(r2, r1)
            com.android.settings.fuelgauge.batteryusage.UserIdsSeries r5 = new com.android.settings.fuelgauge.batteryusage.UserIdsSeries
            r5.<init>(r11)
            java.util.Map r1 = com.android.settings.fuelgauge.batteryusage.DataProcessor.EMPTY_BATTERY_MAP
            boolean r1 = r3.isEmpty()
            if (r1 == 0) goto L52
            java.lang.String r11 = "DataProcessor"
            java.lang.String r1 = "getBatteryLevelData() returns null"
            android.util.Log.d(r11, r1)
        L50:
            r11 = r0
            goto La9
        L52:
            java.util.Map r7 = com.android.settings.fuelgauge.batteryusage.DataProcessor.getHistoryMapWithExpectedTimestamps(r3)
            com.android.settings.fuelgauge.batteryusage.BatteryLevelData r1 = com.android.settings.fuelgauge.batteryusage.DataProcessor.getLevelDataThroughProcessedHistoryMap(r11, r7)
            java.util.Map r2 = com.android.settings.fuelgauge.batteryusage.DataProcessor.getCurrentBatteryHistoryMapFromStatsService(r11)
            r3 = r7
            android.util.ArrayMap r3 = (android.util.ArrayMap) r3
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L69:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L87
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r6 = r4.getValue()
            java.util.Map r6 = (java.util.Map) r6
            java.lang.String r8 = "CURRENT_TIME_BATTERY_HISTORY_PLACEHOLDER"
            boolean r6 = r6.containsKey(r8)
            if (r6 == 0) goto L69
            r4.setValue(r2)
            goto L69
        L87:
            if (r1 != 0) goto L8a
            goto L50
        L8a:
            java.util.List r6 = r1.mHourlyBatteryLevelsPerDay
            java.util.Set<java.lang.String> r2 = com.android.settings.fuelgauge.batteryusage.DataProcessor.sTestSystemAppsPackageNames
            if (r2 == 0) goto L92
        L90:
            r9 = r2
            goto L9b
        L92:
            int r2 = r11.getUserId()
            java.util.Set r2 = com.android.settingslib.spaprivileged.model.app.AppListRepositoryUtil.getSystemPackageNames(r11, r2)
            goto L90
        L9b:
            java.util.Set r10 = com.android.settings.fuelgauge.batteryusage.DataProcessor.getSystemAppsUids(r11)
            r8 = 0
            r4 = r11
            java.util.Map r2 = com.android.settings.fuelgauge.batteryusage.DataProcessor.getBatteryDiffDataMap(r4, r5, r6, r7, r8, r9, r10)
            java.util.Map r11 = com.android.settings.fuelgauge.batteryusage.DataProcessor.generateBatteryUsageMap(r11, r2, r1)
        La9:
            if (r11 != 0) goto Lad
        Lab:
            r11 = r0
            goto Lc9
        Lad:
            r1 = -1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)
            android.util.ArrayMap r11 = (android.util.ArrayMap) r11
            java.lang.Object r11 = r11.get(r2)
            java.util.Map r11 = (java.util.Map) r11
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.Object r11 = r11.get(r1)
            com.android.settings.fuelgauge.batteryusage.BatteryDiffData r11 = (com.android.settings.fuelgauge.batteryusage.BatteryDiffData) r11
            if (r11 != 0) goto Lc7
            goto Lab
        Lc7:
            java.util.List r11 = r11.mAppEntries
        Lc9:
            if (r11 != 0) goto Lcc
            return r0
        Lcc:
            java.util.Iterator r11 = r11.iterator()
        Ld0:
            boolean r1 = r11.hasNext()
            if (r1 == 0) goto Lf4
            java.lang.Object r1 = r11.next()
            com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry r1 = (com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry) r1
            boolean r2 = r1.isSystemEntry()
            if (r2 != 0) goto Ld0
            long r2 = r1.mUserId
            long r4 = (long) r12
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto Ld0
            java.lang.String r2 = r1.getPackageName()
            boolean r2 = r13.equals(r2)
            if (r2 == 0) goto Ld0
            return r1
        Lf4:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController.getAppBatteryUsageData(android.content.Context,"
                    + " int,"
                    + " java.lang.String):com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry");
    }

    public static int getTotalHours(BatteryLevelData batteryLevelData) {
        if (batteryLevelData == null) {
            return 0;
        }
        List list = batteryLevelData.mDailyBatteryLevels.mTimestamps;
        return (int)
                ((((Long) PrioritySet$$ExternalSyntheticOutline0.m(1, list)).longValue()
                                - ((Long) list.get(0)).longValue())
                        / 3600000);
    }

    public final void animateBatteryHourlyChartView(boolean z) {
        BatteryChartView batteryChartView = this.mHourlyChartView;
        if (batteryChartView != null) {
            if ((batteryChartView.getVisibility() == 0) == z) {
                return;
            }
            if (!z) {
                this.mHourlyChartView
                        .animate()
                        .alpha(0.0f)
                        .setDuration(200L)
                        .setListener(this.mHourlyChartFadeOutAdapter)
                        .start();
            } else {
                this.mHourlyChartView.setVisibility(0);
                this.mHourlyChartView
                        .animate()
                        .alpha(1.0f)
                        .setDuration(400L)
                        .setListener(this.mHourlyChartFadeInAdapter)
                        .start();
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPrefContext = preferenceScreen.getContext();
    }

    public final String getAccessibilityAnnounceMessage() {
        String slotInformation = getSlotInformation(true);
        return this.mPrefContext.getString(
                R.string.battery_usage_time_info_and_battery_level,
                slotInformation == null
                        ? this.mPrefContext.getString(
                                R.string.battery_usage_breakdown_title_since_last_full_charge)
                        : this.mPrefContext.getString(
                                R.string.battery_usage_breakdown_title_for_slot, slotInformation),
                getBatteryLevelPercentageInfo());
    }

    public String getBatteryLevelPercentageInfo() {
        List list;
        BatteryChartViewModel batteryChartViewModel = this.mDailyViewModel;
        if (batteryChartViewModel == null || (list = this.mHourlyViewModels) == null) {
            return ApnSettings.MVNO_NONE;
        }
        int i = this.mDailyChartIndex;
        return (i == -1 || this.mHourlyChartIndex == -1)
                ? batteryChartViewModel.getSlotBatteryLevelText(i)
                : ((BatteryChartViewModel) ((ArrayList) list).get(i))
                        .getSlotBatteryLevelText(this.mHourlyChartIndex);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "battery_chart";
    }

    public final String getSlotInformation(boolean z) {
        List list;
        if (this.mDailyViewModel == null || (list = this.mHourlyViewModels) == null) {
            return null;
        }
        if (((list != null && ((ArrayList) list).size() == 1) || this.mDailyChartIndex == -1)
                && this.mHourlyChartIndex == -1) {
            return null;
        }
        String contentDescription =
                z
                        ? this.mDailyViewModel.getContentDescription(this.mDailyChartIndex)
                        : this.mDailyViewModel.getFullText(this.mDailyChartIndex);
        if (this.mHourlyChartIndex == -1) {
            return contentDescription;
        }
        String contentDescription2 =
                z
                        ? ((BatteryChartViewModel)
                                        ((ArrayList) this.mHourlyViewModels)
                                                .get(this.mDailyChartIndex))
                                .getContentDescription(this.mHourlyChartIndex)
                        : ((BatteryChartViewModel)
                                        ((ArrayList) this.mHourlyViewModels)
                                                .get(this.mDailyChartIndex))
                                .getFullText(this.mHourlyChartIndex);
        List list2 = this.mHourlyViewModels;
        return (list2 == null || ((ArrayList) list2).size() != 1)
                ? this.mContext.getString(
                        R.string.battery_usage_day_and_hour,
                        contentDescription,
                        contentDescription2)
                : contentDescription2;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public final void onCreate(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        this.mDailyChartIndex = bundle.getInt("daily_chart_index", this.mDailyChartIndex);
        this.mHourlyChartIndex = bundle.getInt("hourly_chart_index", this.mHourlyChartIndex);
        Log.d(
                "BatteryChartPreferenceController",
                String.format(
                        "onCreate() dailyIndex=%d hourlyIndex=%d",
                        Integer.valueOf(this.mDailyChartIndex),
                        Integer.valueOf(this.mHourlyChartIndex)));
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        SettingsActivity settingsActivity = this.mActivity;
        if (settingsActivity == null || settingsActivity.isChangingConfigurations()) {
            BatteryDiffEntry.clearCache();
        }
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mIs24HourFormat = DateFormat.is24HourFormat(this.mContext);
        this.mMetricsFeatureProvider.action(this.mPrefContext, 1880, new Pair[0]);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public final void onSaveInstanceState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        bundle.putInt("daily_chart_index", this.mDailyChartIndex);
        bundle.putInt("hourly_chart_index", this.mHourlyChartIndex);
        Log.d(
                "BatteryChartPreferenceController",
                String.format(
                        "onSaveInstanceState() dailyIndex=%d hourlyIndex=%d",
                        Integer.valueOf(this.mDailyChartIndex),
                        Integer.valueOf(this.mHourlyChartIndex)));
    }

    public void refreshUi() {
        if (this.mDailyChartView == null || this.mHourlyChartView == null) {
            return;
        }
        if (this.mDailyViewModel == null || this.mHourlyViewModels == null) {
            TextView textView = this.mChartSummaryTextView;
            if (textView != null) {
                textView.setVisibility(8);
            }
            this.mDailyChartView.setVisibility(8);
            this.mHourlyChartView.setVisibility(8);
            this.mDailyChartView.setViewModel(null);
            this.mHourlyChartView.setViewModel(null);
            return;
        }
        TextView textView2 = this.mChartSummaryTextView;
        if (textView2 != null) {
            textView2.setVisibility(0);
        }
        List list = this.mHourlyViewModels;
        if (list != null && ((ArrayList) list).size() == 1) {
            this.mDailyChartView.setVisibility(8);
            this.mDailyChartIndex = 0;
        } else {
            this.mDailyChartView.setVisibility(0);
            if (this.mDailyChartIndex >= this.mDailyViewModel.mLevels.size()) {
                this.mDailyChartIndex = -1;
            }
            BatteryChartViewModel batteryChartViewModel = this.mDailyViewModel;
            batteryChartViewModel.mSelectedIndex = this.mDailyChartIndex;
            batteryChartViewModel.mHighlightSlotIndex = this.mDailyHighlightSlotIndex;
            this.mDailyChartView.setViewModel(batteryChartViewModel);
        }
        if (this.mDailyChartIndex == -1) {
            animateBatteryHourlyChartView(false);
            return;
        }
        animateBatteryHourlyChartView(true);
        BatteryChartViewModel batteryChartViewModel2 =
                (BatteryChartViewModel)
                        ((ArrayList) this.mHourlyViewModels).get(this.mDailyChartIndex);
        if (this.mHourlyChartIndex >= batteryChartViewModel2.mLevels.size()) {
            this.mHourlyChartIndex = -1;
        }
        batteryChartViewModel2.mSelectedIndex = this.mHourlyChartIndex;
        batteryChartViewModel2.mHighlightSlotIndex =
                this.mDailyChartIndex == this.mDailyHighlightSlotIndex
                        ? this.mHourlyHighlightSlotIndex
                        : -2;
        this.mHourlyChartView.setViewModel(batteryChartViewModel2);
    }

    public final void setBatteryChartView(
            final BatteryChartView batteryChartView, final BatteryChartView batteryChartView2) {
        View view = (View) batteryChartView.getParent();
        if (view != null && view.getId() == R.id.battery_chart_group) {
            this.mBatteryChartViewGroup = (View) batteryChartView.getParent();
        }
        if (this.mDailyChartView != batteryChartView
                || this.mHourlyChartView != batteryChartView2) {
            this.mHandler.post(
                    new Runnable() { // from class:
                        // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final BatteryChartPreferenceController
                                    batteryChartPreferenceController =
                                            BatteryChartPreferenceController.this;
                            BatteryChartView batteryChartView3 = batteryChartView;
                            BatteryChartView batteryChartView4 = batteryChartView2;
                            batteryChartPreferenceController.mDailyChartView = batteryChartView3;
                            final int i = 0;
                            batteryChartView3.setOnSelectListener(
                                    new BatteryChartView.OnSelectListener() { // from class:
                                        // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController$$ExternalSyntheticLambda2
                                        @Override // com.android.settings.fuelgauge.batteryusage.BatteryChartView.OnSelectListener
                                        public final void onSelect(int i2) {
                                            BatteryChartPreferenceController
                                                    batteryChartPreferenceController2 =
                                                            batteryChartPreferenceController;
                                            switch (i) {
                                                case 0:
                                                    if (batteryChartPreferenceController2
                                                                    .mDailyChartIndex
                                                            != i2) {
                                                        ListPopupWindow$$ExternalSyntheticOutline0
                                                                .m1m(
                                                                        i2,
                                                                        "onDailyChartSelect:",
                                                                        "BatteryChartPreferenceController");
                                                        batteryChartPreferenceController2
                                                                        .mDailyChartIndex =
                                                                i2;
                                                        batteryChartPreferenceController2
                                                                        .mHourlyChartIndex =
                                                                -1;
                                                        batteryChartPreferenceController2
                                                                .refreshUi();
                                                        batteryChartPreferenceController2.mHandler
                                                                .post(
                                                                        new BatteryChartPreferenceController$$ExternalSyntheticLambda1(
                                                                                batteryChartPreferenceController2,
                                                                                1));
                                                        batteryChartPreferenceController2
                                                                .mMetricsFeatureProvider.action(
                                                                batteryChartPreferenceController2
                                                                        .mPrefContext,
                                                                i2 == -1 ? 1800 : 1799,
                                                                batteryChartPreferenceController2
                                                                        .mDailyChartIndex);
                                                        PowerUsageAdvanced$$ExternalSyntheticLambda1
                                                                powerUsageAdvanced$$ExternalSyntheticLambda1 =
                                                                        batteryChartPreferenceController2
                                                                                .mOnSelectedIndexUpdatedListener;
                                                        if (powerUsageAdvanced$$ExternalSyntheticLambda1
                                                                != null) {
                                                            BaseSearchIndexProvider
                                                                    baseSearchIndexProvider =
                                                                            PowerUsageAdvanced
                                                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                                            powerUsageAdvanced$$ExternalSyntheticLambda1
                                                                    .f$0
                                                                    .onSelectedSlotDataUpdated();
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    if (batteryChartPreferenceController2
                                                                            .mDailyChartIndex
                                                                    != -1
                                                            && batteryChartPreferenceController2
                                                                            .mHourlyChartIndex
                                                                    != i2) {
                                                        ListPopupWindow$$ExternalSyntheticOutline0
                                                                .m1m(
                                                                        i2,
                                                                        "onHourlyChartSelect:",
                                                                        "BatteryChartPreferenceController");
                                                        batteryChartPreferenceController2
                                                                        .mHourlyChartIndex =
                                                                i2;
                                                        batteryChartPreferenceController2
                                                                .refreshUi();
                                                        batteryChartPreferenceController2.mHandler
                                                                .post(
                                                                        new BatteryChartPreferenceController$$ExternalSyntheticLambda1(
                                                                                batteryChartPreferenceController2,
                                                                                2));
                                                        batteryChartPreferenceController2
                                                                .mMetricsFeatureProvider.action(
                                                                batteryChartPreferenceController2
                                                                        .mPrefContext,
                                                                i2 == -1 ? 1767 : 1766,
                                                                batteryChartPreferenceController2
                                                                        .mHourlyChartIndex);
                                                        PowerUsageAdvanced$$ExternalSyntheticLambda1
                                                                powerUsageAdvanced$$ExternalSyntheticLambda12 =
                                                                        batteryChartPreferenceController2
                                                                                .mOnSelectedIndexUpdatedListener;
                                                        if (powerUsageAdvanced$$ExternalSyntheticLambda12
                                                                != null) {
                                                            BaseSearchIndexProvider
                                                                    baseSearchIndexProvider2 =
                                                                            PowerUsageAdvanced
                                                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                                            powerUsageAdvanced$$ExternalSyntheticLambda12
                                                                    .f$0
                                                                    .onSelectedSlotDataUpdated();
                                                            break;
                                                        }
                                                    }
                                                    break;
                                            }
                                        }
                                    });
                            batteryChartPreferenceController.mHourlyChartView = batteryChartView4;
                            final int i2 = 1;
                            batteryChartView4.setOnSelectListener(
                                    new BatteryChartView.OnSelectListener() { // from class:
                                        // com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController$$ExternalSyntheticLambda2
                                        @Override // com.android.settings.fuelgauge.batteryusage.BatteryChartView.OnSelectListener
                                        public final void onSelect(int i22) {
                                            BatteryChartPreferenceController
                                                    batteryChartPreferenceController2 =
                                                            batteryChartPreferenceController;
                                            switch (i2) {
                                                case 0:
                                                    if (batteryChartPreferenceController2
                                                                    .mDailyChartIndex
                                                            != i22) {
                                                        ListPopupWindow$$ExternalSyntheticOutline0
                                                                .m1m(
                                                                        i22,
                                                                        "onDailyChartSelect:",
                                                                        "BatteryChartPreferenceController");
                                                        batteryChartPreferenceController2
                                                                        .mDailyChartIndex =
                                                                i22;
                                                        batteryChartPreferenceController2
                                                                        .mHourlyChartIndex =
                                                                -1;
                                                        batteryChartPreferenceController2
                                                                .refreshUi();
                                                        batteryChartPreferenceController2.mHandler
                                                                .post(
                                                                        new BatteryChartPreferenceController$$ExternalSyntheticLambda1(
                                                                                batteryChartPreferenceController2,
                                                                                1));
                                                        batteryChartPreferenceController2
                                                                .mMetricsFeatureProvider.action(
                                                                batteryChartPreferenceController2
                                                                        .mPrefContext,
                                                                i22 == -1 ? 1800 : 1799,
                                                                batteryChartPreferenceController2
                                                                        .mDailyChartIndex);
                                                        PowerUsageAdvanced$$ExternalSyntheticLambda1
                                                                powerUsageAdvanced$$ExternalSyntheticLambda1 =
                                                                        batteryChartPreferenceController2
                                                                                .mOnSelectedIndexUpdatedListener;
                                                        if (powerUsageAdvanced$$ExternalSyntheticLambda1
                                                                != null) {
                                                            BaseSearchIndexProvider
                                                                    baseSearchIndexProvider =
                                                                            PowerUsageAdvanced
                                                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                                            powerUsageAdvanced$$ExternalSyntheticLambda1
                                                                    .f$0
                                                                    .onSelectedSlotDataUpdated();
                                                            break;
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    if (batteryChartPreferenceController2
                                                                            .mDailyChartIndex
                                                                    != -1
                                                            && batteryChartPreferenceController2
                                                                            .mHourlyChartIndex
                                                                    != i22) {
                                                        ListPopupWindow$$ExternalSyntheticOutline0
                                                                .m1m(
                                                                        i22,
                                                                        "onHourlyChartSelect:",
                                                                        "BatteryChartPreferenceController");
                                                        batteryChartPreferenceController2
                                                                        .mHourlyChartIndex =
                                                                i22;
                                                        batteryChartPreferenceController2
                                                                .refreshUi();
                                                        batteryChartPreferenceController2.mHandler
                                                                .post(
                                                                        new BatteryChartPreferenceController$$ExternalSyntheticLambda1(
                                                                                batteryChartPreferenceController2,
                                                                                2));
                                                        batteryChartPreferenceController2
                                                                .mMetricsFeatureProvider.action(
                                                                batteryChartPreferenceController2
                                                                        .mPrefContext,
                                                                i22 == -1 ? 1767 : 1766,
                                                                batteryChartPreferenceController2
                                                                        .mHourlyChartIndex);
                                                        PowerUsageAdvanced$$ExternalSyntheticLambda1
                                                                powerUsageAdvanced$$ExternalSyntheticLambda12 =
                                                                        batteryChartPreferenceController2
                                                                                .mOnSelectedIndexUpdatedListener;
                                                        if (powerUsageAdvanced$$ExternalSyntheticLambda12
                                                                != null) {
                                                            BaseSearchIndexProvider
                                                                    baseSearchIndexProvider2 =
                                                                            PowerUsageAdvanced
                                                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                                            powerUsageAdvanced$$ExternalSyntheticLambda12
                                                                    .f$0
                                                                    .onSelectedSlotDataUpdated();
                                                            break;
                                                        }
                                                    }
                                                    break;
                                            }
                                        }
                                    });
                            batteryChartPreferenceController.refreshUi();
                        }
                    });
            View view2 = this.mBatteryChartViewGroup;
            if (view2 != null && view2.getAlpha() == 0.0f) {
                this.mBatteryChartViewGroup.animate().alpha(1.0f).setDuration(400L).start();
            }
        }
        View view3 = this.mBatteryChartViewGroup;
        if (view3 != null) {
            View view4 = (View) view3.getParent();
            this.mChartSummaryTextView =
                    view4 != null ? (TextView) view4.findViewById(R.id.chart_summary) : null;
        }
    }
}
