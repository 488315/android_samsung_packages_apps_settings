package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;

import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryTipsController extends BasePreferenceController {

    @VisibleForTesting static final String ANOMALY_KEY = "anomaly_key";
    private static final String CARD_PREFERENCE_KEY = "battery_tips_card";
    private static final String ROOT_PREFERENCE_KEY = "battery_tips_category";
    private static final String TAG = "BatteryTipsController";

    @VisibleForTesting AnomalyEventWrapper mAnomalyEventWrapper;

    @VisibleForTesting BatteryTipsCardPreference mCardPreference;

    @VisibleForTesting Boolean mIsAcceptable;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private OnAnomalyConfirmListener mOnAnomalyConfirmListener;
    private OnAnomalyRejectListener mOnAnomalyRejectListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnAnomalyConfirmListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnAnomalyRejectListener {}

    public BatteryTipsController(Context context) {
        super(context, ROOT_PREFERENCE_KEY);
        this.mAnomalyEventWrapper = null;
        this.mIsAcceptable = Boolean.FALSE;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$handleBatteryTipsCardUpdated$0(int i) {
        int i2;
        int i3 = 0;
        this.mCardPreference.setVisible(false);
        OnAnomalyConfirmListener onAnomalyConfirmListener = this.mOnAnomalyConfirmListener;
        if (onAnomalyConfirmListener != null) {
            PowerUsageAdvanced powerUsageAdvanced =
                    ((PowerUsageAdvanced$$ExternalSyntheticLambda1) onAnomalyConfirmListener).f$0;
            BatteryChartPreferenceController batteryChartPreferenceController =
                    powerUsageAdvanced.mBatteryChartPreferenceController;
            int i4 = batteryChartPreferenceController.mDailyHighlightSlotIndex;
            if (i4 != -2
                    && (i2 = batteryChartPreferenceController.mHourlyHighlightSlotIndex) != -2
                    && (i4 != batteryChartPreferenceController.mDailyChartIndex
                            || i2 != batteryChartPreferenceController.mHourlyChartIndex)) {
                batteryChartPreferenceController.mDailyChartIndex = i4;
                batteryChartPreferenceController.mHourlyChartIndex = i2;
                Log.d(
                        "BatteryChartPreferenceController",
                        String.format(
                                "onDailyChartSelect:%d, onHourlyChartSelect:%d",
                                Integer.valueOf(i4),
                                Integer.valueOf(
                                        batteryChartPreferenceController.mHourlyChartIndex)));
                batteryChartPreferenceController.refreshUi();
                batteryChartPreferenceController.mHandler.post(
                        new BatteryChartPreferenceController$$ExternalSyntheticLambda1(
                                batteryChartPreferenceController, i3));
                PowerUsageAdvanced$$ExternalSyntheticLambda1
                        powerUsageAdvanced$$ExternalSyntheticLambda1 =
                                batteryChartPreferenceController.mOnSelectedIndexUpdatedListener;
                if (powerUsageAdvanced$$ExternalSyntheticLambda1 != null) {
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            PowerUsageAdvanced.SEARCH_INDEX_DATA_PROVIDER;
                    powerUsageAdvanced$$ExternalSyntheticLambda1.f$0.onSelectedSlotDataUpdated();
                }
            }
            powerUsageAdvanced.mBatteryTipsController.acceptTipsCard();
            return;
        }
        AnomalyEventWrapper anomalyEventWrapper = this.mAnomalyEventWrapper;
        anomalyEventWrapper.getClass();
        String str =
                (String)
                        anomalyEventWrapper.getInfo(
                                new AnomalyEventWrapper$$ExternalSyntheticLambda0(2), null);
        Integer num =
                (Integer)
                        anomalyEventWrapper.getInfo(
                                new AnomalyEventWrapper$$ExternalSyntheticLambda0(3), null);
        if (!TextUtils.isEmpty(str) && num != null) {
            try {
                Settings.System.putInt(
                        anomalyEventWrapper.mContext.getContentResolver(), str, num.intValue());
                Log.d(
                        "AnomalyEventWrapper",
                        String.format("Update settings name=%s to value=%d", str, num));
            } catch (SecurityException e) {
                Log.w(
                        "AnomalyEventWrapper",
                        String.format("Failed to update settings name=%s to value=%d", str, num),
                        e);
            }
            this.mMetricsFeatureProvider.action(51, 1861, 51, i, ANOMALY_KEY);
        }
        Log.d("AnomalyEventWrapper", "Failed to update settings due to invalid key or value");
        AnomalyEventWrapper anomalyEventWrapper2 = this.mAnomalyEventWrapper;
        SubSettingLauncher subSettingLauncher = anomalyEventWrapper2.mSubSettingLauncher;
        if (subSettingLauncher == null) {
            if (subSettingLauncher == null) {
                String str2 =
                        (String)
                                anomalyEventWrapper2.getInfo(
                                        new AnomalyEventWrapper$$ExternalSyntheticLambda0(11),
                                        null);
                if (!TextUtils.isEmpty(str2)) {
                    Integer num2 =
                            (Integer)
                                    anomalyEventWrapper2.getInfo(
                                            new AnomalyEventWrapper$$ExternalSyntheticLambda0(12),
                                            null);
                    String str3 =
                            (String)
                                    anomalyEventWrapper2.getInfo(
                                            new AnomalyEventWrapper$$ExternalSyntheticLambda0(1),
                                            null);
                    Bundle bundle = Bundle.EMPTY;
                    if (!TextUtils.isEmpty(str3)) {
                        bundle = new Bundle(1);
                        bundle.putString(":settings:fragment_args_key", str3);
                    }
                    SubSettingLauncher subSettingLauncher2 =
                            new SubSettingLauncher(anomalyEventWrapper2.mContext);
                    SubSettingLauncher.LaunchRequest launchRequest =
                            subSettingLauncher2.mLaunchRequest;
                    launchRequest.mDestinationName = str2;
                    launchRequest.mSourceMetricsCategory = num2.intValue();
                    launchRequest.mArguments = bundle;
                    anomalyEventWrapper2.mSubSettingLauncher = subSettingLauncher2;
                }
                subSettingLauncher = anomalyEventWrapper2.mSubSettingLauncher;
            }
            anomalyEventWrapper2.mSubSettingLauncher = subSettingLauncher;
        }
        SubSettingLauncher subSettingLauncher3 = anomalyEventWrapper2.mSubSettingLauncher;
        if (subSettingLauncher3 != null) {
            subSettingLauncher3.launch();
            this.mMetricsFeatureProvider.action(51, 1861, 51, i, ANOMALY_KEY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$handleBatteryTipsCardUpdated$1(int i) {
        this.mCardPreference.setVisible(false);
        String dismissRecordKey =
                this.mAnomalyEventWrapper.mPowerAnomalyEvent.getDismissRecordKey();
        if (!TextUtils.isEmpty(dismissRecordKey)) {
            Context context = this.mContext;
            Uri uri = DatabaseUtils.BATTERY_CONTENT_URI;
            SharedPreferences sharedPreferences =
                    context.getApplicationContext()
                            .getSharedPreferences("battery_usage_shared_prefs", 0);
            if (sharedPreferences != null) {
                SharedPreferences sharedPreferences2 =
                        context.getApplicationContext()
                                .getSharedPreferences("battery_usage_shared_prefs", 0);
                Set<String> stringSet =
                        sharedPreferences2 != null
                                ? sharedPreferences2.getStringSet(
                                        "dismissed_power_anomaly_keys", new ArraySet())
                                : new ArraySet<>();
                stringSet.add(dismissRecordKey);
                sharedPreferences
                        .edit()
                        .putStringSet("dismissed_power_anomaly_keys", stringSet)
                        .apply();
            }
        }
        this.mMetricsFeatureProvider.action(51, 1862, 51, i, ANOMALY_KEY);
    }

    public void acceptTipsCard() {
        if (this.mAnomalyEventWrapper == null || !this.mIsAcceptable.booleanValue()) {
            return;
        }
        String dismissRecordKey =
                this.mAnomalyEventWrapper.mPowerAnomalyEvent.getDismissRecordKey();
        if (!TextUtils.isEmpty(dismissRecordKey)) {
            Context context = this.mContext;
            Uri uri = DatabaseUtils.BATTERY_CONTENT_URI;
            SharedPreferences sharedPreferences =
                    context.getApplicationContext()
                            .getSharedPreferences("battery_usage_shared_prefs", 0);
            if (sharedPreferences != null) {
                SharedPreferences sharedPreferences2 =
                        context.getApplicationContext()
                                .getSharedPreferences("battery_usage_shared_prefs", 0);
                Set<String> stringSet =
                        sharedPreferences2 != null
                                ? sharedPreferences2.getStringSet(
                                        "dismissed_power_anomaly_keys", new ArraySet())
                                : new ArraySet<>();
                stringSet.add(dismissRecordKey);
                sharedPreferences
                        .edit()
                        .putStringSet("dismissed_power_anomaly_keys", stringSet)
                        .apply();
            }
        }
        this.mCardPreference.setVisible(false);
        this.mMetricsFeatureProvider.action(
                51,
                1861,
                51,
                this.mAnomalyEventWrapper.mPowerAnomalyEvent.getKey().getNumber(),
                ANOMALY_KEY);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCardPreference =
                (BatteryTipsCardPreference) preferenceScreen.findPreference(CARD_PREFERENCE_KEY);
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

    public void handleBatteryTipsCardUpdated(AnomalyEventWrapper anomalyEventWrapper, boolean z) {
        BatteryDiffEntry batteryDiffEntry;
        this.mAnomalyEventWrapper = anomalyEventWrapper;
        this.mIsAcceptable = Boolean.valueOf(z);
        AnomalyEventWrapper anomalyEventWrapper2 = this.mAnomalyEventWrapper;
        if (anomalyEventWrapper2 == null) {
            this.mCardPreference.setVisible(false);
            return;
        }
        int number = anomalyEventWrapper2.mPowerAnomalyEvent.getKey().getNumber();
        AnomalyEventWrapper anomalyEventWrapper3 = this.mAnomalyEventWrapper;
        BatteryTipsCardPreference batteryTipsCardPreference = this.mCardPreference;
        anomalyEventWrapper3.getClass();
        String str =
                (String)
                        anomalyEventWrapper3.getInfo(
                                new AnomalyEventWrapper$$ExternalSyntheticLambda0(5),
                                new AnomalyEventWrapper$$ExternalSyntheticLambda0(6));
        boolean isEmpty = TextUtils.isEmpty(str);
        int i = anomalyEventWrapper3.mResourceIndex;
        if (isEmpty) {
            int resourceId =
                    anomalyEventWrapper3.getResourceId(
                            R.array.power_anomaly_title_ids, i, "string");
            PowerAnomalyEvent powerAnomalyEvent = anomalyEventWrapper3.mPowerAnomalyEvent;
            str =
                    powerAnomalyEvent.hasWarningBannerInfo()
                            ? anomalyEventWrapper3.mContext.getString(resourceId)
                            : (!powerAnomalyEvent.hasWarningItemInfo()
                                            || (batteryDiffEntry =
                                                            anomalyEventWrapper3
                                                                    .mRelatedBatteryDiffEntry)
                                                    == null)
                                    ? null
                                    : anomalyEventWrapper3.mContext.getString(
                                            resourceId, batteryDiffEntry.getAppLabel());
        }
        if (TextUtils.isEmpty(str)) {
            this.mCardPreference.setVisible(false);
            return;
        }
        batteryTipsCardPreference.setTitle(str);
        int i2 = anomalyEventWrapper3.mCardStyleId;
        int resourceId2 =
                anomalyEventWrapper3.getResourceId(R.array.battery_tips_card_icons, i2, "drawable");
        if (batteryTipsCardPreference.mIconResourceId != resourceId2) {
            batteryTipsCardPreference.mIconResourceId = resourceId2;
            batteryTipsCardPreference.notifyChanged();
        }
        int resourceId3 =
                anomalyEventWrapper3.getResourceId(R.array.battery_tips_card_colors, i2, "color");
        if (batteryTipsCardPreference.mButtonColorResourceId != resourceId3) {
            batteryTipsCardPreference.mButtonColorResourceId = resourceId3;
            batteryTipsCardPreference.notifyChanged();
        }
        String str2 =
                (String)
                        anomalyEventWrapper3.getInfo(
                                new AnomalyEventWrapper$$ExternalSyntheticLambda0(9),
                                new AnomalyEventWrapper$$ExternalSyntheticLambda0(10));
        if (TextUtils.isEmpty(str2)) {
            str2 =
                    anomalyEventWrapper3.getStringFromArrayResource(
                            R.array.power_anomaly_main_btn_strings, i);
        }
        if (!TextUtils.equals(batteryTipsCardPreference.mMainButtonLabel, str2)) {
            batteryTipsCardPreference.mMainButtonLabel = str2;
            batteryTipsCardPreference.notifyChanged();
        }
        String str3 =
                (String)
                        anomalyEventWrapper3.getInfo(
                                new AnomalyEventWrapper$$ExternalSyntheticLambda0(7),
                                new AnomalyEventWrapper$$ExternalSyntheticLambda0(8));
        if (TextUtils.isEmpty(str3)) {
            str3 =
                    anomalyEventWrapper3.getStringFromArrayResource(
                            R.array.power_anomaly_dismiss_btn_strings, i);
        }
        if (!TextUtils.equals(batteryTipsCardPreference.mDismissButtonLabel, str3)) {
            batteryTipsCardPreference.mDismissButtonLabel = str3;
            batteryTipsCardPreference.notifyChanged();
        }
        BatteryTipsCardPreference batteryTipsCardPreference2 = this.mCardPreference;
        batteryTipsCardPreference2.mOnConfirmListener =
                new BatteryTipsController$$ExternalSyntheticLambda0(this, number);
        batteryTipsCardPreference2.mOnRejectListener =
                new BatteryTipsController$$ExternalSyntheticLambda0(this, number);
        batteryTipsCardPreference2.setVisible(true);
        this.mMetricsFeatureProvider.action(51, 1860, 51, number, ANOMALY_KEY);
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
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setOnAnomalyConfirmListener(OnAnomalyConfirmListener onAnomalyConfirmListener) {
        this.mOnAnomalyConfirmListener = onAnomalyConfirmListener;
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

    public void setOnAnomalyRejectListener(OnAnomalyRejectListener onAnomalyRejectListener) {}
}
