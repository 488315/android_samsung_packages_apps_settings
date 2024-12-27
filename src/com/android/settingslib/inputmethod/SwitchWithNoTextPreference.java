package com.android.settingslib.inputmethod;

import android.content.Context;

import androidx.preference.SwitchPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SwitchWithNoTextPreference extends SwitchPreference {
    public SwitchWithNoTextPreference(Context context) {
        super(context, null);
        this.mSwitchOn = ApnSettings.MVNO_NONE;
        notifyChanged();
        this.mSwitchOff = ApnSettings.MVNO_NONE;
        notifyChanged();
    }
}
