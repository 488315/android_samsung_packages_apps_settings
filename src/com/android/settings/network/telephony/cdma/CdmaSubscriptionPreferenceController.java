package com.android.settings.network.telephony.cdma;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.network.telephony.MobileNetworkUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CdmaSubscriptionPreferenceController extends CdmaBasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String TYPE_NV = "NV";
    private static final String TYPE_RUIM = "RUIM";
    ListPreference mPreference;

    public CdmaSubscriptionPreferenceController(Context context, String str) {
        super(context, str);
    }

    public boolean deviceSupportsNvAndRuim() {
        boolean z;
        boolean z2;
        String rilSubscriptionTypes = getRilSubscriptionTypes();
        if (TextUtils.isEmpty(rilSubscriptionTypes)) {
            z = false;
            z2 = false;
        } else {
            z = false;
            z2 = false;
            for (String str : rilSubscriptionTypes.split(",")) {
                String trim = str.trim();
                if (trim.equalsIgnoreCase(TYPE_NV)) {
                    z = true;
                } else if (trim.equalsIgnoreCase(TYPE_RUIM)) {
                    z2 = true;
                }
            }
        }
        return z && z2;
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        return (MobileNetworkUtils.isCdmaOptions(this.mContext, i) && deviceSupportsNvAndRuim())
                ? 0
                : 2;
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

    public String getRilSubscriptionTypes() {
        return SystemProperties.get("ril.subscription.types");
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
            this.mTelephonyManager.setCdmaSubscriptionMode(parseInt);
            Settings.Global.putInt(
                    this.mContext.getContentResolver(), "subscription_mode", parseInt);
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
        super.updateState(preference);
        ListPreference listPreference = (ListPreference) preference;
        listPreference.setVisible(getAvailabilityStatus() == 0);
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "subscription_mode", 0);
        if (i != -1) {
            listPreference.setValue(Integer.toString(i));
        }
    }

    @Override // com.android.settings.network.telephony.cdma.CdmaBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
