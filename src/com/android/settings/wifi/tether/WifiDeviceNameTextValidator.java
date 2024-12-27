package com.android.settings.wifi.tether;

import android.text.TextUtils;

import com.android.settings.widget.ValidatedEditTextPreference;
import com.android.settings.wifi.WifiUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiDeviceNameTextValidator implements ValidatedEditTextPreference.Validator {
    @Override // com.android.settings.widget.ValidatedEditTextPreference.Validator
    public final boolean isTextValid(String str) {
        return (WifiUtils.isSSIDTooLong(str) || TextUtils.isEmpty(str) || str.length() < 1)
                ? false
                : true;
    }
}
