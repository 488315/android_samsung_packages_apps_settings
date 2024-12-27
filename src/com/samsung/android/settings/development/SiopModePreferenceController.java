package com.samsung.android.settings.development;

import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SiopModePreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public static final boolean sIsShipBinary =
            SystemProperties.getBoolean("ro.product_ship", true);
    public Context mContext;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "disable_siop";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !sIsShipBinary;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (sIsShipBinary || this.mContext == null) {
            return true;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        Log.d("SiopModePreferenceController", "switch event=" + obj);
        Intent intent = new Intent("com.samsung.intent.action.SDHMS_SIOP_DISABLE");
        intent.putExtra("disable_siop", booleanValue);
        this.mContext.sendBroadcast(intent);
        SystemProperties.set("persist.sys.siop.disable", Boolean.toString(booleanValue));
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (sIsShipBinary) {
            return;
        }
        ((SwitchPreference) this.mPreference)
                .setChecked(SystemProperties.getBoolean("persist.sys.siop.disable", false));
    }
}
