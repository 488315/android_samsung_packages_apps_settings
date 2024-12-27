package com.android.settings.deviceinfo;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.deviceinfo.AbstractIpAddressPreferenceController;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class IpAddressPreferenceController extends AbstractIpAddressPreferenceController
        implements PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !Rune.isDomesticModel();
    }
}
