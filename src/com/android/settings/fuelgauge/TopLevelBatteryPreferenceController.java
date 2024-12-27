package com.android.settings.fuelgauge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryUsageStats;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TopLevelBatteryPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String TAG = "TopLvBatteryPrefControl";
    private final BatteryBroadcastReceiver mBatteryBroadcastReceiver;
    private BatteryInfo mBatteryInfo;
    private BatteryStatusFeatureProvider mBatteryStatusFeatureProvider;
    private String mBatteryStatusLabel;
    protected boolean mIsBatteryPresent;
    Preference mPreference;

    public TopLevelBatteryPreferenceController(Context context, String str) {
        super(context, str);
        this.mIsBatteryPresent = true;
        BatteryBroadcastReceiver batteryBroadcastReceiver =
                new BatteryBroadcastReceiver(this.mContext);
        this.mBatteryBroadcastReceiver = batteryBroadcastReceiver;
        batteryBroadcastReceiver.mBatteryListener =
                new TopLevelBatteryPreferenceController$$ExternalSyntheticLambda1(this);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mBatteryStatusFeatureProvider =
                (BatteryStatusFeatureProviderImpl)
                        featureFactoryImpl.batteryStatusFeatureProvider$delegate.getValue();
    }

    public static ComponentName convertClassPathToComponentName(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String[] split = str.split("\\.");
        int length = split.length - 1;
        if (length < 0) {
            return null;
        }
        int length2 = (str.length() - split[length].length()) - 1;
        return new ComponentName(
                length2 > 0 ? str.substring(0, length2) : ApnSettings.MVNO_NONE, split[length]);
    }

    private CharSequence generateLabel(BatteryInfo batteryInfo) {
        CharSequence charSequence;
        if (Utils.containsIncompatibleChargers(this.mContext, TAG)) {
            return this.mContext.getString(
                    R.string.power_incompatible_charging_settings_home_page,
                    batteryInfo.batteryPercentString);
        }
        if (batteryInfo.isBatteryDefender && !batteryInfo.discharging) {
            return this.mContext.getString(
                    R.string.power_charging_on_hold_settings_home_page,
                    batteryInfo.batteryPercentString);
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        BatterySettingsFeatureProviderImpl batterySettingsFeatureProvider =
                featureFactoryImpl.getBatterySettingsFeatureProvider();
        if (batteryInfo.chargeLabel != null) {
            batterySettingsFeatureProvider.getClass();
        }
        if (batteryInfo.batteryStatus == 4) {
            return batteryInfo.statusLabel;
        }
        if (!batteryInfo.discharging && (charSequence = batteryInfo.chargeLabel) != null) {
            return charSequence;
        }
        CharSequence charSequence2 = batteryInfo.remainingLabel;
        return charSequence2 == null
                ? batteryInfo.batteryPercentString
                : this.mContext.getString(
                        R.string.power_remaining_settings_home_page,
                        batteryInfo.batteryPercentString,
                        charSequence2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(BatteryInfo batteryInfo) {
        Log.d(TAG, "getBatteryInfo: " + batteryInfo);
        this.mBatteryInfo = batteryInfo;
        updateState(this.mPreference);
        setSummaryAsync(batteryInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$new$1(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "onBatteryChanged: type=", TAG);
        if (i == 6) {
            this.mIsBatteryPresent = false;
        }
        final Context context = this.mContext;
        final TopLevelBatteryPreferenceController$$ExternalSyntheticLambda1
                topLevelBatteryPreferenceController$$ExternalSyntheticLambda1 =
                        new TopLevelBatteryPreferenceController$$ExternalSyntheticLambda1(this);
        new AsyncTask() { // from class: com.android.settings.fuelgauge.BatteryInfo.2
            public final /* synthetic */ BatteryUsageStats val$batteryUsageStats = null;
            public final /* synthetic */
            TopLevelBatteryPreferenceController$$ExternalSyntheticLambda1 val$callback;
            public final /* synthetic */ Context val$context;
            public final /* synthetic */ boolean val$shortString;

            public AnonymousClass2(
                    final Context context2,
                    final TopLevelBatteryPreferenceController$$ExternalSyntheticLambda1
                            topLevelBatteryPreferenceController$$ExternalSyntheticLambda12) {
                r2 = context2;
                r3 = topLevelBatteryPreferenceController$$ExternalSyntheticLambda12;
            }

            /* JADX WARN: Removed duplicated region for block: B:24:0x0096  */
            /* JADX WARN: Removed duplicated region for block: B:6:0x0045  */
            @Override // android.os.AsyncTask
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object doInBackground(java.lang.Object[] r19) {
                /*
                    r18 = this;
                    r1 = r18
                    r0 = r19
                    java.lang.Void[] r0 = (java.lang.Void[]) r0
                    android.os.BatteryUsageStats r0 = r1.val$batteryUsageStats
                    r2 = 1
                    java.lang.String r3 = "BatteryInfo"
                    r4 = 0
                    if (r0 == 0) goto L10
                Le:
                    r5 = r4
                    goto L32
                L10:
                    android.content.Context r0 = r2     // Catch: java.lang.RuntimeException -> L20
                    java.lang.Class<android.os.BatteryStatsManager> r5 = android.os.BatteryStatsManager.class
                    java.lang.Object r0 = r0.getSystemService(r5)     // Catch: java.lang.RuntimeException -> L20
                    android.os.BatteryStatsManager r0 = (android.os.BatteryStatsManager) r0     // Catch: java.lang.RuntimeException -> L20
                    android.os.BatteryUsageStats r0 = r0.getBatteryUsageStats()     // Catch: java.lang.RuntimeException -> L20
                    r5 = r2
                    goto L32
                L20:
                    r0 = move-exception
                    java.lang.String r5 = "getBatteryInfo() from getBatteryUsageStats()"
                    android.util.Log.e(r3, r5, r0)
                    android.os.BatteryUsageStats$Builder r0 = new android.os.BatteryUsageStats$Builder
                    java.lang.String[] r5 = new java.lang.String[r4]
                    r0.<init>(r5)
                    android.os.BatteryUsageStats r0 = r0.build()
                    goto Le
                L32:
                    android.content.Context r1 = r2
                    long r6 = java.lang.System.currentTimeMillis()
                    java.lang.String r8 = "time for getStats"
                    com.android.settings.fuelgauge.BatteryUtils.logRuntime(r6, r3, r8)
                    long r6 = java.lang.System.currentTimeMillis()
                    com.android.settings.overlay.FeatureFactoryImpl r8 = com.android.settings.overlay.FeatureFactoryImpl._factory
                    if (r8 == 0) goto L96
                    com.android.settings.fuelgauge.PowerUsageFeatureProviderImpl r8 = r8.getPowerUsageFeatureProvider()
                    android.os.SystemClock.elapsedRealtime()
                    int r9 = com.android.settingslib.utils.PowerUtil.$r8$clinit
                    android.content.IntentFilter r9 = new android.content.IntentFilter
                    java.lang.String r10 = "android.intent.action.BATTERY_CHANGED"
                    r9.<init>(r10)
                    r10 = 0
                    android.content.Intent r9 = r1.registerReceiver(r10, r9)
                    java.lang.String r10 = "plugged"
                    r11 = -1
                    int r10 = r9.getIntExtra(r10, r11)
                    if (r10 != 0) goto L64
                    goto L65
                L64:
                    r2 = r4
                L65:
                    if (r2 == 0) goto L6a
                    r8.getClass()
                L6a:
                    if (r2 == 0) goto L72
                    long r10 = r0.getBatteryTimeRemainingMs()
                L70:
                    r13 = r10
                    goto L75
                L72:
                    r10 = 0
                    goto L70
                L75:
                    com.android.settingslib.fuelgauge.Estimate r2 = new com.android.settingslib.fuelgauge.Estimate
                    r17 = 0
                    r15 = -1
                    r12 = r2
                    r12.<init>(r13, r15, r17)
                    java.lang.String r4 = "time for regular BatteryInfo"
                    com.android.settings.fuelgauge.BatteryUtils.logRuntime(r6, r3, r4)
                    com.android.settings.fuelgauge.BatteryInfo r1 = com.android.settings.fuelgauge.BatteryInfo.getBatteryInfo(r1, r9, r0, r2)
                    if (r5 == 0) goto L95
                    r0.close()     // Catch: java.lang.Exception -> L8e
                    goto L95
                L8e:
                    r0 = move-exception
                    r2 = r0
                    java.lang.String r0 = "BatteryUsageStats.close() failed"
                    android.util.Log.e(r3, r0, r2)
                L95:
                    return r1
                L96:
                    java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
                    java.lang.String r1 = "No feature factory configured"
                    r0.<init>(r1)
                    throw r0
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.settings.fuelgauge.BatteryInfo.AnonymousClass2.doInBackground(java.lang.Object[]):java.lang.Object");
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                long currentTimeMillis = System.currentTimeMillis();
                r3.f$0.lambda$new$0((BatteryInfo) obj);
                BatteryUtils.logRuntime(currentTimeMillis, "BatteryInfo", "time for callback");
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSummaryAsync$2(boolean z, BatteryInfo batteryInfo) {
        if (!z) {
            this.mBatteryStatusLabel = null;
        }
        Preference preference = this.mPreference;
        CharSequence charSequence = this.mBatteryStatusLabel;
        if (charSequence == null) {
            charSequence = generateLabel(batteryInfo);
        }
        preference.setSummary(charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSummaryAsync$3(BatteryInfo batteryInfo) {
        this.mBatteryStatusFeatureProvider.getClass();
        ThreadUtils.postOnMainThread(
                new TopLevelBatteryPreferenceController$$ExternalSyntheticLambda0(
                        this, batteryInfo, 1));
    }

    private void setSummaryAsync(BatteryInfo batteryInfo) {
        ThreadUtils.postOnBackgroundThread(
                new TopLevelBatteryPreferenceController$$ExternalSyntheticLambda0(
                        this, batteryInfo, 0));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_top_level_battery)
                ? 0
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public CharSequence getDashboardLabel(Context context, BatteryInfo batteryInfo, boolean z) {
        if (batteryInfo == null || context == null) {
            return null;
        }
        Log.d(TAG, "getDashboardLabel: " + this.mBatteryStatusLabel + " batteryStatusUpdate=" + z);
        if (z) {
            setSummaryAsync(batteryInfo);
        }
        String str = this.mBatteryStatusLabel;
        return str == null ? generateLabel(batteryInfo) : str;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return getSummary(true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mBatteryBroadcastReceiver.register();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        BatteryBroadcastReceiver batteryBroadcastReceiver = this.mBatteryBroadcastReceiver;
        batteryBroadcastReceiver.mContext.unregisterReceiver(batteryBroadcastReceiver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateBatteryStatus(String str, BatteryInfo batteryInfo) {
        this.mBatteryStatusLabel = str;
        if (this.mPreference == null) {
            return;
        }
        CharSequence summary = getSummary(false);
        if (summary != null) {
            this.mPreference.setSummary(summary);
        }
        Log.d(TAG, "updateBatteryStatus: " + str + " summary: " + ((Object) summary));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private CharSequence getSummary(boolean z) {
        return !this.mIsBatteryPresent
                ? this.mContext.getText(R.string.battery_missing_message)
                : getDashboardLabel(this.mContext, this.mBatteryInfo, z);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
