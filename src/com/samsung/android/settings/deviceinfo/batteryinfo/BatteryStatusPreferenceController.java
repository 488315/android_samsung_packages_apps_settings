package com.samsung.android.settings.deviceinfo.batteryinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BatteryStatusPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private final String LOG_TAG;
    private BroadcastReceiver mBatteryStatusReceiver;
    private Preference mPreference;

    public BatteryStatusPreferenceController(Context context, String str) {
        super(context, str);
        this.LOG_TAG = "BatteryStatusPreferenceController";
    }

    private BroadcastReceiver getBatteryStatusReceiver() {
        if (this.mBatteryStatusReceiver == null) {
            this.mBatteryStatusReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.samsung.android.settings.deviceinfo.batteryinfo.BatteryStatusPreferenceController.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (!"android.intent.action.BATTERY_CHANGED".equals(intent.getAction())
                                    || BatteryStatusPreferenceController.this.mPreference == null) {
                                return;
                            }
                            BatteryStatusPreferenceController.this.mPreference.setSummary(
                                    BatteryStatusPreferenceController.this.getBatteryStatusSummary(
                                            intent));
                        }
                    };
        }
        return this.mBatteryStatusReceiver;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getBatteryStatusSummary(Intent intent) {
        String string;
        int i;
        Resources resources = this.mContext.getResources();
        int intExtra = intent.getIntExtra("plugged", 0);
        int intExtra2 = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
        boolean z = (intent.getIntExtra("misc_event", 0) & 4) == 0;
        boolean booleanExtra = intent.getBooleanExtra("hv_charger", false);
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "plugType : ", ", status : ", intExtra, intExtra2, ", hv_charger : ");
        m.append(booleanExtra);
        m.append(", FullyConnected : ");
        m.append(z);
        Log.d("BatteryStatusPreferenceController", m.toString());
        if (booleanExtra && Rune.isVzwModel()) {
            string = resources.getString(R.string.sec_battery_info_status_adaptive_fast_charging);
        } else if (intExtra2 == 2) {
            String string2 = resources.getString(R.string.battery_info_status_charging);
            if (intExtra > 0) {
                if (intExtra == 4) {
                    i = R.string.sec_battery_info_power_wireless;
                } else {
                    if (z) {
                        if (intExtra == 1) {
                            i = R.string.sec_battery_info_power_ac;
                        } else if (intExtra == 2) {
                            i = R.string.sec_battery_info_power_usb;
                        }
                    }
                    i = -1;
                }
                if (i != -1) {
                    StringBuilder m2 =
                            PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                    string2, " (");
                    m2.append(resources.getString(i));
                    m2.append(")");
                    string = m2.toString();
                }
            }
            string = string2;
        } else {
            string =
                    intExtra2 == 3
                            ? resources.getString(R.string.battery_info_status_discharging)
                            : intExtra2 == 4
                                    ? resources.getString(R.string.battery_info_status_not_charging)
                                    : intExtra2 == 5
                                            ? resources.getString(R.string.battery_info_status_full)
                                            : ApnSettings.MVNO_NONE;
        }
        return TextUtils.isEmpty(string)
                ? resources.getString(R.string.battery_info_status_unknown)
                : string;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        BroadcastReceiver broadcastReceiver = this.mBatteryStatusReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mBatteryStatusReceiver = null;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContext.registerReceiver(
                getBatteryStatusReceiver(),
                new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
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
}
