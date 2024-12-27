package com.android.settings.development.snooplogger;

import android.content.Context;
import android.os.SystemProperties;

import androidx.preference.SwitchPreferenceCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SnoopLoggerFiltersPreference extends SwitchPreferenceCompat {
    public final String mKey;

    public SnoopLoggerFiltersPreference(Context context, String str, String str2) {
        super(context);
        this.mKey = str;
        setKey(str);
        setTitle(str2);
        super.setChecked(
                SystemProperties.get(
                                "persist.bluetooth.snooplogfilter.".concat(str).concat(".enabled"))
                        .equals("true"));
    }

    @Override // androidx.preference.TwoStatePreference
    public final void setChecked(boolean z) {
        super.setChecked(z);
        SystemProperties.set(
                "persist.bluetooth.snooplogfilter.".concat(this.mKey).concat(".enabled"),
                z ? "true" : "false");
    }
}
