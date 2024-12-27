package com.android.settings.network.telephony.cdma;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.network.telephony.MobileNetworkUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CdmaSystemSelectPreferenceController extends CdmaBasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "CdmaSystemSelectPreferenceController";

    public CdmaSystemSelectPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void resetCdmaRoamingModeToDefault() {
        ((ListPreference) this.mPreference).setValue(Integer.toString(2));
        Settings.Global.putInt(this.mContext.getContentResolver(), "roaming_settings", 2);
        this.mTelephonyManager.setCdmaRoamingMode(2);
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int parseInt = Integer.parseInt((String) obj);
        try {
            this.mTelephonyManager.setCdmaRoamingMode(parseInt);
            Settings.Global.putInt(
                    this.mContext.getContentResolver(), "roaming_settings", parseInt);
            return true;
        } catch (IllegalStateException unused) {
            return false;
        }
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        int cdmaRoamingMode;
        super.updateState(preference);
        ListPreference listPreference = (ListPreference) preference;
        boolean z = true;
        boolean z2 = getAvailabilityStatus() == 0;
        listPreference.setVisible(z2);
        if (z2) {
            TelephonyManager telephonyManager = this.mTelephonyManager;
            boolean z3 = telephonyManager != null;
            if (z3) {
                try {
                    cdmaRoamingMode = telephonyManager.getCdmaRoamingMode();
                } catch (Exception e) {
                    Log.e(TAG, "Fail to access framework API", e);
                    return;
                }
            } else {
                cdmaRoamingMode = -1;
            }
            if (cdmaRoamingMode != -1) {
                if (cdmaRoamingMode != 0 && cdmaRoamingMode != 2) {
                    resetCdmaRoamingModeToDefault();
                }
                listPreference.setValue(Integer.toString(cdmaRoamingMode));
            }
            int networkTypeFromRaf =
                    z3
                            ? MobileNetworkUtils.getNetworkTypeFromRaf(
                                    (int) this.mTelephonyManager.getAllowedNetworkTypesForReason(0))
                            : -1;
            if (networkTypeFromRaf == 9 || networkTypeFromRaf == 26) {
                z = false;
            }
            listPreference.setEnabled(z);
        }
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
