package com.android.settings.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SafetyInfoPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public static final Intent INTENT_PROBE =
            new Intent("android.settings.SHOW_SAFETY_AND_REGULATORY_INFO")
                    .setPackage("com.android.safetyregulatoryinfo");
    public final PackageManager mPackageManager;

    public SafetyInfoPreferenceController(Context context) {
        super(context);
        this.mPackageManager = this.mContext.getPackageManager();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "safety_info";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "safety_info")) {
            return false;
        }
        Intent intent = new Intent(INTENT_PROBE);
        intent.addFlags(268435456);
        this.mContext.startActivity(intent);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !this.mPackageManager.queryIntentActivities(INTENT_PROBE, 0).isEmpty();
    }
}
