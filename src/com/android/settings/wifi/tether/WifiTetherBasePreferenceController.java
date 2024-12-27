package com.android.settings.wifi.tether;

import android.content.Context;
import android.net.TetheringManager;
import android.net.wifi.WifiManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class WifiTetherBasePreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final OnTetherConfigUpdateListener mListener;
    public Preference mPreference;
    public final WifiManager mWifiManager;
    public final String[] mWifiRegexs;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnTetherConfigUpdateListener {
        void onTetherConfigUpdated(AbstractPreferenceController abstractPreferenceController);
    }

    public WifiTetherBasePreferenceController(
            Context context, OnTetherConfigUpdateListener onTetherConfigUpdateListener) {
        super(context);
        this.mListener = onTetherConfigUpdateListener;
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mWifiRegexs =
                ((TetheringManager) context.getSystemService(TetheringManager.class))
                        .getTetherableWifiRegexs();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        updateDisplay$1();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        String[] strArr;
        return (this.mWifiManager == null
                        || (strArr = this.mWifiRegexs) == null
                        || strArr.length <= 0)
                ? false
                : true;
    }

    public abstract void updateDisplay$1();
}
