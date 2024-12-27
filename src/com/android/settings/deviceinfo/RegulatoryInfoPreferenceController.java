package com.android.settings.deviceinfo;

import android.content.Intent;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RegulatoryInfoPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public static final Intent INTENT_PROBE = new Intent("android.settings.SHOW_REGULATORY_INFO");

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "regulatory_info";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !this.mContext.getPackageManager().queryIntentActivities(INTENT_PROBE, 0).isEmpty();
    }
}
