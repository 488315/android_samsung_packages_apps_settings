package com.samsung.android.settings.development;

import android.content.Context;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.SwitchPreference;

import com.android.internal.telephony.ISemTelephony;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SimVonrCallsPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final String[] EXCEPT_LIST;
    public final String mPreferenceKey;
    public final int mSlotId;
    public int mStateMode;
    public final TelephonyManager mTelephonyManager;

    public SimVonrCallsPreferenceController(Context context, String str) {
        super(context);
        this.mSlotId = 0;
        this.mStateMode = 0;
        this.EXCEPT_LIST =
                new String[] {
                    "SM-A7160",
                    "SM-A5160",
                    "SM-G9880",
                    "SM-G9860",
                    "SM-G9810",
                    "SM-N9860",
                    "SM-N9810",
                    "SM-F7070",
                    "SM-F9160",
                    "SM-W2021",
                    "SM-G7810",
                    "SM-E5260"
                };
        this.mPreferenceKey = str;
        this.mSlotId = "sim_vonr_calls1".equals(str) ? 1 : 0;
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPreferenceKey;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        int supportedModemCount;
        int i;
        if (Rune.isChinaModel()
                && (supportedModemCount = this.mTelephonyManager.getSupportedModemCount()) != 0
                && ((i = this.mSlotId) != 1 || supportedModemCount >= 2)) {
            boolean z = this.mTelephonyManager.getSimState(i) != 1;
            boolean z2 =
                    SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportStandAlone");
            boolean z3 = SystemProperties.getInt("ril.supportSA", -1) != -1;
            if (z && (z2 || z3)) {
                for (String str : this.EXCEPT_LIST) {
                    if (str.equalsIgnoreCase(
                            SystemProperties.get("ro.product.model", ApnSettings.MVNO_NONE))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "onPreferenceChange :: Vonr Calls set to ",
                "SimVonrCallsPreferenceController",
                booleanValue);
        if (booleanValue) {
            setVonrMode(1);
        } else {
            setVonrMode(0);
        }
        return true;
    }

    public final void setVonrMode(int i) {
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i, "[setVonrMode] - mode : ", " phoneId: ");
        int i2 = this.mSlotId;
        Preference$$ExternalSyntheticOutline0.m(m, i2, "SimVonrCallsPreferenceController");
        try {
            ISemTelephony asInterface =
                    ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
            if (asInterface != null ? asInterface.setVoNRMode(i2, i) : false) {
                this.mStateMode = i;
            }
        } catch (Exception e) {
            Log.i("SimVonrCallsPreferenceController", e.toString());
        }
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        SwitchPreference switchPreference = (SwitchPreference) this.mPreference;
        StringBuilder sb = new StringBuilder("[getVonrMode]  phoneId: ");
        int i = this.mSlotId;
        Preference$$ExternalSyntheticOutline0.m(sb, i, "SimVonrCallsPreferenceController");
        try {
            ISemTelephony asInterface =
                    ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
            int voNRMode = asInterface != null ? asInterface.getVoNRMode(i) : -1;
            Log.d("SimVonrCallsPreferenceController", "[getVonrMode] " + voNRMode);
            if (voNRMode != -1) {
                this.mStateMode = voNRMode;
            }
        } catch (Exception e) {
            this.mStateMode = 0;
            Log.i("SimVonrCallsPreferenceController", e.toString());
        }
        if (this.mStateMode == 0) {
            switchPreference.setChecked(false);
        } else {
            switchPreference.setChecked(true);
        }
    }
}
