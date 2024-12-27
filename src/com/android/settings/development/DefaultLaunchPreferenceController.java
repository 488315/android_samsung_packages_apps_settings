package com.android.settings.development;

import android.content.Context;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultLaunchPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin {
    public final String mPreferenceKey;

    public DefaultLaunchPreferenceController(Context context, String str) {
        super(context);
        this.mPreferenceKey = str;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPreferenceKey;
    }
}
