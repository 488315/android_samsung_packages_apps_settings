package com.samsung.android.settings.wifi.details;

import androidx.preference.SwitchPreferenceCompat;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiNetworkConnectFragment$$ExternalSyntheticLambda2
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AbstractPreferenceController abstractPreferenceController =
                (AbstractPreferenceController) obj;
        if (abstractPreferenceController instanceof WifiEapPreferenceController) {
            ((WifiEapPreferenceController) abstractPreferenceController).updatePreference(true);
            return;
        }
        if (!(abstractPreferenceController instanceof WifiHiddenNetworkPreferenceController)) {
            if (abstractPreferenceController instanceof WifiNetworkConfigPreferenceController) {
                ((WifiNetworkConfigPreferenceController) abstractPreferenceController)
                        .updatePreference();
            }
        } else {
            WifiHiddenNetworkPreferenceController wifiHiddenNetworkPreferenceController =
                    (WifiHiddenNetworkPreferenceController) abstractPreferenceController;
            SwitchPreferenceCompat switchPreferenceCompat =
                    wifiHiddenNetworkPreferenceController.mHiddenPref;
            if (switchPreferenceCompat == null) {
                return;
            }
            switchPreferenceCompat.setChecked(wifiHiddenNetworkPreferenceController.mHiddenSsid);
        }
    }
}
