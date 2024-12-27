package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.fuelgauge.AdvancedPowerUsageDetail;
import com.android.settings.fuelgauge.BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryUsageBreakdownController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnDestroy {
    private static final String ANOMALY_KEY = "anomaly_key";
    private static final String APP_LIST_PREFERENCE_KEY = "app_list";
    private static final String FOOTER_PREFERENCE_KEY = "battery_usage_footer";
    private static final String PACKAGE_NAME_NONE = "none";
    private static final String ROOT_PREFERENCE_KEY = "battery_usage_breakdown";
    private static final String SLOT_TIMESTAMP = "slot_timestamp";
    private static final String SPINNER_PREFERENCE_KEY = "battery_usage_spinner";
    private static final String TAG = "BatteryUsageBreakdownController";
    private final SettingsActivity mActivity;

    @VisibleForTesting String mAnomalyEntryKey;

    @VisibleForTesting String mAnomalyHintPrefKey;

    @VisibleForTesting String mAnomalyHintString;

    @VisibleForTesting int mAnomalyKeyNumber;

    @VisibleForTesting PreferenceGroup mAppListPreferenceGroup;

    @VisibleForTesting BatteryDiffData mBatteryDiffData;

    @VisibleForTesting String mBatteryUsageBreakdownTitleLastFullChargeText;

    @VisibleForTesting FooterPreference mFooterPreference;
    private final InstrumentedPreferenceFragment mFragment;
    private final Handler mHandler;

    @VisibleForTesting boolean mIsHighlightSlot;
    private final MetricsFeatureProvider mMetricsFeatureProvider;

    @VisibleForTesting String mPercentLessThanThresholdContentDescription;

    @VisibleForTesting String mPercentLessThanThresholdText;

    @VisibleForTesting Context mPrefContext;

    @VisibleForTesting final Map<String, Preference> mPreferenceCache;

    @VisibleForTesting PreferenceCategory mRootPreference;
    private String mSlotInformation;
    private int mSpinnerPosition;

    @VisibleForTesting SpinnerPreference mSpinnerPreference;
    private static final List<BatteryDiffEntry> EMPTY_ENTRY_LIST = new ArrayList();
    private static int sUiMode = 0;

    public BatteryUsageBreakdownController(
            Context context,
            Lifecycle lifecycle,
            SettingsActivity settingsActivity,
            InstrumentedPreferenceFragment instrumentedPreferenceFragment) {
        super(context, ROOT_PREFERENCE_KEY);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mPreferenceCache = new ArrayMap();
        this.mActivity = settingsActivity;
        this.mFragment = instrumentedPreferenceFragment;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    private List<BatteryDiffEntry> getBatteryDiffEntries() {
        BatteryDiffData batteryDiffData = this.mBatteryDiffData;
        return batteryDiffData == null
                ? EMPTY_ENTRY_LIST
                : this.mSpinnerPosition == 0
                        ? batteryDiffData.mAppEntries
                        : batteryDiffData.mSystemEntries;
    }

    private boolean isAnomalyBatteryDiffEntry(BatteryDiffEntry batteryDiffEntry) {
        String str;
        return this.mIsHighlightSlot
                && (str = this.mAnomalyEntryKey) != null
                && str.equals(batteryDiffEntry.mKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void lambda$removeAndCacheAllUnusedPreferences$2(
            Set set, BatteryDiffEntry batteryDiffEntry) {
        set.add(batteryDiffEntry.mKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSpinnerAndAppList$1() {
        lambda$showSpinnerAndAppList$0();
        addAllPreferences();
    }

    private void logPreferenceClickedMetrics(BatteryDiffEntry batteryDiffEntry) {
        int i = batteryDiffEntry.isSystemEntry() ? 1769 : 1768;
        String packageName =
                TextUtils.isEmpty(batteryDiffEntry.getPackageName())
                        ? "none"
                        : batteryDiffEntry.getPackageName();
        int round = (int) Math.round(batteryDiffEntry.mPercentage);
        int i2 = (int) (this.mBatteryDiffData.mStartTimestamp / 1000);
        int i3 = i;
        this.mMetricsFeatureProvider.action(1880, i3, 1880, round, packageName);
        this.mMetricsFeatureProvider.action(1880, i3, 1880, i2, SLOT_TIMESTAMP);
        if (isAnomalyBatteryDiffEntry(batteryDiffEntry)) {
            this.mMetricsFeatureProvider.action(1880, i, 1880, this.mAnomalyKeyNumber, ANOMALY_KEY);
        }
    }

    private void showCategoryTitle(String str, String str2) {
        this.mRootPreference.setTitle(
                Utils.createAccessibleSequence(
                        str2 == null
                                ? this.mBatteryUsageBreakdownTitleLastFullChargeText
                                : this.mPrefContext.getString(
                                        R.string.battery_usage_breakdown_title_for_slot, str2),
                        str == null
                                ? this.mBatteryUsageBreakdownTitleLastFullChargeText
                                : this.mPrefContext.getString(
                                        R.string.battery_usage_breakdown_title_for_slot, str)));
        this.mRootPreference.setVisible(true);
    }

    private void showFooterPreference(boolean z) {
        this.mFooterPreference.setTitle(
                this.mPrefContext.getString(
                        z
                                ? R.string.battery_usage_screen_footer_empty
                                : R.string.battery_usage_screen_footer));
        this.mFooterPreference.setVisible(true);
    }

    private void showSpinnerAndAppList() {
        if (this.mBatteryDiffData == null) {
            this.mHandler.post(
                    new BatteryUsageBreakdownController$$ExternalSyntheticLambda1(0, this));
            return;
        }
        this.mSpinnerPreference.setVisible(true);
        this.mAppListPreferenceGroup.setVisible(true);
        this.mHandler.post(new BatteryUsageBreakdownController$$ExternalSyntheticLambda1(1, this));
    }

    @VisibleForTesting
    public void addAllPreferences() {
        boolean z;
        if (this.mBatteryDiffData == null) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        List<BatteryDiffEntry> batteryDiffEntries = getBatteryDiffEntries();
        int preferenceCount = this.mAppListPreferenceGroup.getPreferenceCount();
        for (BatteryDiffEntry batteryDiffEntry : batteryDiffEntries) {
            String appLabel = batteryDiffEntry.getAppLabel();
            batteryDiffEntry.loadLabelAndIcon();
            Drawable drawable = batteryDiffEntry.mAppIcon;
            Drawable newDrawable =
                    (drawable == null || drawable.getConstantState() == null)
                            ? null
                            : batteryDiffEntry.mAppIcon.getConstantState().newDrawable();
            if (TextUtils.isEmpty(appLabel) || newDrawable == null) {
                Log.w(TAG, "cannot find app resource for:" + batteryDiffEntry.getPackageName());
            } else {
                PreferenceGroup preferenceGroup = this.mAppListPreferenceGroup;
                String str = batteryDiffEntry.mKey;
                AnomalyAppItemPreference anomalyAppItemPreference =
                        (AnomalyAppItemPreference) preferenceGroup.findPreference(str);
                if (anomalyAppItemPreference != null) {
                    z = true;
                } else {
                    anomalyAppItemPreference =
                            (AnomalyAppItemPreference) this.mPreferenceCache.get(str);
                    z = false;
                }
                if (anomalyAppItemPreference == null) {
                    anomalyAppItemPreference =
                            new AnomalyAppItemPreference(this.mPrefContext, null);
                    anomalyAppItemPreference.setLayoutResource(
                            R.layout.anomaly_app_item_preference);
                    anomalyAppItemPreference.setKey(str);
                    this.mPreferenceCache.put(str, anomalyAppItemPreference);
                }
                anomalyAppItemPreference.setIcon(newDrawable);
                anomalyAppItemPreference.setTitle(appLabel);
                anomalyAppItemPreference.setOrder(preferenceCount);
                anomalyAppItemPreference.setSingleLineTitle(true);
                String str2 =
                        isAnomalyBatteryDiffEntry(batteryDiffEntry)
                                ? this.mAnomalyHintString
                                : null;
                if (!TextUtils.equals(anomalyAppItemPreference.mAnomalyHintText, str2)) {
                    anomalyAppItemPreference.mAnomalyHintText = str2;
                    anomalyAppItemPreference.notifyChanged();
                }
                anomalyAppItemPreference.mBatteryDiffEntry = batteryDiffEntry;
                batteryDiffEntry.loadLabelAndIcon();
                anomalyAppItemPreference.setSelectable(batteryDiffEntry.mValidForRestriction);
                setPreferencePercentage(anomalyAppItemPreference, batteryDiffEntry);
                setPreferenceSummary(anomalyAppItemPreference, batteryDiffEntry);
                if (!z) {
                    this.mAppListPreferenceGroup.addPreference(anomalyAppItemPreference);
                }
                preferenceCount++;
            }
        }
        Log.d(
                TAG,
                String.format(
                        "addAllPreferences() is finished in %d/ms",
                        BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0.m(
                                currentTimeMillis)));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPrefContext = preferenceScreen.getContext();
        this.mRootPreference =
                (PreferenceCategory) preferenceScreen.findPreference(ROOT_PREFERENCE_KEY);
        this.mSpinnerPreference =
                (SpinnerPreference) preferenceScreen.findPreference(SPINNER_PREFERENCE_KEY);
        this.mAppListPreferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(APP_LIST_PREFERENCE_KEY);
        this.mFooterPreference =
                (FooterPreference) preferenceScreen.findPreference(FOOTER_PREFERENCE_KEY);
        this.mBatteryUsageBreakdownTitleLastFullChargeText =
                this.mPrefContext.getString(
                        R.string.battery_usage_breakdown_title_since_last_full_charge);
        String formatPercentage = com.android.settingslib.Utils.formatPercentage(1.0d, false);
        this.mPercentLessThanThresholdText =
                this.mPrefContext.getString(
                        R.string.battery_usage_less_than_percent, formatPercentage);
        this.mPercentLessThanThresholdContentDescription =
                this.mPrefContext.getString(
                        R.string.battery_usage_less_than_percent_content_description,
                        formatPercentage);
        this.mAppListPreferenceGroup.mOrderingAsAdded = false;
        SpinnerPreference spinnerPreference = this.mSpinnerPreference;
        String[] strArr = {
            this.mPrefContext.getString(R.string.battery_usage_spinner_view_by_apps),
            this.mPrefContext.getString(R.string.battery_usage_spinner_view_by_systems)
        };
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        spinnerPreference.mItems = strArr;
        spinnerPreference.mOnItemSelectedListener = anonymousClass1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public void handleBatteryUsageUpdated(
            BatteryDiffData batteryDiffData,
            String str,
            String str2,
            boolean z,
            boolean z2,
            Optional<AnomalyEventWrapper> optional) {
        String str3;
        this.mBatteryDiffData = batteryDiffData;
        this.mSlotInformation = str;
        this.mIsHighlightSlot = z2;
        if (optional != null) {
            AnomalyEventWrapper orElse = optional.orElse(null);
            this.mAnomalyKeyNumber =
                    orElse != null ? orElse.mPowerAnomalyEvent.getKey().getNumber() : -1;
            this.mAnomalyEntryKey = orElse != null ? orElse.getAnomalyEntryKey() : null;
            if (orElse != null) {
                str3 =
                        (String)
                                orElse.getInfo(
                                        null, new AnomalyEventWrapper$$ExternalSyntheticLambda0(4));
                if (TextUtils.isEmpty(str3)) {
                    str3 =
                            orElse.getStringFromArrayResource(
                                    R.array.power_anomaly_hint_messages, orElse.mResourceIndex);
                }
            } else {
                str3 = null;
            }
            this.mAnomalyHintString = str3;
            this.mAnomalyHintPrefKey =
                    orElse != null
                            ? (String)
                                    orElse.getInfo(
                                            null,
                                            new AnomalyEventWrapper$$ExternalSyntheticLambda0(0))
                            : null;
        }
        showCategoryTitle(str, str2);
        showSpinnerAndAppList();
        showFooterPreference(z);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!(preference instanceof PowerGaugePreference)) {
            return false;
        }
        PowerGaugePreference powerGaugePreference = (PowerGaugePreference) preference;
        BatteryDiffEntry batteryDiffEntry = powerGaugePreference.mBatteryDiffEntry;
        logPreferenceClickedMetrics(batteryDiffEntry);
        String appLabel = batteryDiffEntry.getAppLabel();
        String packageName = batteryDiffEntry.getPackageName();
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "handleClick() label=", appLabel, " key=");
        m.append(batteryDiffEntry.mKey);
        m.append(" package=");
        m.append(packageName);
        Log.d(TAG, m.toString());
        AdvancedPowerUsageDetail.startBatteryDetailPage(
                this.mActivity,
                this.mFragment.getMetricsCategory(),
                batteryDiffEntry,
                powerGaugePreference.mProgress.toString(),
                this.mSlotInformation,
                true,
                isAnomalyBatteryDiffEntry(batteryDiffEntry) ? this.mAnomalyHintPrefKey : null,
                isAnomalyBatteryDiffEntry(batteryDiffEntry) ? this.mAnomalyHintString : null);
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mPreferenceCache.clear();
        this.mAppListPreferenceGroup.removeAll();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        int i = this.mContext.getResources().getConfiguration().uiMode & 48;
        if (sUiMode != i) {
            sUiMode = i;
            BatteryDiffEntry.clearCache();
            this.mPreferenceCache.clear();
            Log.d(TAG, "clear icon and label cache since uiMode is changed");
        }
    }

    @VisibleForTesting
    /* renamed from: removeAndCacheAllUnusedPreferences, reason: merged with bridge method [inline-methods] */
    public void lambda$showSpinnerAndAppList$0() {
        List<BatteryDiffEntry> batteryDiffEntries = getBatteryDiffEntries();
        final ArraySet arraySet = new ArraySet(batteryDiffEntries.size());
        batteryDiffEntries.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.fuelgauge.batteryusage.BatteryUsageBreakdownController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        BatteryUsageBreakdownController.lambda$removeAndCacheAllUnusedPreferences$2(
                                arraySet, (BatteryDiffEntry) obj);
                    }
                });
        for (int preferenceCount = this.mAppListPreferenceGroup.getPreferenceCount() - 1;
                preferenceCount >= 0;
                preferenceCount--) {
            Preference preference = this.mAppListPreferenceGroup.getPreference(preferenceCount);
            if (!arraySet.contains(preference.getKey())) {
                if (!TextUtils.isEmpty(preference.getKey())) {
                    this.mPreferenceCache.put(preference.getKey(), preference);
                }
                this.mAppListPreferenceGroup.removePreference(preference);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @VisibleForTesting
    public void setPreferencePercentage(
            PowerGaugePreference powerGaugePreference, BatteryDiffEntry batteryDiffEntry) {
        double d = batteryDiffEntry.mPercentage;
        if (d >= 1.0d) {
            String formatPercentage =
                    com.android.settingslib.Utils.formatPercentage(
                            d + batteryDiffEntry.mAdjustPercentageOffset, true);
            powerGaugePreference.mProgress = formatPercentage;
            powerGaugePreference.mProgressContentDescription = formatPercentage;
            powerGaugePreference.notifyChanged();
            return;
        }
        String str = this.mPercentLessThanThresholdText;
        powerGaugePreference.mProgress = str;
        powerGaugePreference.mProgressContentDescription = str;
        powerGaugePreference.notifyChanged();
        powerGaugePreference.mProgressContentDescription =
                this.mPercentLessThanThresholdContentDescription;
        powerGaugePreference.notifyChanged();
    }

    @VisibleForTesting
    public void setPreferenceSummary(
            PowerGaugePreference powerGaugePreference, BatteryDiffEntry batteryDiffEntry) {
        Context context = this.mPrefContext;
        boolean isSystemEntry = batteryDiffEntry.isSystemEntry();
        long j = batteryDiffEntry.mForegroundUsageTimeInMs;
        long j2 =
                batteryDiffEntry.mBackgroundUsageTimeInMs
                        + batteryDiffEntry.mForegroundServiceUsageTimeInMs;
        long j3 = batteryDiffEntry.mScreenOnTimeInMs;
        StringBuilder sb = new StringBuilder();
        if (isSystemEntry) {
            long j4 = j + j2;
            if (j4 != 0) {
                sb.append(
                        BatteryUtils.buildBatteryUsageTimeInfo(
                                context,
                                j4,
                                R.string.battery_usage_total_less_than_one_minute,
                                R.string.battery_usage_for_total_time));
            }
        } else {
            if (j3 != 0) {
                sb.append(
                        BatteryUtils.buildBatteryUsageTimeInfo(
                                context,
                                j3,
                                R.string.battery_usage_screen_time_less_than_one_minute,
                                R.string.battery_usage_screen_time));
            }
            if (j3 != 0 && j2 != 0) {
                sb.append('\n');
            }
            if (j2 != 0) {
                sb.append(
                        BatteryUtils.buildBatteryUsageTimeInfo(
                                context,
                                j2,
                                R.string.battery_usage_background_less_than_one_minute,
                                R.string.battery_usage_for_background_time));
            }
        }
        powerGaugePreference.setSummary(sb.toString());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batteryusage.BatteryUsageBreakdownController$1, reason: invalid class name */
    public final class AnonymousClass1 implements AdapterView.OnItemSelectedListener {
        public AnonymousClass1() {}

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
            if (BatteryUsageBreakdownController.this.mSpinnerPosition != i) {
                BatteryUsageBreakdownController.this.mSpinnerPosition = i;
                BatteryUsageBreakdownController.this.mHandler.post(
                        new BatteryUsageBreakdownController$$ExternalSyntheticLambda1(2, this));
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public final void onNothingSelected(AdapterView adapterView) {}
    }
}
