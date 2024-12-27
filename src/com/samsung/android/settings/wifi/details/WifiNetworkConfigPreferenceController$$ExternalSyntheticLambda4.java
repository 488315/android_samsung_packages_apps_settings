package com.samsung.android.settings.wifi.details;

import androidx.preference.Preference;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiNetworkConfigPreferenceController$$ExternalSyntheticLambda4
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Preference preference = (Preference) obj;
        switch (this.$r8$classId) {
            case 0:
                preference.setVisible(false);
                break;
            case 1:
                preference.setVisible(false);
                break;
            case 2:
                preference.setVisible(true);
                break;
            case 3:
                preference.setVisible(false);
                break;
            case 4:
                preference.setVisible(!preference.getKey().equals("proxy_pac"));
                break;
            case 5:
                preference.setVisible(preference.getKey().equals("proxy_pac"));
                break;
            default:
                preference.setVisible(false);
                break;
        }
    }
}
