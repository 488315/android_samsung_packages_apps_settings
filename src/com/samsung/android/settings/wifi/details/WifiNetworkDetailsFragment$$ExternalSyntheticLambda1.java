package com.samsung.android.settings.wifi.details;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiNetworkDetailsFragment$$ExternalSyntheticLambda1
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AbstractPreferenceController abstractPreferenceController =
                (AbstractPreferenceController) obj;
        if (abstractPreferenceController instanceof WifiNetworkConfigPreferenceController) {
            ((WifiNetworkConfigPreferenceController) abstractPreferenceController)
                    .updatePreference();
        }
    }
}
